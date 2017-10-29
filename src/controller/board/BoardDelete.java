package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.BoardDAO;
import model.BoardVO;
import model.MemberVO;

public class BoardDelete implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		int cateNo = Integer.parseInt(request.getParameter("cateNo"));
		int boardNo = Integer.parseInt(request.getParameter("no"));
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		BoardDAO dao = new BoardDAO();
		BoardVO vo = null;
		try {
			vo = dao.getBoardDetail(boardNo);
			if(user.getId().equals(vo.getWriterId())) {
				int chk = dao.BoardDelete(vo);
				
				if(chk > 0) {
					forward.setForward(false);
					forward.setPath(request.getContextPath() + "/board/list.do?cateNo=" + cateNo + "&pageNo=" + pageNo);
				} else {
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return forward;
	}
}