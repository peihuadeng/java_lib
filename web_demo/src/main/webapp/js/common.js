//警告框
function alertx(content, title, options) {
	$.jBox.alert(content, title, options);
}
//弹出tip
function showTip(content, icon, options) {
	$.jBox.tip(content, icon, options);
}
//关闭tip
function closeTip() {
	$.jBox.closeTip();
}
//右下角提示框
function messager(content, title, timeout, options) {
	$.jBox.messager(content, title, timeout, options);
}

//默认表单校验
$.validator.setDefaults({
	submitHandler: function(form) {
		showTip("正在提交，请稍等...", "loading");
		form.submit();
	},
	errorPlacement: function(error, element) {
		error.insertAfter(element);
	},
	errorClass: "error",
	errorElement: "label"
});

$(document).ready(function() {
	//默认使用jquery-validation验证表单
	$("form").validate();
	//所有下拉都选用select2
	$("select").select2({language: "zh-CN"});
});

$(window).unload(function() {
	closeTip();
});