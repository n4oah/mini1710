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

public class BoardWriter implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		MemberVO memVo = (MemberVO)session.getAttribute("user");
		if(memVo != null) {
			
			int cateNo = Integer.parseInt(request.getParameter("cateNo"));
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardDAO dao = new BoardDAO();
			BoardVO vo = new BoardVO();
			try {
				vo.setBoardNo(dao.getBoardSeq());
				vo.setCateNo( cateNo );
				vo.setContent(content);
				vo.setTitle(title);
				vo.setUsed(true);
				vo.setWriter(memVo.getNickname());
				vo.setWriterId(memVo.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			int chk = 0;
			try {
				chk = dao.BoardInsert(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(chk > 0) {
				forward.setForward(true);
				forward.setPath("/board/detail.do?no=" + vo.getBoardNo());
			}
		} else {
			forward.setForward(true);
			forward.setPath("/login/signinform.do");
		}
		return forward;
	}
}
