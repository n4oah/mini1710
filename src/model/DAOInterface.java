package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionPool;

public class DAOInterface {

	private static <T> String mattch(Class<T> c, String name) {

		try {
			for (Field f : c.getFields()) {
				if (f.getName().toUpperCase().equals(name.toUpperCase())) {
					return f.getName();
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	private static void SetParameter(PreparedStatement pre, Object[] parameter) throws SQLException {
		for (int i = 1; i <= parameter.length; i++) {
			if (parameter[i - 1] == null) {
				pre.setNull(i, java.sql.Types.NULL);
			} else {
				pre.setObject(i, parameter[i - 1]);
			}
		}
	}

	public static int Delete(String query, Object[] parameter) throws Exception {
		Connection Conn = ConnectionPool.getConnection();

		try {
			PreparedStatement pre = Conn.prepareStatement(query);
			if (parameter != null) {
				SetParameter(pre, parameter);
			}
			return pre.executeUpdate();
		} catch (Exception e) {
		} finally {
			ConnectionPool.releaseConnection(Conn);
		}
		return -1;
	}

	public static int Insert(String query, Object[] parameter) throws Exception {
		Connection Conn = ConnectionPool.getConnection();
		try {
			PreparedStatement pre = Conn.prepareStatement(query);
			if (parameter != null) {
				SetParameter(pre, parameter);
			}
			return pre.executeUpdate();
		} catch (Exception e) {
		} finally {
			ConnectionPool.releaseConnection(Conn);
		}
		return -1;
	}

	public static <T> List<T> Select(String query, Object[] parameter, Class<T> classinfo) throws Exception {
		Connection Conn = ConnectionPool.getConnection();
		try {
			List<T> list = new ArrayList<T>();

			PreparedStatement pre = Conn.prepareStatement(query);
			if (parameter != null) {
				SetParameter(pre, parameter);
			}
			ResultSet set = pre.executeQuery();
			List<String> array = new ArrayList<>();
			for (int i = 0 + 1; i < set.getMetaData().getColumnCount(); i++) {

				String tmp = mattch(classinfo, set.getMetaData().getColumnName(i));
				if (!tmp.equals("")) {
					array.add(tmp);
				}
			}

			while (set.next()) {
				Constructor<?> co = classinfo.getConstructors()[0];
				T r = (T) co.newInstance();

				for (String s : array) {
					Field fi = classinfo.getField(s);
					fi.set(r, set.getObject(s));
				}
				list.add(r);
			}

			set.close();
			pre.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.releaseConnection(Conn);
		}

		return null;
	}
}
