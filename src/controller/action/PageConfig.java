package controller.action;

public class PageConfig {
	private String path;
	private boolean loggin;

	public PageConfig(String path, boolean loggin) {
		this.path = path;
		this.loggin = loggin;
	}
	
	public String getPath() {
		return path;
	}

	public boolean isLoggin() {
		return loggin;
	}
}