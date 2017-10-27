package controller.action;

import java.util.HashMap;
import java.util.Map;

public class PageConfig {
	private String path;
	private boolean loggin;
	private Map<String, String> param;

	public PageConfig(String path) {
		this(path, false);
	}

	public PageConfig(String path, boolean loggin) {
		this.param = new HashMap<>();
		this.path = path;
		this.loggin = loggin;
	}
	
	public void setParam(String key, String value) {
		param.put(key, value);
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getParam(String key) {
		return param.get(key);
	}
	
	public String getPath() {
		return path;
	}

	public boolean isLoggin() {
		return loggin;
	}
}