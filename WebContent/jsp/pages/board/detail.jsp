<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${board.title}</title>
<%@ include file="/jsp/include/basic.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/basic.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/detail.css" />
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	<div class="mainbox">
		<p class="title">하</p>
		<table class="board-detail" align="center">
			<tr>
				<th>제목</th>
				<td><c:out value="${board.title }" /></td>
			</tr>
			<tr>
				<th>글쓴이</th>
				<td><c:out value="${board.writer }" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><c:out value="${board.content }" /></td>
			</tr>
		</table>
		<div class="button">
			<a href="${pageContext.request.contextPath}/board/modifyform/${cateName}.do?no=${board.boardNo}">수정</a>
			<a href="${pageContext.request.contextPath}/board/delete/${cateName}.do?no=${board.boardNo}">삭제</a>
			<a href="${pageContext.request.contextPath}/board/list/${cateName}.do?pageNo=${pageNo}">목록보기</a>
		</div>
	</div>
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>