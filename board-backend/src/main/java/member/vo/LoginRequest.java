package member.vo;

public class LoginRequest {
	private String userID;
	private String userPW;
	
	public LoginRequest() {
	}

	public LoginRequest(String userID, String userPW) {
		super();
		this.userID = userID;
		this.userPW = userPW;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
}
