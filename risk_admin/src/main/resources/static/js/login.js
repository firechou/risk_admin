$(document).ready(function () {
	
	// 登录提示框
	showLoginModal();
	
	$("form").bootstrapValidator({
		feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
		},
        fields: {
        	username: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 0,
                        max: 30,
                        message: '用户名过长'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '用户名只能由字母、数字、点和下划线组成'
                    },
                }
        	},
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 0,
                        max: 30,
                        message: '密码过长'
                    },
                }
            }
        }
    });
	
});

function showLoginModal(){
	var loginMsg = $("#login_msg").text();
	if($.trim(loginMsg)!=""){
		loginMsg = loginMsg=="Bad credentials"?"密码错误":loginMsg;
		bootbox.alert(loginMsg+"！");
	}
}

function forgetPwd(){
	bootbox.alert("请联系管理员找回密码！");
}
