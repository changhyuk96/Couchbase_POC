<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="static/css/index.css">
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->

</head>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<body>

	<div id="header">
		<a> <img src="static/image/cb_logo_bug_white_2.svg"
			alt="Couchbase Server" class="logobug">
		</a>
	</div>
	<div id=header-tab>
		<div class="menu">
			<a href="settings"> 서버 </a>
		</div>
		<div class="menu">
			<a href="nodeManagePage"> 노드 </a>
		</div>
		<div class="menu">
			<a href="bucketManagePage"> 버킷 </a>
		</div>
		<div class="menu">
			<a href="documentPage"> 문서 </a>
		</div>

		<div class="menu">
			<a href="queryExcutePage"> 쿼리 </a>
		</div>
		<div class="menu">
			<a href="randomDataPage"> 랜덤 데이터 </a>
		</div>
		<div class="menu">
			<a href="CsvOrFileUpsertPage"> 파일 업로드 </a>
		</div>
		<div class="menu">
			<a href="logPage"> 로그 </a>
		</div>
	</div>

</body>
</html>