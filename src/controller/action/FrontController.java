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

public class FrontController extends HttpServlet {
	Map<String, String> map;
	
	@Override
	public void init() throws ServletException {
		map = new HashMap<>();
		
		map.put("main.do", "/index.jsp");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uri = request.getRequestURI().substring(path.length() + 1);
		
		ActionForward actForward = new ActionForward();
		
		Iterator<String> it = map.keySet().iterator();
		
		String key = null;
		String value = null;
		
		while(it.hasNext()) {
			if(uri.equals(key = it.next())) {
				value = map.get(key);
				
				if(value.lastIndexOf(".jsp") != -1) {
					actForward.setForward(true);
					actForward.setPath(value);
				} else {
					
				}
				break;
			}
		}
		
		if(actForward.isForward()) {
			RequestDispatcher rd = request.getRequestDispatcher(actForward.getPath());
			rd.forward(request, response);
		} else {
			response.sendRedirect(actForward.getPath());
		}
	}
}