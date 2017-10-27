package websocket;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import websocket.chatting.GameSystem;

@WebServlet("/Login")
public class Login extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post");
		req.setCharacterEncoding("utf-8");
		String box = (String)req.getParameter("idbox");
		while(req.getParameterNames().hasMoreElements()) {
			System.out.println(req.getParameterNames().nextElement());
		}
		
		System.out.println("IDBox : " + box);
		req.getSession().setAttribute("id", box);

	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 		req.setCharacterEncoding("utf-8");
		String box = (String)req.getParameter("id");
		
		System.out.println("IDBox : " + box);
		req.getSession().setAttribute("id", box);
		Date d = new Date();
		
		if(req.getSession().getId()!= null)
			req.getSession().setAttribute("id", req.getSession().getId() + d.getTime());
		GameSystem.inputID(box, (String)req.getSession().getAttribute("id") );
		// 로그인 폼으로 이동하기
		RequestDispatcher rd = req.getRequestDispatcher(
				"/games/paintgame.jsp"
		);
		rd.forward(req, res);
	}
}
