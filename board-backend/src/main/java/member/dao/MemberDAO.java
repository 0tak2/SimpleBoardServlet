package member.dao;

import org.apache.ibatis.session.SqlSession;

import member.vo.Member;
public class MemberDAO {
	private SqlSession session;

	public MemberDAO() {
	}

	public MemberDAO(SqlSession session) {
		this.session = session;
	}
	
	public Member select(Member member) {
		Member result = null;
		try {
			result = session.selectOne("myMember.login", member);		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
