package controller.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/aosdk")
public class MainServlet extends HttpServlet {
	Map<String, String> map;
	
	@Override
	public void init() throws ServletException {
		map = new HashMap<String, String>();
		
		map.put("main.do", "/index.jsp");
		map.put("errorPage.do", "/index.jsp");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uri = request.getRequestURI().substring(path.length() + 1);
		
		ActionForward actForward = new ActionForward();
		
		Iterator<String> it = map.keySet().iterator();
		
		String key = null;
		while(it.hasNext()) {
			if(uri.equals(key = it.next())) {
				String value = map.get(key);
				
				if(value.lastIndexOf(".jsp") != -1) {
					System.out.println("aabb");
					
					actForward.setForward(true);
					actForward.setPath(path + value);
				} else {
					
				}
				break;
			}
			else if(!it.hasNext()) {
				System.out.println("asdiojasfj");
				
				actForward.setForward(false);
				actForward.setPath(path + "/errorPage");
			}
		}
		
		if(actForward.isForward()) {
			System.out.println(actForward.getPath());
			
			RequestDispatcher rd = request.getRequestDispatcher(actForward.getPath());
			rd.forward(request, response);
		} else {
			response.sendRedirect(actForward.getPath());
		}
	}
}