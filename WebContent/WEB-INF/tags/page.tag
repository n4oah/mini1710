<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="data" type="common.PageResult" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board/page.css" />
<c:if test="${data.count != 0}">
	<c:set var="pageUri" value="${pageContext.request.contextPath}/board/list/${cateVO.uriName}.do" />
	<nav class="page-nav">
		<ul class="pagination">
			<c:choose>
				<c:when test="${data.prev}">
					<li><a href="${pageUri}?pageNo=${data.beginPage-1}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:when>
			</c:choose>
			<c:forEach var="i" begin="${data.beginPage }" end="${data.endPage }">
				<c:choose>
					<c:when test="${i == data.pageNo}">
						<li class="active"><a href="#${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageUri}?pageNo=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${data.next}">
				<li><a href="${pageUri}?pageNo=${data.endPage+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:when>
			</c:choose>
		</ul>
	</nav>
</c:if>