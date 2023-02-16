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
			result = session.selectOne("myMember.select", member);		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public Member selectByIdAndPw(Member member) {
		Member result = null;
		try {
			result = session.selectOne("myMember.login", member);		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public int insert(Member member) {
		int affectedRows = 0;
		try {
			affectedRows = session.insert("myMember.insert", member);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int update(Member member) {
		int affectedRows = 0;
		try {
			affectedRows = session.update("myMember.update", member);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

	public int updateToEmpty(Member param) {
		int affectedRows = 0;
		try {
			affectedRows = session.update("myMember.updateToEmpty", param);
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

}
