<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<link rel="stylesheet" type="text/css" href="lib/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/demo.css">
<script type="text/javascript" src="lib/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="js/getting-started.js"></script>
</head>
<body>
<div id="uploader" class="wu-example">
    用来存放文件信息
    <div id="thelist" class="uploader-list"></div>
    <div class="btns">
        <div id="picker">选择文件</div>
        <button id="ctlBtn" class="btn btn-default">开始上传</button>
    </div>
</div>
<!-- dom结构部分 -->
<div id="uploader-demo" class="wu-example">
    用来存放item
    <div id="fileList" class="uploader-list"></div>
    <div id="filePicker">选择图片</div>
</div>
</body>
</html>