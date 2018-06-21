$(document).ready(function () {
	
	$("form").bootstrapValidator({
		feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
		},
        fields: {
        	bank_card_no: {
                validators: {
                    regexp: {
                        regexp: /^(\d*)$/,
                        message: '银行卡格式错误'
                    },
                }
        	},
        	cert_no: {
        		validators: {
        			regexp: {
        				regexp: /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([\d|x|X]{1})$/,
        				message: '身份证格式错误'
        			},
        		}
        	},
        	business_license_no: {
        		validators: {
        			regexp: {
        				regexp: /^(\d{15}|\d{18})$/,
        				message: '营业执照号格式错误'
        			},
        		}
        	},
        	mobile: {
        		validators: {
        			regexp: {
        				regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
        				message: '手机号格式错误'
        			},
        		}
        	},
        	email_address: {
        		validators: {
        			regexp: {
        				regexp: /(\S)+[@]{1}(\S)+[.]{1}(\w)+/,
        				message: '邮箱格式错误'
        			},
        		}
        	},
            
        }
    });
	
});

function submitForm(obj){
	var $form = $(obj).parents("form").eq(0);
	$form.data("bootstrapValidator").validate(); // 手动验证
	var isValid = $form.data("bootstrapValidator").isValid(); // 是否验证成功
	if(isValid){
		
		var dialog = showLoading();
		var url = $form.attr("action");
		$.ajax({
			url: url,
			type: "post",
			async: true, // 是否异步请求（此处需这只为异步请求true，否则bootstrap的modal不会顺序显示）
			cache: false, // 是否缓存此页面，每次都请求服务器
			contentType: "application/x-www-form-urlencoded", // 内容编码类型，默认
			dataType: "json", // 预期服务器返回数据格式
			timeout: 60000, // 超时时间，60s
			data: $form.serialize(), // 请求参数
			beforeSend: function(xhr){
				
			}, // 发送请求预处理
			error: function(xhr, errMsg, e){
				bootbox.alert("请求服务器失败！");
			}, // 请求服务器失败的处理
			dataFilter: function(data, type){
				return data;
			}, // 请求成功预处理，返回的值为success的参数data
			success: function(data){
				bootbox.alert(data.msg+"！",function(){
					if(data.result){
						// 成功
						location.href=ctx()+"/risk";
					}else{
						// 失败
					}
				});
				
			}, // 请求服务器成功的处理
			complete: function(xhr, ts){
				hideDialog(dialog);
			} // 请求完成的处理，无论成功或失败
			
		});
		
	}
}