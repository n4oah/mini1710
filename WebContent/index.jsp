<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인페이지</title>
<%@ include file="/jsp/include/basic.jsp"%>
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	<%@ include file="/jsp/include/bottom.jsp" %>
	
	<script type="text/javascript">
		$(".navbar.navbar-default ul.nav.navbar-nav > li").removeClass("active");
		$(".navbar.navbar-default ul.nav.navbar-nav > li:eq(0)").addClass("active");
	</script>
</body>
</html>