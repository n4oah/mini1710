<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String id = null;
	id = request.getParameter("chkId");
	boolean result = false;
	if(id != null)
	{
		MemberDAO dao = new MemberDAO();
		result = dao.getOverlapId(id);
		out.clear();
		if(result == true)
		{
			out.print("0");
		}
		else out.print("1");
	}
%>