package xyz.acproject.blogs.tools.returnJson.PubilcClass;

public class CommentsFlag<T> {
	private T comment;
	private T flag;
	public CommentsFlag() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public CommentsFlag(T comment, T flag) {
		super();
		this.comment = comment;
		this.flag = flag;
	}
	public T getComment() {
		return comment;
	}
	public void setComment(T comment) {
		this.comment = comment;
	}
	public T getFlag() {
		return flag;
	}
	public void setFlag(T flag) {
		this.flag = flag;
	}
	
}
