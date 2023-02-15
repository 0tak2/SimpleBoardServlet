package board.vo;

public class Article {
	private int articleNum;
	private String articleTitle;
	private String articleContent;
	private String articleAuthor;
	private String articleDate;
	
	public Article() {
	}

	public Article(int articleNum, String articleTitle, String articleContent,
			String articleAuthor, String articleDate) {
		super();
		this.articleNum = articleNum;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.articleAuthor = articleAuthor;
		this.articleDate = articleDate;
	}

	public Article(String articleTitle, String articleContent, String articleAuthor) {
		super();
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.articleAuthor = articleAuthor;
	}

	public int getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleAuthor() {
		return articleAuthor;
	}

	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public String getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}
}
