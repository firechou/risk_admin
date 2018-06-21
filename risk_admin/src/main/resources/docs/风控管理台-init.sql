--初始化sql语句

﻿CREATE SEQUENCE  "QRPAY"."RISK_RECORD_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_RECORD" 
(	
"ID" NUMBER(12,0) primary key, 
"PID" VARCHAR2(20 CHAR), 
"TRADE_NO" VARCHAR2(1024 CHAR), 
"RISK_TYPE" VARCHAR2(32 CHAR), 
"RISK_LEVEL" VARCHAR2(256 CHAR), 
"RISK_STATUS" VARCHAR2(16 CHAR), 
"CREATE_TIME" DATE DEFAULT sysdate, 
"UPDATE_TIME" DATE
);
COMMENT ON COLUMN "QRPAY"."RISK_RECORD"."PID" IS '银行或商家在支付宝的PID';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD"."TRADE_NO" IS '涉嫌商户被投诉的外部交易号样例';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD"."RISK_TYPE" IS '风险类型，欺诈、赌博、疑似欺诈、疑似赌博、疑似其它违禁、高危欺诈渠道商、高危违禁渠道商';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD"."RISK_LEVEL" IS '风险情况描述，商户欺诈基本成立';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD"."RISK_STATUS" IS 'wait_deal待处理 dealed已处理 off关闭';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_RECORD_ID_TRG" BEFORE INSERT OR UPDATE ON  RISK_RECORD
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_RECORD_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_RECORD;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_RECORD_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;


CREATE SEQUENCE  "QRPAY"."RISK_RECORD_BACK_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_RECORD_BACK" 
(	
"ID" NUMBER(12,0), 
"RECORD_ID" NUMBER(12,0), 
"BANK_CARD_NO" VARCHAR2(32 CHAR), 
"CERT_NO" VARCHAR2(32 CHAR), 
"BUSINESS_LICENSE_NO" VARCHAR2(32 CHAR), 
"MOBILE" VARCHAR2(16 CHAR), 
"MOBILE_IP" VARCHAR2(32 CHAR), 
"ORDER_IP" VARCHAR2(32 CHAR), 
"MERCH_NAME" VARCHAR2(32 CHAR), 
"EMAIL_ADDRESS" VARCHAR2(32 CHAR), 
"PROCESS_CODE" VARCHAR2(2 CHAR), 
"CREATE_TIME" DATE DEFAULT sysdate, 
"UPDATE_TIME" DATE, 
"OPERATE_USER" NUMBER(12,0), 
"RET_CODE" VARCHAR2(16 CHAR), 
"RET_MSG" VARCHAR2(128 CHAR), 
"MERCHANT_NO" VARCHAR2(32 CHAR), 
 PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "QRPAY"."RISK_RECORD_BACK"."RET_CODE" IS '返回码';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD_BACK"."RET_MSG" IS '返回描述';
COMMENT ON COLUMN "QRPAY"."RISK_RECORD_BACK"."MERCHANT_NO" IS '平台商户号';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_RECORD_BACK_ID_TRG" BEFORE INSERT OR UPDATE ON  RISK_RECORD_BACK
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT  RISK_RECORD_BACK_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_RECORD_BACK;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_RECORD_BACK_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;


CREATE SEQUENCE  "QRPAY"."RISK_SYS_USER_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_SYS_USER" 
(	
"ID" NUMBER(12,0), 
"USER_NAME" VARCHAR2(32 CHAR) NOT NULL, 
"PASSWORD" VARCHAR2(32 CHAR) NOT NULL, 
"STATUS" CHAR(1 CHAR) NOT NULL, 
"CREATE_TIME" DATE DEFAULT sysdate, 
"UPDATE_TIME" DATE, 
"FAIL_TIMES" NUMBER(2,0) DEFAULT 0 NOT NULL, 
"REAL_NAME" VARCHAR2(32 CHAR), 
"EMAIL" VARCHAR2(32 CHAR), 
"MOBILE" VARCHAR2(16 CHAR), 
 PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."USER_NAME" IS '用户名';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."PASSWORD" IS '密码';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."STATUS" IS '状态：0正常1关闭2冻结';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."FAIL_TIMES" IS '登录失败次数';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."REAL_NAME" IS '姓名';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."EMAIL" IS '邮箱';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER"."MOBILE" IS '手机号';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_SYS_USER_ID_TRG" BEFORE INSERT OR UPDATE ON  RISK_SYS_USER
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_SYS_USER_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_SYS_USER;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_SYS_USER_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;


CREATE SEQUENCE  "QRPAY"."RISK_SYS_ROLE_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_SYS_ROLE" 
(	"ID" NUMBER(12,0), 
"CODE" VARCHAR2(16 CHAR) NOT NULL, 
"NAME" VARCHAR2(32 CHAR) NOT NULL, 
"CREATE_TIME" DATE DEFAULT sysdate, 
"UPDATE_TIME" DATE, 
 PRIMARY KEY ("ID")
 );
COMMENT ON COLUMN "QRPAY"."RISK_SYS_ROLE"."CODE" IS '角色代码';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_ROLE"."NAME" IS '角色名称';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_SYS_ROLE_ID_TRG" BEFORE INSERT OR UPDATE ON  RISK_SYS_ROLE
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_SYS_ROLE_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_SYS_ROLE;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_SYS_ROLE_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;


CREATE SEQUENCE  "QRPAY"."RISK_SYS_USER_ROLE_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_SYS_USER_ROLE" 
(	
"ID" NUMBER(12,0), 
"USER_ID" NUMBER(12,0) NOT NULL, 
"ROLE_ID" NUMBER(12,0) NOT NULL, 
PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER_ROLE"."USER_ID" IS '用户id';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_USER_ROLE"."ROLE_ID" IS '角色id';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_SYS_USER_ROLE_ID_TRG" BEFORE INSERT OR UPDATE ON  RISK_SYS_USER_ROLE
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_SYS_USER_ROLE_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_SYS_USER_ROLE;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_SYS_USER_ROLE_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;


CREATE SEQUENCE  "QRPAY"."RISK_SYS_AUTH_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_SYS_AUTH" 
(	
"ID" NUMBER(12,0), 
"CODE" VARCHAR2(16 CHAR) NOT NULL, 
"NAME" VARCHAR2(32 CHAR) NOT NULL, 
"CREATE_TIME" DATE DEFAULT sysdate, 
"UPDATE_TIME" DATE, 
"PARENT_ID" NUMBER(12,0), 
PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "QRPAY"."RISK_SYS_AUTH"."CODE" IS '权限代码';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_AUTH"."NAME" IS '权限名称';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_AUTH"."PARENT_ID" IS '父id';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_SYS_AUTH_ID_TRG" BEFORE INSERT OR UPDATE ON  "RISK_SYS_AUTH"
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_SYS_AUTH_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_SYS_AUTH;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT  RISK_SYS_AUTH_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;
 

CREATE SEQUENCE  "QRPAY"."RISK_SYS_ROLE_AUTH_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE TABLE "QRPAY"."RISK_SYS_ROLE_AUTH" 
(	
"ID" NUMBER(12,0), 
"ROLE_ID" NUMBER(12,0) NOT NULL, 
"AUTH_ID" NUMBER(12,0) NOT NULL, 
PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "QRPAY"."RISK_SYS_ROLE_AUTH"."ROLE_ID" IS '角色id';
COMMENT ON COLUMN "QRPAY"."RISK_SYS_ROLE_AUTH"."AUTH_ID" IS '权限id';
CREATE OR REPLACE TRIGGER "QRPAY"."RISK_SYS_ROLE_AUTH_ID_TRG" BEFORE INSERT OR UPDATE ON  "RISK_SYS_ROLE_AUTH"
FOR EACH ROW
DECLARE
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT   RISK_SYS_ROLE_AUTH_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM  RISK_SYS_ROLE_AUTH;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT RISK_SYS_ROLE_AUTH_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;













   