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

	/*@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		Map<Integer, List<CateVO>> map = null;
		
		List<CateVO> cateList = new ArrayList<CateVO>();
		
		CateDAO dao = new CateDAO();
		try {
			cateList = dao.getCategorys();
			map = new TreeMap<>();
			
			for(CateVO vo : cateList) {
				map.put(vo.getGroupNo(), dao.getBoards(vo.getGroupNo()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		context.setAttribute("cateList", cateList);
		context.setAttribute("boardList", map);
	}*/
	/*@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		Map<Integer, List<CateVO>> map = null;
		
		List<CateVO> list = new ArrayList<CateVO>();
		List<CateVO> cateList = new ArrayList<CateVO>();
		
		CateDAO dao = new CateDAO();
		try {
			cateList = dao.getCategorys();
			
			map = new TreeMap<>();
			
			for(CateVO vo : cateList) {
				list.add(vo);
				
				List<CateVO> boardList = dao.getBoards(vo.getGroupNo());
				if(boardList != null) {
					for(CateVO cVo : boardList) {
						list.add(cVo);
					}
					map.put(vo.getGroupNo(), list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		context.setAttribute("cateList", map);
	}*/
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		Map<Integer, CateVO> map = new TreeMap<>();
		Map<Integer, List<CateVO>> navMap = new TreeMap<>();
		
		List<CateVO> cateList = new ArrayList<CateVO>();
		List<CateVO> boardList = new ArrayList<CateVO>();
		
		CateDAO dao = new CateDAO();
		try {
			cateList = dao.getCategorys();
			
			for(CateVO vo : cateList) {
				map.put(vo.getCateNo(), vo);
				
				boardList = dao.getBoards(vo.getGroupNo());
				navMap.put(vo.getGroupNo(), boardList);
				if(boardList != null) {
					for(CateVO cVo : boardList) {
						map.put(cVo.getCateNo(), cVo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		context.setAttribute("navCateList", cateList);
		context.setAttribute("navBoardList", navMap);
		context.setAttribute("cateList", map);
	}
}