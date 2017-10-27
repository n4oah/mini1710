<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>로그인</title>
<%@ include file="/jsp/include/basic.jsp"%>
<style>
	html, body {
		width: 100%;
		height: 100%;
		
		background-color: #5D5D5D; /* 배경색 */
	}
	
	.mainbox {
		padding-top: 20px;
		margin: 30px auto;
		background-color: rgba(255, 255, 255, 0.5); 
	}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login/loginform.css" />
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp" %>
	<div class="mainbox">
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">로그인</h3>
						</div>
						<div class="panel-body">
							<form action="${pageContext.request.contextPath}/login/login.do" method="post">
								<fieldset>
									<div class="form-group">
										<input class="form-control" placeholder="id" name="id" value="${cid}" type="text" />
									</div>
									<div class="form-group">
										<input class="form-control" placeholder="pwd" name="pwd" type="password" />
									</div>
									<div class="checkbox">
										<label> <input name="remember" type="checkbox" <c:if test="${not empty cid}">checked</c:if> value="Y"/>아이디 기억</label>
									</div>
									<input class="btn btn-lg btn-success btn-block" type="submit" value="Login"/>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/jsp/include/bottom.jsp" %>
</body>
</html>