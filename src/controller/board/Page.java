package controller.board;

public class Page {
	private int cateNo, pageNo;
	static final private int MAX_PAGEING_BOARD = 10;
	
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getBegin() {
		return (pageNo - 1) * MAX_PAGEING_BOARD + 1;
	}

	public int getEnd() {
		return (pageNo * MAX_PAGEING_BOARD);
	}

	@Override
	public String toString() {
		return "Page [cateNo=" + cateNo + ", pageNo=" + pageNo + "]";
	}
}