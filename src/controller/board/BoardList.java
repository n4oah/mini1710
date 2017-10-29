package controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PageResult;
import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;
import model.CateDAO;
import model.CateVO;

public class BoardList implements Action {
	private int cateNo, pageNo;
	private PageResult pageResult;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = new ActionForward();
		try {
			cateNo = -1;
			cateNo = Integer.parseInt(request.getParameter("cateNo"));
			
			try {
				pageNo = 1;
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			} catch (NumberFormatException e) {}
			
			List<BoardVO> list = getPageBoardList();
			
			request.setAttribute("boardList", list);
			request.setAttribute("cateNo", cateNo);
			request.setAttribute("pageResult", pageResult);
			
			forward.setForward(true);
			forward.setPath("/jsp/pages/board/list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
	
	private List<BoardVO> getPageBoardList() throws Exception {
		List<BoardVO> vo = null;
		
		BoardDAO dao = new BoardDAO();
		Page page = new Page(pageNo);
		page.setCateNo(cateNo);
		
		vo = dao.getBoardList(page);
		
		int count = dao.getBoardListCount(cateNo);
		
		pageResult = new PageResult(pageNo, count);
		return vo;
	}
}