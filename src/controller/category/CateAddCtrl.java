package controller.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.CateDAO;

public class CateAddCtrl implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		CateDAO dao = new CateDAO();
		try {
			/*dao.addCate("Programming", "board/detail/programming", 1);
			dao.addCate("Java", "board/detail/java", 1);
			dao.addCate("C언어", "board/detail/c_language", 1);
			dao.addCate("Python", "board/detail/python", 1);
			
			dao.addCate("Community", "board/detail/community", 2);
			dao.addCate("자유게시판", "board/detail/free_community", 2);
			dao.addCate("Q&A 게시판", "board/detail/newbie", 2);
			dao.addCate("관리자게시판", "board/detail/admin", 2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setForward(false);
		forward.setPath(request.getContextPath() + "/main.do");
		return forward;
	}
}