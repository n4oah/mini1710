package controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Encryption;
import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.MemberDAO;
import model.MemberVO;

public class Signout implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		ActionForward forward = new ActionForward();
		
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String pwd = Encryption.getEncSHA256(request.getParameter("pwd"));
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPwd(pwd);
		vo.setNickname(nickname);
		vo.setEmail(email);
		
		int chk = 0;
		try {
			MemberDAO dao = new MemberDAO();
			chk = dao.MemberInsert(vo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(chk >= 1) {
			forward.setForward(true);
			forward.setPath("/login/signinform.do");
		}
		return forward;
	}
}