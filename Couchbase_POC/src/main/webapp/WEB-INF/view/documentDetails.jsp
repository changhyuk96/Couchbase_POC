<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="static/css/index.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<style>
textarea {
	word-wrap: break-word;
	white-space: pre-wrap;
	white-space: -moz-pre-wrap;
	white-space: -pre-wrap;
	white-space: -o-pre-wrap;
	word-break: break-all;
	width:500px;
	height:500px;
}
</style>
</head>
<script>
	function upsertDocument(){
		
		let data = $("#documentForm").serializeArray();
		
		$.ajax({
			type : "post",
			url : "documentUpsert",
			data : data,
			error : function(xhr, status, error) {
				alert('오류가 발생했습니다. 제대로된 JSON 형식인지 확인해주십시오.');
			},
			success : function(data) {
				alert('수정이 완료되었습니다.');
				window.close();
			}
		});
	}
	
	function deleteDocument(){
		
		let data = $("#documentForm").serializeArray();

		$.ajax({
			type : "post",
			url : "dropDocument",
			data : data,
			error : function(xhr, status, error) {
				alert('오류가 발생했습니다.');
			},
			success : function(data) {
				alert('해당 Document가 삭제되었습니다.');
				window.close();
			}
		});
	}
	
	function closeEvent(){
	    opener.document.location.reload();
	}

</script>

<body onunload="closeEvent();">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div style=text-align:center;margin-top:15px;>
		<form id=documentForm name=documentForm>
			<input type="hidden" name=documentId id=documentId value=${documentId } />
			<textarea id="documentText" name=documentText onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}" ><c:out value="${documentDetails}" /></textarea>
		</form>
	</div>
	
	<div style="text-align:right;margin-right:15px;">
		<button class=n1qlexcute onclick="deleteDocument()">삭제</button>
		<button class=n1qlexcute onclick="upsertDocument()">저장</button>
	</div>
</body>
</html>