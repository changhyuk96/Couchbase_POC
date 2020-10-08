<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
<style>
	textarea {
	width: 90%;
	height: 180px;
	background-color: #ebecf0;
}
</style>
</head>
<script>
	function queryExcute() {
		
		var check = inputCheck();
		if(check == false){
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		var data = jQuery("#queryForm").serializeArray();
	
		$.ajax({
				type : "post",
				url : "getQueryResult",
				data : data,
				error : function(xhr, status, error) {
					$('#queryResult').val("에러가 발생하였습니다. 문서의 ID가 중복 혹은 존재하지 않습니다.");
				},
				success : function(data) {

					var obj = eval(data);
					if (obj.status == "success") {
						$('#queryResult').val(obj.allRows);
						var ugly = document.getElementById('queryResult').value;
						var obj = JSON.parse(ugly);
						var pretty = JSON.stringify(obj, undefined, 4);
						document.getElementById('queryResult').value = pretty;
					} else if (obj.status != null) {
						$('#queryResult').val(obj.error);
					} else {
						$('#queryResult').val(obj.result);
						var ugly = document.getElementById('queryResult').value;
						var obj = JSON.parse(ugly);
						var pretty = JSON.stringify(obj, undefined, 4);
						document.getElementById('queryResult').value = pretty;
					}
				}
			});
	}
	
	function inputCheck(){
		let inputTextArea = $("#queryForm textarea");
		
		if(inputTextArea[0].value == "" || inputTextArea[0].value == null){
			
			return false;
		}
		return true;
	}
	
	function reset(){
		document.getElementById('documentId').value="";
		document.getElementById('sdkWriInput').value="";
	}
</script>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- header.jsp -->
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>


	<div class=container>
		<div class=container-div>
			<h1 style="margin-bottom: 50px;"> 쿼리 작업</h1>
			<form id="queryForm" name="queryForm">
				<textarea id="queryInput" name="queryInput" placeholder="쿼리문을 작성해주세요." style="width:100%; height:80px; font-size:1.5rem;"></textarea>
	
				<div align="right" >
					<button type="button" class="n1qlexcute" onclick="reset();">값 초기화</button>
					<button type="button" class="n1qlexcute" onclick="queryExcute();">실행</button>
				</div>
			</form>
			</div>
			
			<div class=container-div >
			
			<h1 style="margin-bottom: 50px;"> 작업 결과 </h1>
			<textarea id="queryResult" name="queryResult" readonly
					placeholder="작업을 실행해주세요." style="width:100%; height:500px;">
			</textarea>
		</div>
	</div>

</body>
</html>