package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;

public class BoardDetail implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		int boardNo = Integer.parseInt(request.getParameter("no"));
		
		try {
			BoardVO vo = new BoardDAO().getBoardDetail(boardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setForward(true);
		forward.setPath("/jsp/board/pages/detail.jsp");
		return forward;
	}
}