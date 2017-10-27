package controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.MemberVO;

public class Logout implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("user");
		if(vo != null) {
			session.invalidate();
		}
		
		ActionForward forward = new ActionForward();
		forward.setForward(false);
		forward.setPath(request.getContextPath() + "/home.do");
		return forward;
	}
}