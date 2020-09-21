<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
<link rel="stylesheet" type="text/css" href="static/css/index.css">
</head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	function createNewBucket() {
		
		var check = inputCheck();
		if(check == false){
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		var data = jQuery("#createBucket").serializeArray();
	
		$.ajax({
			type : "post",
			url : "createBucket",
			data : data,
			error : function(xhr, status, error) {
				alert('에러입니다. 정상적인 값을 입력해주세요.');
			},
			success : function(data) {
				alert("버킷이 생성되었습니다.");
			}
		});
	}
	
	function inputCheck(){
		let inputText = $("#createBucket input");
		
		for(var i=0;i<inputText.length; i++){
			
			if(inputText[i].value == "" || inputText[i].value == null){
				
				return false;
			}
		}		
		return true;
	}
</script>
<body>
	<div id="header">
		<a> <img src="static/image/cb_logo_bug_white_2.svg"
			alt="Couchbase Server" class="logobug">
		</a>
	</div>
	<div id=header-tab>
		<div class="menu">
			<a href="settings"> 서버 연결 및 환경 설정 </a>
		</div>
		<div class="menu">
			<a href="randomDataPage"> 랜덤 데이터 설정 </a>
		</div>
		<div class="menu">
			<a href="createBucketPage"> 버킷 생성 </a>
		</div>
		<div class="menu">
			<a href="documentManagePage"> 문서 작업 </a>
		</div>
		<div class="menu">
			<a href="CsvOrFileUpsertPage"> Csv 및 Json 파일 Upsert </a>
		</div>
	</div>



	<div class=container>
		<div class=container-div>
			<h1 style="margin-top: -50px;">버킷 생성</h1><br><br>
			<form id="createBucket" name="createBucket" >
				<div >
					# 호스트 이름 <input type="text" name="hostName" />
				</div>
	
				<div>
					# 유저 이름 <input type="text" name="userName" />
				</div>
	
				<div>
					# 패스워드 <input type="password" name="userPassword" />
				</div>
	
				<div>
					# 버킷의 이름 <input type="text" id="newBucketName" name="newBucketName">
				</div>
	
				<div>
					# 버킷 타입 <select name="newBucketType">
						<option selected value="Couchbase">Couchbase</option>
						<option value="Ephemeral">Ephemeral</option>
						<option value="Memcached">Memcached</option>
					</select>
				</div>
	
				<div>
					# 메모리 할당 <input type="text" name="bucketMemory"
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
	
				<div>
					# 복제본 수 <select name="newBucketReplicas">
						<option selected value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</select>
				</div>
				<div>
					# Index 복제 여부
						
					<label for="False">False</label>
					<input type="radio" name="indexReplicaEnable" value="false" checked /> 
					
					<label for="True">True</label>
					<input type="radio" name="indexReplicaEnable" value="true" />
				</div>
	
				<div>
					# Flush 기능 활성화 여부
					<label for="False">False</label>
					<input type="radio" name="flushEnable" value="false" checked /> 
					
					<label for="True">True</label>
					<input type="radio" name="flushEnable" value="true" />	
						
				</div>
	
	
				<div align="right">
					<button type="button" class="n1qlexcute" onclick="createNewBucket();">실행</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>