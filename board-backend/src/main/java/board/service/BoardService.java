package board.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import board.dao.BoardDAO;
import board.vo.Article;
import board.vo.ArticleExtended;
import board.vo.Comment;
import board.vo.Like;
import common.mybatis.MyBatisConnectionFactory;
import common.service.ServiceResult;

public class BoardService {

	public List<ArticleExtended> getAllArticles() {
		List<ArticleExtended> list = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			list = dao.selectAllArticles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArticleExtended getArticle(Article articleParam) {	
		ArticleExtended result = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();) {
			BoardDAO dao = new BoardDAO(sqlSession);
			result = dao.selectOneArticle(articleParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean writeArticle(Article newArticle) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			int affectedRows = dao.insertArticle(newArticle);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean editArticle(Article param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			int affectedRows = dao.editArticle(param);
			
			if (affectedRows  == 1) {
				System.out.println(param.getArticleContent());
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteArticle(Article param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			int affectedRows = dao.deleteArticle(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Comment> getAllComments(ArticleExtended param) {
		List<Comment> list = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			list = dao.selectAllComments(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Comment getOneComment(Comment param) {
		Comment result = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			result = dao.selectOneComment(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean writeComment(Comment param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			int affectedRows = dao.insertComment(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean editComment(Comment param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			int affectedRows = dao.editComment(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteComment(Comment param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			int affectedRows = dao.deleteComment(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Like getLike(Like param) {
		Like result = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			result = dao.selectLike(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult setLike(Like param) {
		ServiceResult result = ServiceResult.FAILED;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			// 중복 좋아요 방지
			Like likeExpectedNull = dao.selectLike(param);
			if (likeExpectedNull != null) {
				// 이미 좋아요 기록이 있으면 처리 거부
				return ServiceResult.REJECTED;
			}
			
			// 좋아요 처리
			int affectedRows = dao.insertLike(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = ServiceResult.SUCCESS;
			} else {
				sqlSession.rollback();
				result = ServiceResult.FAILED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean unSetLike(Like param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			BoardDAO dao = new BoardDAO(sqlSession);
			
			int affectedRows = dao.deleteLike(param);
			
			if (affectedRows  == 1) {
				sqlSession.commit();
				result = true;
			} else {
				sqlSession.rollback();
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
