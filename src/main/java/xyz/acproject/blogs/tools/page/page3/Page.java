package xyz.acproject.blogs.tools.page.page3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjian
 * @version page3.0
 * @effect {前后含有..的省略页码 显示第一页和最后一页 类似与破站的分页.}
 */
public class Page implements Serializable{
	private static final long serialVersionUID = -3198048449643774660L;
	private int pageNow=1;
	private int pageSize=5;
	private int startPos;//从第几个开始拿几（pageSize）个
	private int totalCount;
	private int totalPageCount;
	private int begin=1;
	private int end = totalPageCount;
	private List<Integer> betweenPageList = new ArrayList<Integer>();
	private boolean beginDot;
	private boolean endDot;
	public Page() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Page(int totalCount,int pageNow) {
		this.totalCount=totalCount;
		this.pageNow=pageNow;
	}
	public Page(int pageNow, int pageSize, int startPos, int totalCount, int totalPageCount, int begin, int end,
			List<Integer> betweenPageList, boolean beginDot, boolean endDot) {
		super();
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.startPos = startPos;
		this.totalCount = totalCount;
		this.totalPageCount = totalPageCount;
		this.begin = begin;
		this.end = end;
		this.betweenPageList = betweenPageList;
		this.beginDot = beginDot;
		this.endDot = endDot;
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartPos() {
		return (getPageNow()-1)*getPageSize();
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPageCount() {
		return (int)Math.ceil((double)getTotalCount()/(double)getPageSize());
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getBegin() {
		if(getTotalPageCount()<=5) {
			this.begin=1;
			this.end=getTotalPageCount();
		}else {
			if(getPageNow()<=3) {
				this.begin=1;
				this.end=5;
			}else {
				this.begin=getPageNow()-2;
				this.end=getPageNow()+2;
				if(getEnd()>getTotalPageCount()) {
					this.end=getTotalPageCount();
					this.begin=getEnd()-3;
				}
			}
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
	public List<Integer> getBetweenPageList() {
		for(int i=getBegin();i<=getEnd();i++) {
			betweenPageList.add(i);
		}
		return betweenPageList;
	}
	public void setBetweenPageList(List<Integer> betweenPageList) {
		this.betweenPageList = betweenPageList;
	}
	public boolean isBeginDot() {
		return (getBegin()>2)?true:false;
	}
	public void setBeginDot(boolean beginDot) {
		this.beginDot = beginDot;
	}
	public boolean isEndDot() {
		return (getEnd()<getTotalPageCount()-1)?true:false;
	}
	public void setEndDot(boolean endDot) {
		this.endDot = endDot;
	}
	@Override
	public String toString() {
		return "Page [pageNow=" + pageNow + ", pageSize=" + pageSize + ", startPos=" + startPos + ", totalCount="
				+ totalCount + ", totalPageCount=" + totalPageCount + ", begin=" + begin + ", end=" + end
				+ ", betweenPageList=" + betweenPageList + ", beginDot=" + beginDot + ", endDot=" + endDot + "]";
	}
	
}
