package board.vo;

public class ArticleExtended extends Article {
	private String memberName;
	private int articleLike;
	private int articleComments;
	
	public ArticleExtended() {
	}
	

	public ArticleExtended(int articleNum, String articleTitle, String articleContent, String articleAuthor,
			String memberName, String articleDate, int articleLike, int articleComments) {
		super(articleNum, articleTitle, articleContent, articleAuthor, articleDate);
		this.memberName = memberName;
		this.articleLike = articleLike;
		this.articleComments = articleComments;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public int getArticleLike() {
		return articleLike;
	}


	public void setArticleLike(int articleLike) {
		this.articleLike = articleLike;
	}


	public int getArticleComments() {
		return articleComments;
	}


	public void setArticleComments(int articleComments) {
		this.articleComments = articleComments;
	}
}
