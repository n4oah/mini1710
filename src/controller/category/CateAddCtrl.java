package controller.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;
import model.CateDAO;

public class CateAddCtrl implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		CateDAO dao = new CateDAO();
		try {
			dao.addCate("Home", "home", 0);
			
			dao.addCate("Programming", "programming", 1);
			dao.addCate("Java", "java", 1);
			dao.addCate("C언어", "c_language", 1);
			dao.addCate("Python", "python", 1);
			
			dao.addCate("Community", "community", 2);
			dao.addCate("자유게시판", "free_community", 2);
			dao.addCate("Q&A 게시판", "newbie", 2);
			dao.addCate("관리자게시판", "admin", 2);
			
			/*
			BoardDAO dao = new BoardDAO();
			for(int i = 0; i < 10000; i ++) {
				BoardVO vo = new BoardVO();
				vo.setCateNo(62);
				vo.setContent("내용 " + i);
				vo.setTitle("제목 " + i);
				vo.setUsed(true);
				vo.setWriter("글쓴이 " + i);
				vo.setWriterId("글쓴이 아이디" + i);
				dao.BoardInsert(vo);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setForward(false);
		forward.setPath(request.getContextPath() + "/main.do");
		return forward;
	}
}