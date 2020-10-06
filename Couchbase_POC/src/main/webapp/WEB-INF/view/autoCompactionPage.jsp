<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

</head>
<script>

	function setSetting() {

		var check = inputCheck();
		if (check == false) {
			alert('모든 항목을 입력해주세요.');
			return;
		}

		var data = jQuery("#autoCompactionForm").serializeArray();

		$.ajax({
		    contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : "setSettings",
			data : data,
			error : function(xhr, status, error) {
				alert(data);
			},
			success : function(data) {
				alert(data);
			}
		});
	}

	function inputCheck() {
		let inputText = $("#autoCompactionForm input");

		for (var i = 0; i < inputText.length; i++) {

			if (inputText[i].value == "" || inputText[i].value == null) {
				if(inputText[i].disabled){
					continue;
				}
				return false;
			}
		}
		return true;
	}
	
	function autoFailoverChecking(){
		if(document.getElementById("autoFailoverCheck").checked==false)
			$("input.failoverGroup").attr("disabled", true);
		else
			$("input.failoverGroup").removeAttr("disabled");
	}
	
	function threadCheck(check){
		if(check.value=='fixedValue'){
			if(check.name=='readThread'){
				$("input#readThreadNumber").removeAttr("disabled");
			}
			else{
				$("input#writeThreadNumber").removeAttr("disabled");
			}
		}else{
			
			if(check.name=='readThread'){
				$("input#readThreadNumber").attr("disabled", true);
			}
			else{
				$("input#writeThreadNumber").attr("disabled", true);
			}
		}
	}


</script>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- header.jsp -->
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>

	<div class=container >
		<form id=autoCompactionForm name="autoCompactionForm" >
			<div class=container-div style="border:solid 1px gray;width:100%;">
				<h1>자동 압축</h1><br><br>
				<div>
					<h4 style="margin-top: -15px;">- 메모리 할당량 (단위: MB)</h4>
					<div>
						# Data Service <input type="text" name="dataServiceQuota" />
					</div>
				</div>
			</div>
		</form>
	</div>


</body>
</html>