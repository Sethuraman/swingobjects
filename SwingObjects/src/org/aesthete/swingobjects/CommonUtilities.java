package org.aesthete.swingobjects;

import java.text.DateFormat;
import java.util.Date;


public class CommonUtilities {

	public static String getDateInddMMYYYYFormat(Date date){
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
	}





}
