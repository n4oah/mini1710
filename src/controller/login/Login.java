package controller.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Encryption;
import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.MemberDAO;
import model.MemberVO;

public class Login implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		
		String id = request.getParameter("id");
		String pwd = Encryption.getEncSHA256(request.getParameter("pwd"));
		
		String idRemember = request.getParameter("idRemember");
		Cookie c = new Cookie("cid", id);
		c.setMaxAge(0);
		if(idRemember != null) {
			if(idRemember.equals("Y")) {
				c.setMaxAge(60 * 60 * 24);
				request.setAttribute("cid", id);
			}
		}
		response.addCookie(c);
		
		ActionForward forward = new ActionForward();
		
		MemberDAO dao = new MemberDAO();
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPwd(pwd);
		
		MemberVO user = null;
		try {
			user = dao.getLoginChk(vo);
			if(user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				forward.setForward(false);
				forward.setPath(request.getContextPath() + "/home.do");
			} else {
				forward.setForward(false);
				forward.setPath(request.getContextPath() + "/login/signinform.do");
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
}