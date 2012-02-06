package org.aesthete.swingobjects.util;

public class HTMLUtils {

	public static String convertAllLineBreaksToHtml(String s) {
		return s.replaceAll("\n", "<br/>");
	}
	
}
