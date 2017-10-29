package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;

public class BoardWriterform implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = new ActionForward();
		
		int cateNo = Integer.parseInt(request.getParameter("cateNo"));
		
		request.setAttribute("cateNo", cateNo);
		
		forward.setForward(true);
		forward.setPath("/jsp/pages/board/writerform.jsp");
		return forward;
	}
}