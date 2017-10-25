package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import model.CateDAO;
import model.CateVO;

public class MyContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		Map<Integer, List<CateVO>> map = null;
		
		List<CateVO> cateList = new ArrayList<CateVO>();
		List<CateVO> boardList = new ArrayList<CateVO>();
		
		CateDAO dao = new CateDAO();
		try {
			cateList = dao.getCategorys();
			map = new TreeMap<>();
			
			for(CateVO vo : cateList) {
				boardList = dao.getBoards(vo.getGroup_num());
				map.put(vo.getGroup_num(), boardList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		context.setAttribute("cateList", cateList);
		context.setAttribute("boardList", map);
	}
}