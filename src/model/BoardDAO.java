package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionPool;

public class BoardDAO {
	Connection con 			= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	
	public List<BoardVO> getBoardList(int cateNo) throws Exception {
		List<BoardVO> list = null;
		try {
			con = ConnectionPool.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM board ");
			sql.append("WHERE cate_no = ? AND used = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cateNo);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<>();
			
			while(rs.next()) {
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
}