package xyz.acproject.blogs.tools.page.page2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjian
 * @version page2.0
 * @effect {省略了前后页码 只留中间5个页码}
 */
public class Page implements Serializable {
	private static final long serialVersionUID = -3198048449643774660L;
	private int pageNow = 1;
	private int startPos;
	private int pageSize = 5;
	private int totalCount;
	private int totalPageCount;
	private int begin = 1;
	private int end = totalPageCount;
	private List<Integer> pageList = new ArrayList<Integer>();

	public Page() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public Page(int pageNow, int totalCount) {
		super();
		this.pageNow = pageNow;
		this.totalCount = totalCount;
	}

	public Page(int pageNow, int startPos, int pageSize, int totalCount, int totalPageCount, int begin, int end,
			List<Integer> pageList) {
		super();
		this.pageNow = pageNow;
		this.startPos = startPos;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPageCount = totalPageCount;
		this.begin = begin;
		this.end = end;
		this.pageList = pageList;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getStartPos() {
		return (getPageNow() - 1) * getPageSize();
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		return (int) Math.ceil((double) getTotalCount() / (double) getPageSize());
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getBegin() {
		if (getPageNow() < getTotalPageCount() - 2) {
			this.end = getPageNow() + 2;
			if (getPageNow() > 3) {
				this.begin = getPageNow() - 2;
			} else {
				this.begin = 1;
				this.end = 5;
			}
		} else {
			if (getPageNow() > 3) {
			this.begin = getPageNow() - 2;
			
			if (getPageNow() == getTotalPageCount() - 1) {
				this.begin = getPageNow() - 3;
			}
			if (getPageNow() == getTotalPageCount()) {
				this.begin = getPageNow() - 4;
			}
			}
			this.end = getTotalPageCount();
		}
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public List<Integer> getPageList() {
		for (int i = getBegin(); i <= getEnd(); i++) {
			pageList.add(i);
		}
		return pageList;
	}

	public void setPageList(List<Integer> pageList) {
		this.pageList = pageList;
	}

	@Override
	public String toString() {
		return "Page [pageNow=" + pageNow + ", startPos=" + startPos + ", pageSize=" + pageSize + ", totalCount="
				+ totalCount + ", totalPageCount=" + totalPageCount + ", begin=" + begin + ", end=" + end
				+ ", pageList=" + pageList + "]";
	}

}
