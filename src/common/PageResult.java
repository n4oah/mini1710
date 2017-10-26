package common;

/*
 * 페이지에 대한 모듈 처리
 */

public class PageResult {
	private int pageNo, count;
	private int beginPage, endPage;
	private boolean prev, next;
	static final private int MAX_PAGING = 5; 
	
	public PageResult(int pageNo, int count) {
		this.pageNo = pageNo;
		this.count = count;
		
		// 마지막 페이지 번호 구하기
		int lastPage = (count % MAX_PAGING == 0) ? count / MAX_PAGING : (count / MAX_PAGING) + 1;
		
		// -- TAB 블럭 관련 처리
		// 페이지 번호에 따른 현재의 탭 번호, 탭의 시작, 탭의 종료
		int currTab = (pageNo - 1) / MAX_PAGING + 1;
		beginPage = (currTab - 1) * MAX_PAGING + 1;
		endPage = (currTab * MAX_PAGING > lastPage) ? lastPage : (currTab * MAX_PAGING);
		
		prev = beginPage != 1;
		next = endPage != lastPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getCount() {
		return count;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean getPrev() {
		return prev;
	}

	public boolean getNext() {
		return next;
	}
}