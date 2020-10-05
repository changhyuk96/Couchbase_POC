<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Couchbase</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
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

		var document_window = window.open('documentDetails?documentId='+docId,'팝업스','width=500, height=530, left='+left+', top='+popupY+', menubar=no, status=no, toolbar=no')
		
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
		
		var document_window = window.open('newDocument','팝업스','width=500, height=530, left='+left+', top='+popupY+', menubar=no, status=no, toolbar=no')
	}
</script>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- header.jsp -->
	<c:import url="/WEB-INF/view/header.jsp">
	</c:import>

	<div class=container >
		<div class=container-div>
			<div style="text-align: center;">
				<h1 style="margin-top: -30px;">Document</h1>
				<br>
				<br>

				<c:if test="${empty documentList}">

					<h2>문서를 확인하려면</h2>
					<h2>서버 연결 및 환경 설정을 해주십시오.</h2>
				</c:if>


				<c:if test="${not empty documentList}">
					<table class="table table-striped table-hover">
						<tr>
							<th style="text-align: center;">문서 ID</th>
							<th style="text-align: center;">문서 내용</th>
						</tr>
						<c:forEach items="${documentList }" var="list">
							<tr>
								<!-- window.open('documentDetails?documentId=${list.id }','팝업스','width=500, height=300, left=3500, top=300, menubar=no, status=no, toolbar=no'); -->
								<td><a href="#" onclick="openDocument('${list.id}')">${list.id }</a>
								</td>
								<!--  openDocument('${list.id}') -->

								<td><span>${list.content }</span></td>
							</tr>

						</c:forEach>
					</table>
				</c:if>

			</div>
			<div style="text-align: right; margin-right: 15px;">
				<button class=n1qlexcute onclick="newDocument()">Document 추가</button>
			</div>
		</div>
	</div>

</body>
</html>