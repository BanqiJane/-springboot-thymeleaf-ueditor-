package xyz.acproject.blogs.tools.returnJson.PubilcClass;

public class SearchJson<T> {
	private T value;
	private T revalue;
	private T id;
	
	public SearchJson() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public SearchJson(T value, T revalue, T id) {
		super();
		this.value = value;
		this.revalue = revalue;
		this.id = id;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getRevalue() {
		return revalue;
	}

	public void setRevalue(T revalue) {
		this.revalue = revalue;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
}
