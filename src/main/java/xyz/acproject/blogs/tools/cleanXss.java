package xyz.acproject.blogs.tools;

public class cleanXss {
	 public static String cleanXSS(String value) {
	        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	        value = value.replaceAll("'", "&#39;");
	        value = value.replaceAll("eval\\((.*)\\)", "");
	        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
	        value = value.replaceAll("script", "");
	        return value;
	    }
}
