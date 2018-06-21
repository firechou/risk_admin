$(document).ready(function(){
	
	initMenuSelect();
	
})

/**
 *	根据URL路径名称匹配选中的菜单
 *	
 */
function initMenuSelect(){
	// 获取需选中菜单名称
	var pathName = window.location.pathname; // 获取url路径
	var indexName = pathName.substring(pathName.lastIndexOf("/")+1, pathName.length);
	indexName = indexName==""?"index":indexName; // 没有匹配上的默认选中首页
	
	var MENU_ID = "#menu"; // 菜单限定ID名称
	$(MENU_ID).find("li.active").removeClass("active");
	var indexHrefs = $(MENU_ID).find("a");
	indexHrefs.each(function(){
		var href = $(this).attr("href");
		var targetName = href.substring(href.lastIndexOf("/")+1, href.length);
		if(indexName==targetName){
			$(this).parentsUntil(MENU_ID).filter("li").addClass("active");
		}
	});
}

/**
 * jquery源码中没有这个方法
 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};


