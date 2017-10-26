package common;

public class StringMethod {
	public static String capitalizeFully(String str) {
		return str.toLowerCase().substring(0, 1).toUpperCase() + str.substring(1);
	}
}