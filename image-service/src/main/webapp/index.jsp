<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="upload?scerectKey=DSJK2017dsaDLD" method="post" enctype="multipart/form-data">
		文件：<input type="file" name="file">
		<br> 
		文件名：<input type="text" name="newFileName">
		<br> 
		存放位置：<input type="text" name="folder">
		<br>
		<button type="submit">上传</button>
	</form>
	
</body>
</html>