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
			<a href="/"> ���� ���� ���� </a>
		</div>
		<div class="menu">
			<a href="nodeManagePage"> ��� </a>
		</div>
		<div class="menu">
			<a href="bucketManagePage"> ��Ŷ </a>
		</div>
		<div class="menu">
			<a href="documentPage"> ���� </a>
		</div>

		<div class="menu">
			<a href="queryExcutePage"> ���� </a>
		</div>
		<div class="menu">
			<a href="randomDataPage"> ���� ������ </a>
		</div>
		<div class="menu">
			<a href="CsvOrFileUpsertPage"> ���� ���ε� </a>
		</div>
		<div class="menu">
			<a href="logPage"> �α� </a>
		</div>
		<div class="menu">
			<div class=dropdown>
				<a href="settings"> ȯ�� ���� </a>
				<div class="dropdown-content">
				    <a href="settings">�Ϲ� ����</a>
				    <a href="autoCompactionPage">�ڵ� ����</a>
				    <a href="#">�̸��� �˸�</a>
				    <a href="#">���� ��Ŷ</a>
				  </div>
			</div>
		</div>
	</div>

</body>
</html>