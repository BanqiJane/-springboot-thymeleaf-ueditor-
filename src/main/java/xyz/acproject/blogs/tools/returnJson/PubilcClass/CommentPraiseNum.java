package xyz.acproject.blogs.tools.returnJson.PubilcClass;

public class CommentPraiseNum<T> {
	private T praisenum;
	private T flag;
	public CommentPraiseNum() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public CommentPraiseNum(T praisenum, T flag) {
		super();
		this.praisenum =praisenum;
		this.flag = flag;
	}
	public T getPraisenum() {
		return praisenum;
	}
	public void setPraisenum(T praisenum) {
		this.praisenum = praisenum;
	}
	public T getFlag() {
		return flag;
	}
	public void setFlag(T flag) {
		this.flag = flag;
	}
	
}
