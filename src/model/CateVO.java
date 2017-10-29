package model;

public class CateVO {
	private int cateNo, groupNo, orderNum;
	private String name, uriName;
	private boolean used;

	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}
	
	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	@Override
	public String toString() {
		return "CateVO [cateNo=" + cateNo + ", groupNo=" + groupNo + ", orderNum=" + orderNum + ", name=" + name
				+ ", uriName=" + uriName + ", used=" + used + "]";
	}
}