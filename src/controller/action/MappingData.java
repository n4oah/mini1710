package controller.action;

import java.util.HashMap;

public class MappingData extends HashMap<String, PageConfig> {
	static private MappingData map = null;
	
	private MappingData() {}
	
	static public MappingData getInstance() {
		if(map == null) {
			map = new MappingData();
		}
		return map;
	}
}