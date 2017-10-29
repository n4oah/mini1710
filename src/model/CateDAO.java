package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import common.ConnectionPool;

public class CateDAO {
	Connection con 			= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	
	public int addCate(String name, String uriName, int groupNo, int order) throws Exception {
		int result = 0;
		try {
			if(!Pattern.matches("^[a-zA-Z/_]{1,}$", uriName)) {
				throw new Exception("uriName string is matches error : " + uriName);
			}
			
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO category ");
			sql.append("(cate_no, used, name, uri_name, group_no, order_num)");
			sql.append(" VALUES ");
			sql.append("(cate_seq.nextval, ?, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setInt(index++, 1);
			pstmt.setString(index++, name);
			pstmt.setString(index++, uriName);
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
	
	public CateVO getCategoryDetail(int cateNo) throws Exception {
		CateVO vo = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM category ");
			sql.append("WHERE cate_no = ? AND used = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cateNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new CateVO();
				vo.setCateNo(rs.getInt("cate_no"));
				vo.setGroupNo(rs.getInt("group_no"));
				vo.setName(rs.getString("name"));
				vo.setUriName(rs.getString("uri_name"));
				vo.setUsed(rs.getInt("used") == 1 ? true : false);
				vo.setOrderNum(rs.getInt("order_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return vo;
	}
	
	public List<CateVO> getCategorys() throws Exception {
		List<CateVO> list = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT Tabl.* FROM ");
			sql.append("(SELECT DISTINCT group_no FROM category ORDER BY group_no ASC) Tabl");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int groupNum = rs.getInt("group_no");
				
				sql.setLength(0);
				sql.append("SELECT * FROM category ");
				sql.append("WHERE group_no = ? AND order_num = ? AND used = 1");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setInt(2, 1);
				ResultSet _rs = pstmt.executeQuery();
				
				if(_rs.next()) {
					if(list == null)
						list = new ArrayList<>();
					
					CateVO vo = new CateVO();
					vo.setCateNo(_rs.getInt("cate_no"));
					vo.setGroupNo(_rs.getInt("group_no"));
					vo.setName(_rs.getString("name"));
					vo.setUriName(_rs.getString("uri_name"));
					vo.setUsed(_rs.getInt("used") == 1 ? true : false);
					vo.setOrderNum(_rs.getInt("order_num"));
					list.add(vo);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return list;
	}
	
	public List<CateVO> getBoards(int groupNo) throws Exception {
		List<CateVO> list = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM category ");
			sql.append("WHERE group_no = ? AND used = 1 AND order_num > 1 ORDER BY order_num ASC");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, groupNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(list == null)
					list = new ArrayList<>();
				
				CateVO vo = new CateVO();
				vo.setCateNo(rs.getInt("cate_no"));
				vo.setGroupNo(rs.getInt("group_no"));
				vo.setName(rs.getString("name"));
				vo.setUriName(rs.getString("uri_name"));
				vo.setUsed(rs.getInt("used") == 1 ? true : false);
				vo.setOrderNum(rs.getInt("order_num"));
				list.add(vo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return list;
	}
	
	public List<CateVO> getCategoryBoard() throws Exception {
		List<CateVO> list = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT Tabl.* FROM ");
			sql.append("(SELECT DISTINCT group_no FROM category ORDER BY group_no ASC) Tabl");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int groupNum = rs.getInt("group_no");
				
				sql.setLength(0);
				sql.append("SELECT * FROM category ");
				sql.append("WHERE group_no = ? AND used = 1");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, groupNum);
				ResultSet _rs = pstmt.executeQuery();
				
				while(_rs.next()) {
					if(list == null)
						list = new ArrayList<>();
					
					CateVO vo = new CateVO();
					vo.setCateNo(_rs.getInt("cate_no"));
					vo.setGroupNo(_rs.getInt("group_no"));
					vo.setName(_rs.getString("name"));
					vo.setUriName(_rs.getString("uri_name"));
					vo.setUsed(_rs.getInt("used") == 1 ? true : false);
					vo.setOrderNum(_rs.getInt("order_num"));
					list.add(vo);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return list;
	}
	
	public int getGroupNo(int cateNo) throws Exception {
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT group_no FROM category ");
			sql.append("WHERE cate_no = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cateNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("group_no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return 0;
	}
	
	public int getSequence() {
		int no = -1;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT cate_seq.nextval AS num FROM DUAL");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				no = rs.getInt("num");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.releaseConnection(con);
		}
		return no;
	}
}