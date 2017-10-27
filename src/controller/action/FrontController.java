package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CateDAO;
import model.CateVO;
import model.MemberVO;

public class FrontController extends HttpServlet {
	MappingData map = null;
	
	@Override
	public void init() throws ServletException {
		map = MappingData.getInstance();
		map.put("category/cateAdd.do", new PageConfig("controller.category.CateAddCtrl"));
		map.put("login/signinform.do", new PageConfig("/jsp/pages/login/loginform.jsp"));
		map.put("login/login.do", new PageConfig("controller.login.Login"));
		map.put("login/signoutform.do", new PageConfig("/jsp/pages/login/signoutform.jsp"));
		map.put("login/signout.do", new PageConfig("controller.login.Signout"));
		map.put("quiz.do", new PageConfig("/jsp/quiz/games/paintgame.jsp", true));
		map.put("login/logout.do", new PageConfig("controller.login.Logout", true));
		
		try {
			CateDAO dao = new CateDAO();
			ArrayList<CateVO> list = (ArrayList<CateVO>)dao.getCategoryBoard();
			
			if(list != null) {
				PageConfig[] pc = {
					new PageConfig("list", false),
					new PageConfig("detail", false),
					new PageConfig("modify", true),
					new PageConfig("delete", true),
					new PageConfig("modifyform", true),
					new PageConfig("writer", true),
					new PageConfig("writerform", true)
				};
				
				/*for(CateVO vo : list) {
					if(vo.getGroup_num() == 0) {
						if(vo.getUriName().equals("home")) {
							map.put(vo.getUriName() + ".do", new PageConfig("/index.jsp" , false));
						}
					} else {
						for(PageConfig pcc : pc) {
							PageConfig config = new PageConfig("controller.board.Board" + StringMethod.capitalizeFully(pcc.getPath()), pcc.isLoggin());
							config.setParam("cateNo", Integer.toString(vo.getCateNo()));
							map.put("board/" + pcc.getPath().toLowerCase() +"/" + vo.getUriName() + ".do", config);
						}
					}
				}*/
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
		
		String servletUri = uri.substring(uri.lastIndexOf("/")+1);
		
		ActionForward forward = new ActionForward();
		
		String key 		= null;
		String path 	= null;
		
		try {
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				
				if (uri.equals(key = it.next())) {
					path = map.get(key).getPath();

					if (map.get(key).isLoggin() == true) {
						HttpSession session = request.getSession();
						if((MemberVO)session.getAttribute("user") == null) {
							forward.setForward(false);
							forward.setPath(request.getContextPath() + "/login/signinform.do");
							break;
						}
					}
					if (path.lastIndexOf(".jsp") != -1) {
						forward.setForward(true);
						forward.setPath(path);
					} else {
						Action action = (Action) Class.forName(path).newInstance();
						map.get(key).setParam("pageUri", servletUri);
						forward = action.execute(request, response, map.get(key));
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(forward.isForward()) {
				request.setAttribute("pageUri", servletUri);
				
				RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
				rd.forward(request, response);
			} else {
				response.sendRedirect(forward.getPath());
			}
		}
	}
}