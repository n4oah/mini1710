package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;

public class BoardDetail implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		int boardNo = Integer.parseInt(request.getParameter("no"));
		
		new BoardDAO().getBoardDetail(boardNo);
		
		ActionForward forward = new ActionForward();
		forward.setForward(true);
		forward.setPath("/jsp/board/pages/detail.jsp");
		return forward;
	}
}