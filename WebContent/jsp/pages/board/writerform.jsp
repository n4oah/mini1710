<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<%@ include file="/jsp/include/basic.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/basic.css" />
<style>
	div.table{
		margin: 10px;
	}
	.writer-btn {
		padding: 10px;
	}
</style>
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp"%>
	<div class="mainbox">
		<p class="title">글쓰기</p>
			<form action="${pageContext.request.contextPath}/board/writer/${pageUri}" method="post">
			<div class="table">
				<table>
					<tr>
						<th>제목</th>
						<td><input type="text" name="title" /></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea rows="8" cols="80" name="content" ></textarea> </td>
					</tr>
				</table>
			</div>
			<div class="writer-btn" align="center">
				<input type="submit" class="btn btn-primary" value="작성하기" />
				<input type="reset" class="btn btn-primary" value="다시작성" />
			</div>
		</form>
	</div>
	<%@ include file="/jsp/include/bottom.jsp"%>
</body>
</html>