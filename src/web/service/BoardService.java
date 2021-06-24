package web.service;

import java.util.ArrayList;

import web.dao.BoardDAO;
import web.util.ShopException;
import web.vo.ArticleVO;

public class BoardService {
	
	BoardDAO dao;
	
	public BoardService() {
		dao=new BoardDAO();
	}

	public ArrayList<ArticleVO> listArticles() throws ShopException {
		return dao.selectAllArticles();
	}
}
