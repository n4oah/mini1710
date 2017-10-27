package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionPool;
import controller.board.Page;

public class BoardDAO {
	Connection con 			= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	
	public int BoardInsert(BoardVO vo) throws Exception {
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO board ");
			sql.append("(board_no, cate_no, used, writer, writer_id, title, content)");
			sql.append(" VALUES ");
			sql.append("(?, ?, ?, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			int index = 1;
			pstmt.setInt(index++, vo.getBoardNo());
			pstmt.setInt(index++, vo.getCateNo());
			pstmt.setInt(index++, vo.isUsed() ? 1: 0);
			pstmt.setString(index++, vo.getWriter());
			pstmt.setString(index++, vo.getWriterId());
			pstmt.setString(index++, vo.getTitle());
			pstmt.setString(index++, vo.getContent());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return result;
	}
	
	public BoardVO getBoardDetail(int boardNo) throws Exception {
		BoardVO vo = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM board ");
			sql.append("WHERE board_no = ? AND used = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO();
				vo.setBoardNo(rs.getInt("board_no"));
				vo.setCateNo(rs.getInt("cate_no"));
				vo.setContent(rs.getString("content"));
				vo.setHitCnt(rs.getInt("hit_cnt"));
				vo.setLikeCnt(rs.getInt("like_cnt"));
				vo.setTitle(rs.getString("title"));
				vo.setUsed(rs.getInt("used") == 1 ? true : false);
				vo.setWriter(rs.getString("writer"));
				vo.setWriter_date(rs.getTimestamp("writer_date"));
				vo.setWriterId(rs.getString("writer_id"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			pstmt.close();
			ConnectionPool.releaseConnection(con);
		}
		return vo;
	}
	
	public List<BoardVO> getBoardList(Page page) throws Exception {
		List<BoardVO> list = new ArrayList<>();
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append("(SELECT rownum rnum, a.* FROM (SELECT * FROM board ");
			sql.append("WHERE cate_no = ? AND used = 1 ");
			sql.append("ORDER BY writer_date DESC) a) ");
			sql.append("WHERE rnum BETWEEN ? AND ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, page.getCateNo());
			pstmt.setInt(2, page.getBegin());
			pstmt.setInt(3, page.getEnd());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setBoardNo(rs.getInt("board_no"));
				vo.setCateNo(rs.getInt("cate_no"));
				vo.setContent(rs.getString("content"));
				vo.setHitCnt(rs.getInt("hit_cnt"));
				vo.setLikeCnt(rs.getInt("like_cnt"));
				vo.setTitle(rs.getString("title"));
				vo.setUsed(rs.getInt("used") == 1 ? true : false);
				vo.setWriter(rs.getString("writer"));
				vo.setWriter_date(rs.getTimestamp("writer_date"));
				vo.setWriterId(rs.getString("writer_id"));
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
	
	public int getBoardListCount(int cateNo) throws Exception {
		int cnt = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS count FROM board WHERE cate_no = ? AND used = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cateNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("count");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
			ConnectionPool.releaseConnection(con);
		}
		return cnt;
	}
	
	public int BoardDelete(BoardVO vo) throws Exception {
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM board ");
			sql.append("WHERE board_no = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, vo.getBoardNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
			ConnectionPool.releaseConnection(con);
		}
		return result;
	}
	
	public int BoardModify(BoardVO vo) throws Exception {
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE board SET title = ?, content = ? WHERE board_no = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getBoardNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
			ConnectionPool.releaseConnection(con);
		}
		return result;
	}
	
	public int getBoardSeq() throws Exception {
		int seq = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT board_seq.nextval as num FROM DUAL");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				seq = rs.getInt("num");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
			ConnectionPool.releaseConnection(con);
		}
		return seq;
	}
}