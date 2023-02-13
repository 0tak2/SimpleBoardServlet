package member.vo;

import common.vo.BasicResponse;

public class LoginResponse extends BasicResponse {
	private Member userInfo;
	
	public LoginResponse() {
	}

	public LoginResponse(boolean success, String msg, Member userInfo) {
		super(success, msg);
		this.userInfo = userInfo;
	}

	public Member getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Member userInfo) {
		this.userInfo = userInfo;
	}
	
}
