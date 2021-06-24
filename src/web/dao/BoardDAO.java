package web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import web.util.ShopException;
import web.vo.ArticleVO;

public class BoardDAO {
	DataSource ds;
	
	public BoardDAO() {
		// 1.ConnectionPool 찾기
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/MyDB");	//tomcat_ConnectionPool
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArticleVO> selectAllArticles() throws ShopException {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		
		try {
			// 2.연결
			con = ds.getConnection();	//대여
			
			// 3.Statement 생성
			String query="select level, articleNO, parentNO, title, content, id, writeDate"
					+" from t_board"
					+" start with parentNO=0"
					+" connect by prior articleNO=parentNO"
					+" order siblings by articleNO desc";
			st = con.prepareStatement(query);
			
			// 4.SQL 전송
			rs= st.executeQuery();// select만 query
			
			// 5.결과 얻기
			ArrayList<ArticleVO> list=new ArrayList();
			while(rs.next()) {
				int level=rs.getInt("level");
				int articleNO=rs.getInt("articleNO");
				int parentNO=rs.getInt("parentNO");
				String title=rs.getString("title");
				String content=rs.getString("content");
				String id=rs.getString("id");
				Date writeDate=rs.getDate("writeDate");
				
				ArticleVO vo=new ArticleVO(level, articleNO, parentNO, title, content, null, id, writeDate);
				list.add(vo);				
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShopException("게시판 리스트 불러오기 실패");
		} finally {
			// 6.종료
			try {
				rs.close();
				st.close();
				con.close();	//반납
			} catch (Exception e) {
				
			}
		}
	}

}
