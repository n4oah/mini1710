package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;

public class BoardModify implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		int no = Integer.parseInt(request.getParameter("no"));
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVO vo = new BoardVO();
		vo.setBoardNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		
		BoardDAO dao = new BoardDAO();
		int chk = 0;
		try {
			chk = dao.BoardModify(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		
		if(chk > 0) {
			forward.setForward(false);
			forward.setPath(request.getContextPath() + "/board/detail.do?no=" + no);
		}
		return forward;
	}	
}