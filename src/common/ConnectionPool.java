package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	private static final int INIT_COUNT = 3;
	private static List<Connection> free = new ArrayList<>();
	private static List<Connection> used = new ArrayList<>();
	
	private static final String
			ORACLE_HOST = "localhost",
			ORACLE_PORT = "1521",
			ORACLE_SID 	= "xe",
			ORACLE_USER = "mini1710",
			ORACLE_PASS = "1234";
	/*
	private static final String
			ORACLE_HOST = "localhost",
			ORACLE_PORT = "1521",
			ORACLE_SID 	= "system",
			ORACLE_USER = "hr",
			ORACLE_PASS = "oracle";
	 */

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			for (int i = 0; i < INIT_COUNT; i++) {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + ORACLE_HOST + ":" + ORACLE_PORT + ":" + ORACLE_SID, ORACLE_USER, ORACLE_PASS);
				free.add(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		if (free.isEmpty()) {
			throw new Exception("사용할 수 있는 커넥션이 없습니다.");
		}

		Connection con = free.remove(0);
		used.add(con);
		return con;
	}

	public static void releaseConnection(Connection con) {
		used.remove(con);
		free.add(con);
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 10; i++) {
				Connection con = ConnectionPool.getConnection();
				System.out.println(con);

				ConnectionPool.releaseConnection(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}