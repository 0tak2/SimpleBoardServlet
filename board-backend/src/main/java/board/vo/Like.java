package board.vo;

public class Like {
	private String likeMemberId;
	private int likeArticle;
	
	public Like() {
	}

	public Like(String likeMemberId, int likeArticle) {
		super();
		this.likeMemberId = likeMemberId;
		this.likeArticle = likeArticle;
	}

	public String getLikeMemberId() {
		return likeMemberId;
	}

	public void setLikeMemberId(String likeMemberId) {
		this.likeMemberId = likeMemberId;
	}

	public int getLikeArticle() {
		return likeArticle;
	}

	public void setLikeArticle(int likeArticle) {
		this.likeArticle = likeArticle;
	}
}
