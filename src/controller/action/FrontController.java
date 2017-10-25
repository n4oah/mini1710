package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CateDAO;
import model.CateVO;

public class FrontController extends HttpServlet {
	MappingData map = null;
	
	@Override
	public void init() throws ServletException {
		map = MappingData.getInstance();
		map.put("main.do", new PageConfig("/index.jsp", false));
		map.put("loginform.do", new PageConfig("/login.jsp", false));
		map.put("category/cateAdd.do", new PageConfig("controller.category.CateAddCtrl", false));
		
		try {
			CateDAO dao = new CateDAO();
			ArrayList<CateVO> list = (ArrayList<CateVO>)dao.getCategoryBoard();
			
			if(list != null) {
				for(CateVO vo : list) {
					PageConfig config = new PageConfig("controller.board.BoardList", false);
					config.setParam("cateNo", Integer.toString(vo.getCateNo()));
					map.put(vo.getUriName() + ".do", config);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String contextPath = request.getContextPath();
		String uri 	= request.getRequestURI().substring(contextPath.length() + 1);
		
		ActionForward forward = new ActionForward();
		
		String key 		= null;
		String path 	= null;
		
		try {
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				
				if (uri.equals(key = it.next())) {
					path = map.get(key).getPath();

					if (map.get(key).isLoggin() != true) {
						if (path.lastIndexOf(".jsp") != -1) {
							forward.setForward(true);
							forward.setPath(path);
						} else {
							Action action = (Action) Class.forName(path).newInstance();
							forward = action.execute(request, response, map.get(key));
						}
					} else {
						forward.setForward(false);
						forward.setPath("loginform.do");
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(forward.isForward()) {
			RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
			rd.forward(request, response);
		} else {
			response.sendRedirect(forward.getPath());
		}
	}
}