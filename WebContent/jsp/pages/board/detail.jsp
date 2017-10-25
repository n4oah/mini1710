<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${requestScope.name}</title>
<%@ include file="/jsp/include/basic.jsp"%>
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	
	<style>
		html, body{
			width: 100%;
			height: 100%;
		}
		
		.mainbox {
			width: 100%;
			height: calc(100% - 90px);
			background-color: #5D5D5D; /* 배경색 */
		}
	</style>
	<div class="mainbox">
	
	</div>
	
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>