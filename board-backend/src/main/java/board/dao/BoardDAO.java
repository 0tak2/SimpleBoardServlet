package board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import board.vo.Article;
import board.vo.ArticleExtended;
import board.vo.Comment;
import board.vo.Like;
import common.vo.BasicSelectVO;

public class BoardDAO {
	private SqlSession session;
	
	public BoardDAO() {
	}
	
	public BoardDAO(SqlSession session) {
		this.session = session;
	}

	public BasicSelectVO selectArticleCounts() {
		BasicSelectVO result = null;
		try {
			result = session.selectOne("myBoard.selectArticleCounts");			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public List<ArticleExtended> selectAllArticles() {
		// 데이터베이스 처리 - MyBatis 이용
		List<ArticleExtended> result = null;
		try {
			result = session.selectList("myBoard.selectAllArticles");			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public List<ArticleExtended> selectArticles(BasicSelectVO param) {
		// 데이터베이스 처리 - MyBatis 이용
		List<ArticleExtended> result = null;
		try {
			result = session.selectList("myBoard.selectArticles", param);			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public ArticleExtended selectOneArticle(Article articleParam) {
		ArticleExtended result = null;
		try {
			result = session.selectOne("myBoard.selectOneArticle", articleParam);			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public int insertArticle(Article newArticle) {
		int affectedRows = 0;
		try {
			affectedRows = session.insert("myBoard.insertArticle", newArticle);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int editArticle(Article param) {
		int affectedRows = 0;
		try {
			affectedRows = session.update("myBoard.updateArticle", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int deleteArticle(Article param) {
		int affectedRows = 0;
		try {
			affectedRows = session.delete("myBoard.deleteArticle", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public List<Comment> selectAllComments(Article param) {
		List<Comment> result = null;
		try {
			result = session.selectList("myBoard.selectAllComments", param);			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public Comment selectOneComment(Comment param) {
		Comment result = null;
		try {
			result = session.selectOne("myBoard.selectComment", param);			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public int insertComment(Comment param) {
		int affectedRows = 0;
		try {
			affectedRows = session.insert("myBoard.insertComment", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int editComment(Comment param) {
		int affectedRows = 0;
		try {
			affectedRows = session.update("myBoard.updateComment", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int deleteComment(Comment param) {
		int affectedRows = 0;
		try {
			affectedRows = session.delete("myBoard.deleteComment", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public Like selectLike(Like param) {
		Like result = null;
		try {
			result = session.selectOne("myBoard.selectLike", param);		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public int insertLike(Like param) {
		int affectedRows = 0;
		try {
			affectedRows = session.insert("myBoard.insertLike", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int deleteLike(Like param) {
		int affectedRows = 0;
		try {
			affectedRows = session.delete("myBoard.deleteLike", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

}
