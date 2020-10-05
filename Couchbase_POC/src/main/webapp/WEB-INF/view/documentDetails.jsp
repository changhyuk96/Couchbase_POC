<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
textarea {
	word-wrap: break-word;
	white-space: pre-wrap;
	white-space: -moz-pre-wrap;
	white-space: -pre-wrap;
	white-space: -o-pre-wrap;
	word-break: break-all;
	width:500px;
	height:520px;
}
</style>
</head>

<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style=text-align:center;>
	<textarea><c:out value="${documentDetails}" /></textarea>
</div>

</body>
</html>