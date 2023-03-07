package org.youngtak2.springboard.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.youngtak2.springboard.member.dao.MemberDao;
import org.youngtak2.springboard.member.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	private final PlatformTransactionManager transactionManager;
	public MemberServiceImpl(PlatformTransactionManager transactionManager) {
	    this.transactionManager = transactionManager;
	  }
	
	@Autowired
	private MemberDao dao;
	
	@Override
	public boolean registerNewAccount(Member newMember) {
		int affectedRows = 0;
		TransactionStatus txStatus =
		        transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			affectedRows = dao.insert(newMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (affectedRows == 1) {
			transactionManager.commit(txStatus);
			return true;
		} else {
			transactionManager.rollback(txStatus);
			return false;
		}
	}

	@Override
	public Member login(Member member) {
		return dao.login(member);
	}

}
