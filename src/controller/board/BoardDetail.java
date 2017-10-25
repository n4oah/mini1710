package controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.Action;
import controller.action.ActionForward;
import controller.action.PageConfig;
import model.CateDAO;
import model.CateVO;

public class BoardDetail implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, PageConfig config) {
		String contextPath = request.getContextPath();
		
		int cateNo = Integer.parseInt(config.getParam("cateNo"));
		int groupNo = 0;
		try {
			groupNo = new CateDAO().getGroupNo(cateNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CategoryConfig cateConfig = new CategoryConfig();
		cateConfig.setCateNo(cateNo);
		cateConfig.setGroupNo(groupNo);
		
		request.setAttribute("cateConfig", cateConfig);
		
		ActionForward forward = new ActionForward();
		forward.setForward(true);
		forward.setPath("/jsp/pages/board/detail.jsp");
		return forward;
	}
}