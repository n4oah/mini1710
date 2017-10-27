package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;
import model.CateDAO;
import model.CateVO;

public class BoardDetail implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = null;
		int boardNo = Integer.parseInt(request.getParameter("no"));
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {System.out.println("asos");}
		BoardVO vo = null;
		String cateName = null;
		try {
			vo = new BoardDAO().getBoardDetail(boardNo);
			CateVO cateVo = new CateDAO().getCategoryDetail(vo.getCateNo());
			cateName = cateVo.getUriName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("cateName", cateName);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("board", vo);
		
		forward = new ActionForward();
		forward.setForward(true);
		forward.setPath("/jsp/pages/board/detail.jsp");
		return forward;
	}
}