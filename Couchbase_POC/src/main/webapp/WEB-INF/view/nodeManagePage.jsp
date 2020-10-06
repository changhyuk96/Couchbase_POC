<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
</head>
<script>
	function rebalancing(){
		
		$.ajax({
			type : "post",
			url : "rebalancing",
			error : function(xhr, status, error) {
				alert('에러입니다. 정상적인 값을 입력해주세요.');
			},
			success : function(data) {
				alert(data.result);
			}
		});
	}

	function addNodeNow(rebalancingCheck) {

		var check = addInputCheck();
		if (check == false) {
			alert('모든 항목을 입력하고 노드에서 사용할 서비스를 체크해주세요.');
			return;
		}
		
		var data = jQuery("#addNode").serializeArray();

		$.ajax({
			type : "post",
			url : "addNode",
			data : data,
			error : function(xhr, status, error) {
				alert('에러입니다. 정상적인 값을 입력해주세요.');
			},
			success : function(data) {
				alert(data.result);
				location.reload();
			}
		});
	}
	
	function addInputCheck() {
		let inputText = $("#addNode input");

		for (var i = 0; i < inputText.length; i++) {

			if (inputText[i].value == "" || inputText[i].value == null) {

				return false;
			}
		}
		if($('#addNode input[type=checkbox]:checked').length == 0)
			return false;
		
		return true;
	}

	function dropNodeNow(checkNode) {
		
		var check = dropInputCheck();
		if (check == false) {
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		if(checkNode == 1){
			document.dropNode.activeCheck.value = 'active';
		}
		else{
			document.dropNode.activeCheck.value = 'no';
		}

		var data = jQuery("#dropNode").serializeArray();

		$.ajax({
			type : "post",
			url : "dropNode",
			data : data,
			error : function(xhr, status, error) {
				alert('에러입니다. 정상적인 값을 입력해주세요.');
			},
			success : function(data) {
				alert(data.result);
				location.reload();
			}
		});
	}

	function dropInputCheck() {
		let inputText = $("#dropNode input");

		for (var i = 0; i < inputText.length; i++) {

			if (inputText[i].value == "" || inputText[i].value == null) {

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
		<div class=container-div >
			<h1>노드 리스트</h1>
			<div align="right" >
				<button type="button" class="n1qlexcute" onclick="rebalancing();">Rebalance</button>
			</div><br><br>
		<div style="margin-top:-50px;">
			<c:if test="${empty nodeList}">

				<h2> 노드 리스트를 확인하려면 </h2>
				<h2>서버 연결 및 환경 설정을 해주십시오. </h2>
			</c:if>
			
			<c:if test="${not empty nodeList}">
				<table class="bucketTable">
					<tr>
						<th>노드 이름</th>
						<th>서비스</th>
						<th>CPU</th>
						<th>RAM</th>
						<th>Swap</th>
					</tr>
			
			
				<c:forEach items="${nodeList }" var="list" >

					<tr>
						<td>${list.hostname }</td>
						<td style=width:100px;>${list.service }</td>
						<td>${list.cpu }%</td>
						<td>${list.ram_free}MB / ${list.ram_total }MB</td>
						<td>${list.swap }</td>
					</tr>
					
				</c:forEach>
				</table>
			</c:if>
			</div>
		</div>
		
		<div class=container-div>
			<h1>노드 추가</h1><br><br>
			<form id="addNode" name="addNode" >
				<div >
					# 호스트 이름 <input type="text" name="hostName" />
				</div>
				<div>
     				 <label><input type="checkbox" name="service" value="kv"> Data</label>
     				 <label><input type="checkbox" name="service" value="index"> Index</label>
     				 <label><input type="checkbox" name="service" value="n1ql"> Query</label>
				</div>
				<div>
     				 <label><input type="checkbox" name="service" value="fts"> Search</label>
     				 <label><input type="checkbox" name="service" value="cbas"> Analytics</label>
     				 <label><input type="checkbox" name="service" value="eventing"> Eventing</label>
				</div>
					<input type="hidden" name="rebalancing" value="no" />
				<div align="right">
					<button type="button" class="n1qlexcute" onclick="addNodeNow();">노드 추가</button>
				</div>
			</form>
		</div>
		<div class=container-div>
			<h1>노드 제거</h1><br><br>
			<form id="dropNode" name="dropNode" >
				<div >
					# 호스트 이름 <input type="text" name="dropHostName" />
				</div>
					<input type="hidden" name="activeCheck" value="no" />
				<div align="right">
					<button type="button" class="n1qlexcute" onclick="dropNodeNow(0);">비정상 상태인 노드 제거</button>
					<button type="button" class="n1qlexcute" onclick="dropNodeNow(1);">실행중인 노드 제거</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>