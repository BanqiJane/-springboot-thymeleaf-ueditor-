package xyz.acproject.blogs.tools;

import java.util.HashSet;
import java.util.List;

public class ListTools {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List removeDuplicate(List list) {   
		HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll( h);   
	    return list;   
	}   
}
