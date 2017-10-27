package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;

public class BoardModifyform implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		int no = Integer.parseInt(request.getParameter("no"));
		
		BoardVO vo = null;
		try {
			vo = new BoardDAO().getBoardDetail(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("board", vo);
		
		ActionForward forward = new ActionForward();
		forward.setForward(true);
		forward.setPath("/jsp/pages/board/modifyform.jsp");
		return forward;
	}
}