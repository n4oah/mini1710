<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인페이지</title>
<%@ include file="/jsp/include/basic.jsp"%>
<link rel="stylesheet" href="./css/main.css" />
</head>
<body>
	<c:set var="pageUri" value="${pageContext.request.contextPath}/home.do"></c:set>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	
	<div class="mainbox">
		<p class="title">개발자 : 김호진, 권우용</p>
		<p class="title2" style="color:white; font-size:3px;">젓가락:양희석</p>
	</div>
	
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>