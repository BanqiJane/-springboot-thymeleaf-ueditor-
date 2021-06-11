package xyz.acproject.blogs.tools.returnJson.PubilcClass;

public class CommentsJson<T> {
	private T comments;
	private T recomments;
	private T page;
	
	public CommentsJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsJson(T comments, T recomments, T page) {
		super();
		this.comments = comments;
		this.recomments = recomments;
		this.page = page;
	}

	public T getComments() {
		return comments;
	}

	public void setComments(T comments) {
		this.comments = comments;
	}

	public T getRecomments() {
		return recomments;
	}

	public void setRecomments(T recomments) {
		this.recomments = recomments;
	}

	public T getPage() {
		return page;
	}

	public void setPage(T page) {
		this.page = page;
	}
	
	
	
}
