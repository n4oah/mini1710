<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>회원가입</title>
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

.form-horizontal {
	text-align: center;
}

button {
	background-color: #5D5D5D;
}
legend{
	font-weight: bold;
}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login/loginform.css" />
<script type="text/javascript">
	var reg_id_overlap_chk = 0;
	
	function account_exist_chk()
	{
		if(reg_id_overlap_chk != 1)
		{
			alert('아이디를 확인 해주세요.');
			$("#regid").focus();
			return false;
		}
		if($("#pwd").val() != $("#pwd_chk").val())
		{
			alert('비밀번호를 확인해주세요.');
			$("#pwd").val("").focus();
			$("#pwd_chk").val("");
			return false;
		}
		if($("#id").val().match(/^[a-zA-Z0-9]{6,12}$/) == null)
		{
			alert("아이디는 영문과 숫자만 사용 가능하고, 6~12자로 작성 해야합니다.");
			$("#id").focus();
			return false;
		}
		if($("#pwd").val().match(/^[a-zA-Z0-9]{4,10}$/) == null)
		{
			alert("비밀번호는 영문과 숫자만 사용 가능하고, 4~10자로 작성해야 합니다.");
			$("#pwd").focus();
			return false;
		}
		if($("#nickname").val().match(/^[a-zA-Z0-9]{4,10}$/) == null)
		{
			alert("닉네임은 영문과 숫자만 사용 가능하고, 4~10자로 작성해야 합니다.");
			$("#nickname").focus();
			return false;
		}
		if($("#email").val().match(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i) == null)
		{
			alert("이메일은 영문과 숫자만 사용 가능합니다. (ex xxxxx@xxxx.xxx)");
			$("#email").focus();
			return false;
		}
	};
	
	$(function()
	{
		$("#id").blur(function()
		{
			if($("#id").val().match(/^[a-zA-Z0-9]{6,12}$/) == null)
			{
				$("#id_overlap_chk_msg").html("<font color='red'>아이디는 염문과 숫자만 사용 가능하고, 6~12자 이어야합니다.</font>");
				reg_id_overlap_chk = 0;
				return false;
			}
			$.ajax
			({
				type:"POST",
				url: "<c:out value='${pageContext.request.contextPath}/jsp/include/accounts_overlap.jsp'/>",
				data:
				{
					chkId:$("#id").val()
				},
				success:function(data)
				{
					if(data == "1")
					{
						$("#id_overlap_chk_msg").html("<font color='blue'>사용 가능한 아이디입니다.</font>");
						reg_id_overlap_chk = 1;
					}
					else if(data == "0")
					{
						$("#id_overlap_chk_msg").html("<font color='red'>중복된 아이디가 이미 있습니다.</font>");
						reg_id_overlap_chk = 0;
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<%@ include file="/jsp/include/topmenu.jsp"%>
	<div class="mainbox">
		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/login/signout.do" onsubmit="return account_exist_chk()">
			<fieldset>
				<!-- Form Name -->
				<legend>회원가입</legend>

				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label" for="id">아이디</label>
					<div class="col-md-4">
						<input id="id" name="id" type="text"
							placeholder="아이디를 입력해주세요" class="form-control input-md"/>
						<label id="id_overlap_chk_msg"></label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="pwd">비밀번호</label>
					<div class="col-md-4">
						<input id="pwd" name="pwd" type="password"
							placeholder="비밀번호를 입력해주세요" class="form-control input-md"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="pwd_chk">비밀번호 확인</label>
					<div class="col-md-4">
						<input id="pwd_chk" name="pwd_chk" type="password"
							placeholder="비밀번호를 입력해주세요" class="form-control input-md"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="nickname">닉네임</label>
					<div class="col-md-4">
						<input id="nickname" name="nickname" type="text"
							placeholder="닉네임을 입력해주세요" class="form-control input-md"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="email">이메일</label>
					<div class="col-md-4">
						<input id="email" name="email" type="text"
							placeholder="이메일을 입력해주세요" class="form-control input-md"/>
					</div>
				</div>

				<!-- Button -->
				<div class="form-group">
					<label class="col-md-4 control-label" for="signup"></label>
					<div class="col-md-4">
						<input type="submit" id="signup" name="signup" class="btn btn-success" value="회원가입" />
					</div>
				</div>
			</fieldset>
		</form>

	</div>
	<%@ include file="/jsp/include/bottom.jsp"%>
</body>
</html>