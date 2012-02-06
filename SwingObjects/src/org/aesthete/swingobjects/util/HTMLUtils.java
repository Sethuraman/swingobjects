package org.aesthete.swingobjects.util;

public class HTMLUtils {

	public static String convertAllLineBreaksToHtml(String s) {
		return s.replaceAll("\n", "<br/>");
	}

	public static String getHtmlString(int heading,String value,String colour,boolean isBold) {
		String bold="";
		if(isBold){
			bold="<b>"+value+"</b>";
		}else{
			bold=value;
		}
		String font="";
		if(colour!=null){
			font="<font color=\""+colour+"\">"+bold+"</font>";
		}else{
			font="<font color=\"black\">"+bold+"</font>";
		}
		String headingString="";
		switch(heading){
			case 1:
				headingString="<h1>"+font+"</h1>";
				break;
			case 2:
				headingString="<h2>"+font+"</h2>";
				break;
			case 3:
				headingString="<h3>"+font+"</h3>";
				break;
			case 4:
				headingString="<h4>"+font+"</h4>";
				break;
			default:
				headingString=font;
				break;
		}
		return "<html>"+headingString+"</html>";
	}

}
