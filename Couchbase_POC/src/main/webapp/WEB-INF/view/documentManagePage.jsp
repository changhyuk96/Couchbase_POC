<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
</head>
<script>
	function sdkJobExcute() {
		
		var check = inputCheck();
		if(check == false){
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		var data = jQuery("#sdkJobsForm").serializeArray();
	
		$.ajax({
				type : "post",
				url : "sdkJobExcute",
				data : data,
				error : function(xhr, status, error) {
					$('#sdkJobOutput').val("에러가 발생하였습니다. 문서의 ID가 중복 혹은 존재하지 않습니다.");
				},
				success : function(data) {

					var obj = eval(data);
					if (obj.status == "success") {
						$('#sdkJobOutput').val(obj.allRows);
						var ugly = document.getElementById('sdkJobOutput').value;
						var obj = JSON.parse(ugly);
						var pretty = JSON.stringify(obj, undefined, 4);
						document.getElementById('sdkJobOutput').value = pretty;
					} else if (obj.status != null) {
						$('#sdkJobOutput').val(obj.error);
					} else {
						$('#sdkJobOutput').val(obj.result);
						var ugly = document.getElementById('sdkJobOutput').value;
						var obj = JSON.parse(ugly);
						var pretty = JSON.stringify(obj, undefined, 4);
						document.getElementById('sdkJobOutput').value = pretty;
					}
				}
			});
	}
	
	function jobChange(obj) {
		var job = obj.value;
		
		if (job == "n1ql") {
			document.getElementById('sdkWriInput').style.visibility = 'visible'
			document.getElementById('documentId').style.visibility = 'hidden'
		} else if (job == "write") {
			document.getElementById('sdkWriInput').style.visibility = 'visible'
			document.getElementById('documentId').style.visibility = 'visible'
		} else if (job == "read") {
			document.getElementById('sdkWriInput').style.visibility = 'hidden'
			document.getElementById('documentId').style.visibility = 'visible'
		} else {
			document.getElementById('sdkWriInput').style.visibility = 'hidden'
			document.getElementById('documentId').style.visibility = 'visible'
		}
	}
	
	function inputCheck(){
		let inputText = $("#sdkJobsForm input");
		let inputTextArea = $("#sdkJobsForm textarea");
		
		for(var i=0;i<inputText.length; i++){
			
			if(inputText[i].value == "" || inputText[i].value == null){
				
				if(document.getElementById('documentId').style.visibility == 'visible'){
					return false;
				}
			}
		}		
		
		if(inputTextArea[0].value == "" || inputTextArea[0].value == null){
			
			if(document.getElementById('sdkWriInput').style.visibility == 'visible'){
				return false;
			}
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
			<h1 style="margin-top:-25px;margin-bottom: 50px;"> 문서 작업</h1>
			<form id="sdkJobsForm" name="sdkJobsForm">
				<div>
					# 작업 종류 : 

					<label style="margin-top:0px;">N1QL</label>
					<input type="radio" name="sdkJobType" value="n1ql" onclick="jobChange(this)" />

					<label style="margin-top:0px;">삭제</label>
					<input type="radio" name="sdkJobType" value="delete" onclick="jobChange(this)" />

					<label style="margin-top:0px;">쓰기</label>
					<input type="radio" name="sdkJobType" value="write" onclick="jobChange(this)" />
													  	
					<label style="margin-top:0px;">읽기</label>
					<input type="radio" name="sdkJobType" value="read" onclick="jobChange(this)" checked />
				</div>
				<div id="documentId" style="visibility: visible; float:middle;">
					문서 아이디:
					<input type="text" id="sdkJobDocId" name=sdkJobDocId />
				</div>
				<textarea id="sdkWriInput" name="sdkWriInput"
				placeholder="쓰기 작업 수행 시 json 문서를, N1QL 작성 시 쿼리문을 작성해주세요."
				style="visibility: hidden;"></textarea>
	
				<div align="right" >
					<button type="button" class="n1qlexcute" onclick="reset();"
					style="margin-right:50px; margin-top:30px;">값 초기화</button>
					<button type="button" class="n1qlexcute" onclick="sdkJobExcute();">실행</button>
				</div>
			</form>
		</div>
		
		<div class=container-div style="margin-left:200px;">
			<h1 style="margin-top:-25px;margin-bottom: 50px;"> 작업 결과 </h1>
			<textarea id="sdkJobOutput" name="sdkJobOutput" readonly
					placeholder="작업을 실행해주세요." style="width:500px; height:500px;">
			</textarea>
		</div>
	</div>

</body>
</html>