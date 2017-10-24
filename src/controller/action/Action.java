package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public Action execute(HttpServletRequest request, HttpServletResponse response);
}