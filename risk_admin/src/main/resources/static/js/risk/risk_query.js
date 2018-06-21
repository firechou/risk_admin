$(document).ready(function(){
	loadData("#query_form","#list_table");
	
});

function submitFormForTable(obj){
	loadData("#query_form","#list_table");
}

function loadData(formId, tableId){
//	var dialog = showLoading();
	$(tableId).bootstrapTable('destroy'); // 清除缓存表格数据
	$(tableId).bootstrapTable({
		striped: true, // 隔行变色
		url: $(formId).attr("action")+"?random="+Math.random(),
		method: "post",
		dataType: "json",
	    pagination: true, //分页
	    sidePagination: "server", //服务端处理分页
	    pageNumber:1, // 默认显示第几页
	    pageSize: 10, // 每页显示条数
//	    sortable: false, 
//	    cache: false, // 默认true 设置为 false 禁用 AJAX 数据缓存
	    contentType : "application/x-www-form-urlencoded", // 参数提交类型，默认以application/json提交
	    queryParamsType : "", // 决定了分页的请求后台参数，默认limit
	    queryParams:function(params){
	    	return $.extend(true, params, $(formId).serializeObject()); // 将表单参数合并到请求参数
	    }, // 请求参数
//	    responseHandler:function(res){ // res为从服务器请求到的数据
//	    	return res;
//	    },
	    columns: [ // 渲染列
                  {
                	  title: '序号',
                      field: 'ROW_ID',
                      align: 'center',
                      valign: 'middle',
                  }, 
                  {
                      title: '流水号',
                      field: 'TRADE_NO',
                      align: 'center',
                      valign: 'middle',
                  }, 
                  {
                	  title: '商户号',
                	  field: 'MERCHANT_NO',
                	  align: 'center',
                	  valign: 'middle',
                  }, 
                  {
                	  title: '商户名称',
                	  field: 'MERCH_NAME',
                	  align: 'center',
                	  valign: 'middle',
                  }, 
                  {
                      title: '风险类型',
                      field: 'RISK_TYPE',
                      align: 'center',
                      valign: 'middle',
                  },
                  {
                      title: '风险等级',
                      field: 'RISK_LEVEL',
                      align: 'center',
                      valign: 'middle',
                  },
                  {
                      title: '状态',
                      field: 'RISK_STATUS',
                      align: 'center',
                      valign: 'middle',
                      formatter:function(value,row,index){
                    	  if(value=="wait_deal"){
                    		  value = '<span class="label label-danger">待处理</span>';
                    	  }
                    	  if(value=="dealed"){
                    		  value = '<span class="label label-success">已处理</span>';
                    	  }
                    	  if(value=="off"){
                    		  value = '<span class="label label-default">已关闭</span>';
                    	  }
                    	  
                    	  return value;
                      }  
                  },
                  {
                      title: '操作',
                      align: 'center',
                      formatter:function(value,row,index){  
		                   var a = '<a href="#" onclick="toHref(\'/risk/input?id='+ row.ID + '\')">处理</a> ';  
		                   var b = '| <a href="#" onclick="del(\'/risk/del?id='+ row.ID +'\')">删除</a> '; 
		                   
		                   // 状态为已处理的不能操作
		                   var risk_status = row.RISK_STATUS;
		                   var c = '';
		                   if(risk_status=='wait_deal'){
		                	   c = '| <a href="#" onclick="toggle(\'/risk/toggle?id='+ row.ID +'\')">关闭</a> ';
		                   }
		                   
		                   return a+b+c;  
                      } 
                  }
              ]
	});
//	hideDialog(dialog);
}

/**
 * 删除风险记录
 */
function del(url){
	url = ctx() + url;
	// 确认
	bootbox.confirm({ 
		  message: "是否确定删除？", 
		  callback: function(result){
			  if(result){
				  // ok
				  ajaxGet(url);
			  }else{
				  // cancel
			  }
		  }
	});
}
/**
 * 开启关闭
 */
function toggle(url){
	url = ctx() + url;
	// 确认
	bootbox.confirm({ 
		  message: "是否确定关闭，关闭后将不能处理？", 
		  callback: function(result){
			  if(result){
				  // ok
				  ajaxGet(url);
			  }else{
				  // cancel
			  }
		  }
	});
}

function ajaxGet(url){
	var dialog = showLoading();
	$.ajax({
		url:url,
		type: "get",
		async: true, // 异步
		cache: false, // 不缓存
		dataType: "json",
		beforeSend: function(xhr){
			
		}, // 请求之前
		error: function(xhr, errMsg, e){
			bootbox.alert("请求服务器失败！");
		}, // 请求失败
		success: function(data){
			bootbox.alert(data.msg+"！",function(){
				if(data.result){
					// 成功
					location.reload(); // 刷新当前页面
				}else{
					// 失败
				}
			});
		}, // 请求成功
		complete: function(xhr, ts){
			hideDialog(dialog);
		}, // 请求完成，无论成功还是失败
	});
}
