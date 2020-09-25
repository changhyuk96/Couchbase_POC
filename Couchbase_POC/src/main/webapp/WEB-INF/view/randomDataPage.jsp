<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
</head>
<script>
	function randomData() {
		
		var check = inputCheck();
		if(check == false){
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		var data = jQuery("#randomDataForm").serializeArray();

		$.ajax({
			type : "post",
			url : "randomData",
			data : data,
			error : function(xhr, status, error) {
				alert('입력이 잘못되었습니다.');
			},
			success : function(data) {
				alert('요청이 완료되었습니다.');
			}
		});
	}
	
	function inputCheck(){
		let inputText = $("#randomDataForm input");
		
		for(var i=0;i<inputText.length; i++){
			
			if(inputText[i].value == "" || inputText[i].value == null){
				
				return false;
			}
		}		
		return true;
	}
</script>
<body>
	<!-- header.jsp -->
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>

	<div class=container>
		<div class=container-div>
			<h1 style="margin-top:-25px;margin-bottom: 50px;">랜덤 데이터 생성</h1>
			<form id="randomDataForm" name="randomDataForm">
				<div>
				
					# 아이디 사이즈(Byte)<input type="text" id="docIdSize" name="docIdSize"
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" /> 
				</div>
				<div>
					# 문서 사이즈 (Byte)
					<input type="text" id="docSize" name="docSize" 
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
	
				<div>
					# 생성할 문서의 수 <input type="text" name="docCount" 
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
	
				<div>
					# 문서의 종류
					<label for=Binary>Binary</label>
					<input type="radio" name="docType" value="Binary" />
					<label for=Json>JSON</label>
					<input type="radio" name="docType" value="Json" checked />
					
				</div>
	
				<div>
					# 쓰레드 수 <input type="text" name="threadCount" 
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				
	
				<div align="right">
					<button type="button" class="n1qlexcute" onclick="randomData();">실행</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>