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

		var data = jQuery("#clusterSettings").serializeArray();

		$.ajax({
		    contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : "setSettings",
			data : data,
			error : function(xhr, status, error) {
				alert(data.result);
			},
			success : function(data) {
				alert(data.result);
			}
		});
	}

	function inputCheck() {
		let inputText = $("#clusterSettings input");

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

	<div class=container>
		<form id=clusterSettings name="clusterSettings">
			<div class=container-div>
				<h1>클러스터 세팅</h1>
				<br>
				<br>
				<div>
					# 클러스터 이름 <input type="text" name="clusterName" />
				</div>
				<h4 style="margin-top: -15px;">- 메모리 할당량 (단위: MB)</h4>
				<div>
					# Data Service <input type="text" name="dataServiceQuota" />
				</div>

				<div>
					# Index Service <input type="text" name="indexServiceQuota" />
				</div>
				<div>
					# Search Service <input type="text" name="searchServiceQuota" />
				</div>
				<div>
					# Analytics Service <input type="text" name="analyticsServiceQuota" />
				</div>
				<div>
					# Eventing Service <input type="text" name="eventingServiceQuota" />
				</div>

				<div>
					# 최신버전 업데이트 알림
					<input type="checkbox" name="noticeUpdate" value="true" checked>
					<input type='hidden' value='false' name='noticeUpdate'>
				</div>

			</div>

			<div class=container-div
				style="margin-top: 10px; margin-left: 20px; flex-wrap: wrap;">
				<h4>- 노드 가용성</h4>

				<div>
					<input type="checkbox" name="autoFailoverCheck" id="autoFailoverCheck" value="true" checked	onchange="autoFailoverChecking()">
					<input type='hidden' name='autoFailoverCheck' value='false'>
					# <input type="text" name=failoverSecondTime style="width: 50px;" class=doc />초 동안
					<input type="text" name="failoverEvent" style="width: 50px;" class=doc /> 개의 이벤트가 실행되지 못하면 오토 페일오버
				</div>
				<div>
					<input type="checkbox" name="autoFailoverDataError" value="true" class=failoverGroup>
					<input type='hidden' name='autoFailoverDataError' value='false'>
					# 지속적으로 <input type="text" name="autoFailoverDataErrorSecondTime" style="width: 50px;" class=doc />
					초 동안 디스크에 데이터 읽고/쓰기 오류가 발생하면 오토 페일 오버
				</div>
				<div>
					<input type="checkbox" name="autoFailoverServerGroup" value="true" class=failoverGroup>
					<input type='hidden' name="autoFailoverServerGroup" value='false'>
					 # 서버 그룹의 오토 페일오버 가능 여부
				</div>
				<div>
					<input type="checkbox" name="autoFailoverStopRebalance" value="true" checked class=failoverGroup>
					<input type='hidden' name="autoFailoverStopRebalance" value='false'>
					# 오토 페일오버 수행 시 리밸런싱 중단 가능 여부
				</div>
				<div>
					<input type='hidden' name="autoReprovisioning" value="false">
					<input type="checkbox" name="autoReprovisioning" value="true" >
					# Ephemeral 버킷을 포함하는 노드를 사용할 수 없게 되면
					<input type="text" name="autoReprovisioningNode" style="width: 50px;" class=doc />
					노드의 복제본을 활성화 상태로 변경
				</div>
			</div>

			<div class=container-div
				style="margin-top: 10px; margin-left: 20px; flex-wrap: wrap;">
				<h4>- Setting etc</h4>

				<div>
					<h5>
						읽기 쓰레드 수 
						<input type="text" name="readThreadNumber" id=readThreadNumber style="width: 50px; float: right;" class=doc disabled />
					</h5>
					
					<label>Fixed value</label>
					<input type="radio" name="readThread" value="fixedValue" onchange="threadCheck(this);" /> 
					
					<label>DiskI/O optimized</label>
					<input type="radio" name="readThread" value="diskIoOptimized" onchange="threadCheck(this);" />
						<label>Default</label>
					<input type="radio" name="readThread" value="default" checked onchange="threadCheck(this);" />
				</div>

				<div>
					<h5>
						쓰기 쓰레드 수 <input type="text" name="writeThreadNumber" id="writeThreadNumber" style="width: 50px; float: right;" class=doc disabled />
					</h5>
					<label>Fixed value</label>
					<input type="radio" name="writeThread" value="fixedValue" onchange="threadCheck(this);" />
					<label>Disk I/O optimized</label>
					<input type="radio" name="writeThread" value="diskIoOptimized" onchange="threadCheck(this);" />
					<label>Default</label>
					<input type="radio" name="writeThread" value="default" checked onchange="threadCheck(this);" />
				</div>

				<div>
					<h5>인덱스 저장 모드</h5>

					<label>Memory-Optimized</label>
					<input type="radio" name="IndexStorageMode" value="Memory-Optimized" />
					<label>Standard Global Secondary</label>
					<input type="radio" name="IndexStorageMode" value="StandardGlobalSecondary" checked />
				</div>
				<div style="margin-top: 10px;">
					# 인덱서 쓰레드 개수 <input type="text"name="IndexerThreadNumber" style="width: 50px; margin-left: 15px;" class=doc />
				</div>
				<div>
					# 인덱서 로그 레벨 <select name="logLevel" class=docSelect>
						<option value="silent">Silent</option>
						<option value="fatal">Fatal</option>
						<option value="error">error</option>
						<option value="warn">warn</option>
						<option value="info" selected>info</option>
						<option value="verbose">verbose</option>
						<option value="timing">timing</option>
						<option value="debug">debug</option>
						<option value="trace">trace</option>
					</select>
				</div>
				<div>
					# XDCR 최대 프로세스 수
					<input type="text"name="XDCRMaximumProcesses" style="" class=doc />
				</div>
				
				
				<div align="right">
					<button type="button" class="n1qlexcute" onclick="setSetting();">실행</button>
				</div>
			</div>

		</form>

	</div>


</body>
</html>