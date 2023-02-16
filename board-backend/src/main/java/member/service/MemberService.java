package member.service;

import org.apache.ibatis.session.SqlSession;

import common.mybatis.MyBatisConnectionFactory;
import member.dao.MemberDAO;
import member.vo.Member;

public class MemberService {

	public Member login(Member member) {
		// 로그인 처리
		// 데이터베이스 처리는 DAO에서

		Member result = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			MemberDAO dao = new MemberDAO(sqlSession);
			result = dao.selectByIdAndPw(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean registerMember(Member member) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			MemberDAO dao = new MemberDAO(sqlSession);
			int affectedRows = dao.insert(member);
			
			if (affectedRows == 1) {
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

	public Member getMember(Member newMember) {
		Member result = null;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			MemberDAO dao = new MemberDAO(sqlSession);
			result = dao.select(newMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean editMemberInfo(Member member) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			MemberDAO dao = new MemberDAO(sqlSession);
			int affectedRows = dao.update(member);
			
			if (affectedRows == 1) {
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

	public boolean getOut(Member param) {
		boolean result = false;
		try (SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
			MemberDAO dao = new MemberDAO(sqlSession);
			int affectedRows = dao.updateToEmpty(param);
			
			if (affectedRows == 1) {
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
