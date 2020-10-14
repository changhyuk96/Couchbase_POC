<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/bootstrap_backup.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<title>Couchbase</title>
</head>
<script>

	function openDocument(docId){
		
		// window.open('documentDetails?documentId=${list.id }','팝업스','width=500, height=300, left=3500, top=300, menubar=no, status=no, toolbar=no');
		
		var popupX = (window.screen.width/2)-(500/2);
		var popupY = (window.screen.height/2)-(500/2);
		var left = (screen.availWidth)-popupX;
		
		if( window.screenLeft < 0){
			left += window.screen.width*-1;
			}
			else if ( window.screenLeft > window.screen.width ){
			left += window.screen.width;
		}
		
		var document_window = window.open('documentDetails?documentId='+docId+'&bucketName=${bucketName}','팝업스','width=500, height=560, left='+left+', top='+popupY+', menubar=no, status=no, toolbar=no')
		
	}
	
	function newDocument(){
		
		var popupX = (window.screen.width/2)-(500/2);
		var popupY = (window.screen.height/2)-(500/2);
		var left = (screen.availWidth)-popupX;
		
		if( window.screenLeft < 0){
			left += window.screen.width*-1;
			}
			else if ( window.screenLeft > window.screen.width ){
			left += window.screen.width;
		}
		
		var document_window = window.open('newDocument?bucketName=${bucketName}','팝업스','width=500, height=530, left='+left+', top='+popupY+', menubar=no, status=no, toolbar=no')
	}
	
	function bucketChange(){
		if(document.getElementById("bucketName").value=='-Select Bucket-')
			return;
		
		document.getElementById("documentPageForm").submit();
	}
</script>
<body>
	
	
	<!-- header.jsp -->
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>
	

	<div class=container>
		<div class=container-div style="width:50%;">

			<h1>Document <c:if test="${not empty documentList}">  : ${bucketName } Bucket </c:if></h1> 
			
			<div style="text-align: center;width:1000px;">


				<c:if test="${empty documentList}">
					<h2>문서를 확인하려면</h2>
					<h2>서버 연결 및 환경 설정을 해주십시오.</h2>
				</c:if>


				<c:if test="${not empty documentList}">
				<div style="float:right;margin-top:-20px;">
					<form id=documentPageForm action=documentPage style=display:inline-block;>
						<input type=text name=limit style="margin-right:25px;margin-left:-5px;" placeholder=default=30>
						<label style=margin-left:20px;>Limit:</label>
						
						<select name=bucketName onchange=bucketChange() id=bucketName>
								<option value='-Select Bucket-'>-Select Bucket-</option>
							<c:forEach items="${bucketList }" var="list">
								<option value=${list.name } <c:if test="${list.name eq bucketName}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</select>
					</form>
				
					<button class=n1qlexcute onclick="newDocument();">Document 추가</button>
				</div>	
				
					<div>
						
					</div>
				
					<table class="table table-striped table-hover">
						<tr>
							<th style="text-align: center;">문서 ID</th>
							<th style="text-align: center;">문서 내용</th>
						</tr>
						<c:forEach items="${documentList }" var="list">
							<tr>
								<td><a href="#" onclick="openDocument('${list.id}')">${list.id }</a>
								</td>

								<td><span>${list.content }</span></td>
							</tr>

						</c:forEach>
					</table>
				</c:if>

			</div>
		</div>
	</div>

</body>
</html>