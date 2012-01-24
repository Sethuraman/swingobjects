package org.aesthete.swingobjects;

import java.util.Properties;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;

/**
 * A wrapper to access the swingobjects.properties file. This file is required for a 
 * few default configurations to use the Swing Objects Framework. 
 * To know more about what this file is used for see the swingobjects.properties file
 * @author sethu
 */
public class SwingObjProps {
	
	private static Properties swingObjProps;
	
	public static void init(String fileLocInClasspath) throws SwingObjectException{
		try {
			swingObjProps = new Properties();
			swingObjProps.load(SwingObjProps.class
					.getResourceAsStream(fileLocInClasspath));
		} catch (Exception e) {
			throw new SwingObjectException("Error loading swingobjects properties", e, ErrorSeverity.SEVERE, SwingObjProps.class);
		}
	}
	
	public static String getProperty(String key){
		return swingObjProps.getProperty(key,"");
	}
}
