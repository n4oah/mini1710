package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.ConnectionPool;

public class CateDAO {
	Connection con 			= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	
	public int addCate(String name, int groupNo) throws Exception {
		return addCate(name, groupNo, getMaxOrder(groupNo));
	}
	
	public int addCate(String name, int groupNo, int order) throws Exception {
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO category ");
			sql.append("(cate_no, used, name, group_num, order_num)");
			sql.append(" VALUES ");
			sql.append("(cate_seq.nextval, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setInt(index++, 1);
			pstmt.setString(index++, name);
			pstmt.setInt(index++, groupNo);
			pstmt.setInt(index++, order);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return result;
	}
	
	public int getMaxOrder(int groupNo) throws Exception {
		int no = -1;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MAX(order_num) as num_ FROM category WHERE group_num = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, groupNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				no = rs.getInt("num_");
				no = no <= 0 ? 1 : no + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return no;
	}
	
	/*public int getSequence() {
		int no = -1;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT cate_seq.nextval FROM DUAL");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.executeQuery();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.releaseConnection(con);
		}
		return no;
	}*/
}