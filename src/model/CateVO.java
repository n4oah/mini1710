package model;

public class CateVO {
	private int cateNo, group_num, order_num;
	private String name, uriName;
	private boolean used;

	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}

	public int getGroup_num() {
		return group_num;
	}

	public void setGroup_num(int group_num) {
		this.group_num = group_num;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
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
		return "CateVO [cateNo=" + cateNo + ", group_num=" + group_num + ", order_num=" + order_num + ", name=" + name
				+ ", uriName=" + uriName + ", used=" + used + "]";
	}
}