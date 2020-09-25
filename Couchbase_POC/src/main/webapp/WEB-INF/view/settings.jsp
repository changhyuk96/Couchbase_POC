<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
</head>

<script>
	function connectionData() {
		
		var check = inputCheck();
		if(check == false){
			alert('모든 항목을 입력해주세요.');
			return;
		}
		
		var data = $("#conDataForm").serializeArray();
		$.ajax({
			type : "post",
			url : "conData",
			data : data,
			error : function(xhr, status, error) {
				alert('입력이 잘못되었습니다.');
			},
			success : function(data) {
				alert('설정이 완료되었습니다.');
			}
		});
	
	}
	
	function inputCheck(){
		let inputText = $("#conDataForm input[type=text]");
		
		for(var i=0;i<inputText.length; i++){
			
			if(inputText[i].value == "" || inputText[i].value == null){
				
				
				if( inputText[i].name =="txtSslKeyLoc" ||
					inputText[i].name =="pwdSslKeyPwd"){
					continue;
					
				}
				
				return false;
			}
		}		
		return true;
	}
	
	function testButton(){
		
		document.querySelector('#txtHostName').value='192.168.0.27';
		document.querySelector('#portNumber').value='8091';
		document.querySelector('#txtUserName').value='Admin';
		document.getElementById("pwdPassword").value='tf4220';
		document.querySelector('#txtBucketName').value='test';
		
		connectionData();
	}
</script>

<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- header.jsp -->
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>

		<div class="float-frame" style="margin:25px;">
			<form id="conDataForm" name="conDataForm" >
				<div><h1>서버 연결 및 환경 구성</h1></div>
					
				<div class="float-division" >
					<h2 style="margin-bottom:-15px;"> - Connection </h2>
					<div>
						# 호스트 이름 <input type="text" name="txtHostName" id="txtHostName" />
					</div>
					
					<div>
						# 포트번호 <input type="text" name="portNumber" id="portNumber" />
					</div>
		
					<div>
						# 유저 이름 <input type="text" name="txtUserName" id ="txtUserName" />
					</div>
		
					<div>
						# 패스워드 <input type="password" name="pwdPassword" id ="pwdPassword" />
					</div>
		
					<div>
						# 버킷 이름 <input type="text" name="txtBucketName" id ="txtBucketName" />
					</div>
					
				<div style="align-items:bottom; text-align:right;">
						<button type="button" class="n1qlexcute" onclick="testButton();">테스트</button>
					</div>
			</div>
			
			<div class="float-division">
				<h2 style="margin-bottom:-15px;"> - Time Out Option </h2>
				<div>
					# Key-Value TimeOut 
					<input type="text" name="txtKeyValueTO" size="10" value=2500
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# View TimeOut 
					<input type="text" name="txtViewTO" size="10" value=75000
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Query TimeOut 
					<input type="text" name="txtQueryTO" size="10" value=75000
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Connect TimeOut 
					<input type="text" name="txtConnectTO" size="10" value=5000
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Disconnect TimeOut 
					<input type="text" name="txtDisConnectTO" size="10" value=25000
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Connect TimeOut 
					<input type="text" name="txtManagementTO" size="10" value=75000
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
			</div>
			
						
			<div class="float-division">
				<h2 style="margin-bottom:-15px; "> - Performance Option </h2>
				<div>
					# 노드당 Key:Value EndPoint
					<input type="text" name="txtKvEndpoints" value=1
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# 노드당 View EndPoint
					<input type="text" name="txtViewEndpoint" value=1
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# 노드당 Query EndPoint 
					<input type="text" name="txtQueryEndpoint" value=1
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# TCP NodlLav
					
					<label>False</label>
					<input type="radio" name="rdoTcpNodelayEnable" value="false" checked />
					
					<label>True</label>
					<input type="radio" name="rdoTcpNodelayEnable" value="true" />
				</div>
			</div>
			
			<div class="float-division">
				<h2 style="margin-bottom:-15px;"> - Advanced Option </h2>
				<div>
					# Ring 버퍼 사이즈 요청
					<input type="text" name="txtRequestBufferSize" value=16384
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Ring 버퍼 사이즈 응답
					<input type="text" name="txtResponseBufferSize"	value=16384
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# 버퍼 풀 활성화
					
					<label>False</label>
					<input type="radio" name="rdoBufferPoolEnab" value="false" checked />
					
					<label>True</label>
					<input type="radio" name="rdoBufferPoolEnab" value="true" />
					
					
				</div>
				<br><br>
				<h2 style="margin-bottom:-15px;"> - Reliablility Option </h2>
					<div>
						# 최대 요청 Lifetime
						<input type="text" name="txtMaxReqLifeTime" 
							value=75000 onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
					</div>
					<div>
						# 소켓 유지 시간
						<input type="text" name="txtKeepAliveInterval" 
							value=30000 onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
					</div>
			</div>
			
			
			<div class="float-division" style="height:auto;">
				<h2 style="margin-bottom:-15px;"> - BootStrap Option </h2>
				
				<div>
					# 암호화 사용
					
					<label>False</label>
					<input type="radio" name="rdoSslEnable" value="false" checked /> 
					
					<label>True</label>
					<input type="radio" name="rdoSslEnable" value="true" />
				</div>
				
				<div>
					# SSL 키 저장소 위치
					<input type="text" name="txtSslKeyLoc" value="" />
				</div>
				
				<div>
					# SSL 키 저장소 비밀번호
					<input type="password" name="pwdSslKeyPwd" value="" />
				</div>
				<div>
					# HTTP를 통한 Config 로드

					<label>False</label>
					<input type="radio" name="rdoHttpEnabled" value="false" /> 
					
					<label>True</label>
					<input type="radio" name="rdoHttpEnabled" value="true" checked />
				</div>
				<div>
					# HTTP 비 암호화 포트 설정
					<input type="text" name="txtHttpDirectPort" value=8891
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# HTTP 암호화 포트 설정
					<input type="text" name="txtHttpSslPort" value=18091
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Carrier Publication을 통해 config 로드
					<label for="False">False</label>
					<input type="radio" name="rdoCarrierEnable" value="false" /> 
					
					<label for="True">True</label>
					<input type="radio" name="rdoCarrierEnable" value="true"  checked /> 
					/>
				</div>
				<div>
					# Carrier 비암호화 포트 설정
					<input type="text" name="txtCarrierDirectPort"
						value=11210 onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# Carrier 암호화 포트 설정
					<input type="text" name="txtCarrierSslPort" value=11207
						onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
				</div>
				<div>
					# DNS SRV 사용
					
					<label for=true>False</label>
					<input type="radio" name="rdoDnsSrvEnable" value="false" checked /> False
					
					<label for=true>True</label>
					<input type="radio" name="rdoDnsSrvEnable" value="true" />
				</div>
				<div>
					# 사용 가능한 변조 토큰
					
					<label for="False">False</label>
					<input type="radio" name="rdoMutationTknEnable" value="false" checked /> 
					
					<label for="True">True</label>
					<input type="radio" name="rdoMutationTknEnable" value="true" />
				</div>
				
					<div style="align-items:bottom; text-align:right;">
					<button type="button" class="n1qlexcute" onclick="connectionData();">저장</button>
				</div>
			</div>


		</form>
	</div>

</body>
</html>