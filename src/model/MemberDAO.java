package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.ConnectionPool;

public class MemberDAO {
	Connection con 			= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	
	public int MemberInsert(MemberVO vo) throws Exception {
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO member ");
			sql.append("(id, pwd, nickname, email, reg_date, level_no, ip, score)");
			sql.append(" VALUES ");
			sql.append("(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			int index = 1;
			pstmt.setString(index++, vo.getId());
			pstmt.setString(index++, vo.getPwd());
			pstmt.setString(index++, vo.getNickname());
			pstmt.setString(index++, vo.getEmail());
			pstmt.setDate(index++, (Date) vo.getRegDate());
			pstmt.setInt(index++, vo.getLevelNo());
			pstmt.setString(index++, vo.getId());
			pstmt.setInt(index++, vo.getScore());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return result;
	}
	
	public boolean getOverlapId(String id) throws Exception {
		boolean chk = false;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM member ");
			sql.append("WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chk = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return chk;
	}
	
	public MemberVO getMemberDetail(String id) throws Exception {
		MemberVO vo = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM member ");
			sql.append("WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPwd(rs.getString("pwd"));
				vo.setNickname(rs.getString("nickname"));
				vo.setEmail(rs.getString("email"));
				vo.setRegDate(rs.getTimestamp("reg_date"));
				vo.setLevelNo(rs.getInt("level_no"));
				vo.setScore(rs.getInt("score"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return vo;
	}
	
	public MemberVO getLoginChk(MemberVO vo) throws Exception {
		MemberVO user = null;
		try {
			con = ConnectionPool.getConnection();
			String sql = "select * from member where id = ? and pwd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			rs = pstmt.executeQuery();
	
			if (rs.next()) {
				user = new MemberVO(); 
				user.setId(rs.getString("id"));
				user.setPwd(rs.getString("pwd"));
				user.setNickname(rs.getString("nickname"));
				user.setEmail(rs.getString("email"));
				user.setRegDate(rs.getDate("reg_date"));
				user.setLevelNo(rs.getInt("level_no"));
				user.setIp(rs.getString("ip"));
				user.setScore(rs.getInt("score"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return user;
	}
}