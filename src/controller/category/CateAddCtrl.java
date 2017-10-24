package controller.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import model.CateDAO;

public class CateAddCtrl implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		CateDAO dao = new CateDAO();
		try {
			/*dao.addCate("Programming", 1);
			dao.addCate("Java", 1);
			dao.addCate("C언어", 1);
			dao.addCate("Python", 1);
			
			dao.addCate("Community", 2);
			dao.addCate("자유게시판", 2);
			dao.addCate("Q&A 게시판", 2);
			dao.addCate("관리자게시판", 2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setForward(false);
		forward.setPath(request.getContextPath() + "/main.do");
		return forward;
	}
}