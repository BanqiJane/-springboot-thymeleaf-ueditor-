package xyz.acproject.blogs.tools.returnJson.JackjsonConfig;


public class Data<T> {
	private Integer total;
	private Integer nowTotal;
	private Integer startPage;
	private Integer pageSize;
	private T list1;
	private T list2;
	private String time;
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Data(Integer total, Integer nowTotal, T list1, T list2,String time) {
		super();
		this.total = total;
		this.nowTotal = nowTotal;
		this.list1 = list1;
		this.list2 = list2;
		this.time=time;
	}
	public Data(Integer total, Integer nowTotal, T list1, T list2) {
		super();
		this.total = total;
		this.nowTotal = nowTotal;
		this.list1 = list1;
		this.list2 = list2;
	}
	public Data(T list1) {
		super();
		this.list1=list1;
	}
	public Data( T list1, Integer startPage, Integer pageSize,Integer total) {
		super();
		this.list1 = list1;
		this.startPage = startPage;
		this.pageSize = pageSize;
		this.total = total;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getNowTotal() {
		return nowTotal;
	}
	public void setNowTotal(Integer nowTotal) {
		this.nowTotal = nowTotal;
	}
	public T getList1() {
		return list1;
	}
	public void setList1(T list1) {
		this.list1 = list1;
	}
	public T getList2() {
		return list2;
	}
	public void setList2(T list2) {
		this.list2 = list2;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
