$(document).ready(function(){
	
//	bootbox.setLocale("zh_CN"); // 设置bootbox插件中文，默认为en
	bootbox.setDefaults({
		locale: "zh_CN",
		size: "small",
		title: "提示"
	});
	
})

/**
 * 获取项目工程名
 */
function ctx(){
	return $("meta[name='ctx']").attr("content");
}

/**
 * url跳转
 */
function toHref(url){
	showLoading();
	location.href=ctx()+url;
}

function showLoading(){
	return bootbox.dialog({ 
		message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i>加载中...</div>',
		closeButton: false,
		title:null
		})
}
function hideDialog(dialog){
	dialog.modal("hide");
}
function hideAllDialog(){
	bootbox.hideAll();
}



