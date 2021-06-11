package xyz.acproject.blogs.tools.returnJson.PubilcClass;

public class  ArticlePraiseJson<T> {
	private T flag;
	private T count;
	
	public ArticlePraiseJson() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public ArticlePraiseJson(T flag, T count) {
		super();
		this.flag = flag;
		this.count = count;
	}

	public T getFlag() {
		return flag;
	}

	public void setFlag(T flag) {
		this.flag = flag;
	}

	public T getCount() {
		return count;
	}

	public void setCount(T count) {
		this.count = count;
	}
	
}
