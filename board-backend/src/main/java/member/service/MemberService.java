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
			result = dao.select(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
