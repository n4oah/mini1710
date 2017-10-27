<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${board.title } 수정</title>
<%@ include file="/jsp/include/basic.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/basic.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/modifyform.css" />
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	
	<div class="mainbox">
		<p class="title">변경하기</p>
	
		<form action="${pageContext.request.contextPath}/board/modify/${pageUri}?no=${board.boardNo}" method="post">
			<table class="board-detail" align="center">
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" value="<c:out value='${board.title }' />"/></td>
				</tr>
				<tr>
					<th>글쓴이</th>
					<td><input type="text" readonly="readonly" name="writer" value="<c:out value="${board.writer }" />"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea rows="8" cols="70" name="content"><c:out value="${board.content }" /></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="변경하기" class="button" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>