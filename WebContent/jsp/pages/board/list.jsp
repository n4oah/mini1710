<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navi" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${cateVO.name }</title>
<%@ include file="/jsp/include/basic.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/list.css" />
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>

	<div class="mainbox">
		<h3 class="title">${cateVO.name}</h3>
		
		<div class="table">
			<table class="points_table">
				<thead>
					<tr>
						<th class="col-xs-1">글번호</th>
						<th class="col-xs-5">제목</th>
						<th class="col-xs-2">작성자</th>
						<th class="col-xs-2">작성날짜</th>
						<th class="col-xs-1">조회수</th>
						<th class="col-xs-1">추천수</th>
					</tr>
				</thead>
	
				<tbody class="points_table_scrollbar">
					<c:forEach var="board" items="${boardList}">
						<tr class="content">
							<td class="col-xs-1"><c:out value="${board.boardNo}" /></td>
							<td class="col-xs-5 title">
								<a href="${pageContext.request.contextPath}/board/detail/${cateVO.uriName}.do?no=${board.boardNo}">
									<c:out value="${board.title }" />
								</a>
							</td>
							<td class="col-xs-2"><c:out value="${board.writer}" /></td>
							<td class="col-xs-2"><fmt:formatDate value="${board.writer_date}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
							<td class="col-xs-1"><c:out value="${board.hitCnt}" /></td>
							<td class="col-xs-1"><c:out value="${board.likeCnt}" /></td>
						</tr>
					</c:forEach>
					<c:if test="${empty boardList}">
						<tr class="content">
							<td class="col-xs-12">입력된 게시물이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<navi:page data="${pageResult}" />
	</div>
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>