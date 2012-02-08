package org.aesthete.swingobjects;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;

/**
 * A wrapper to access the swingobjects.properties file. This file is required
 * for a few default configurations to use the Swing Objects Framework. To know
 * more about what this file is used for see the swingobjects.properties file
 *
 * @author sethu
 */
public class SwingObjProps {

	private static Properties swingObjProps;
	private static Properties errorProps;

	public static void init(String swingObjPropsFile, String errorPropsFile) throws SwingObjectException {
		try {
			swingObjProps = new Properties();
			swingObjProps.load(SwingObjProps.class.getResourceAsStream(swingObjPropsFile));
			errorProps = new Properties();
			errorProps.load(SwingObjProps.class.getResourceAsStream(errorPropsFile));
		} catch (Exception e) {
			throw new SwingObjectException("Error loading swingobjects properties", e, ErrorSeverity.SEVERE, SwingObjProps.class);
		}
	}

	public static String getProperty(String key,String... placeHolderValues) {
		return replacePlaceHolders(swingObjProps.getProperty(key),placeHolderValues);
	}

	public static String getErrorProperty(String key,String... placeholderValues){
		return replacePlaceHolders(errorProps.getProperty(key),placeholderValues);
	}

	public static String replacePlaceHolders(String property,String[] params) {
		if(params!=null && params.length>0){
			for (int i = 0; i < params.length; i++) {
				String string = params[i];
				property=property.replaceAll(Pattern.quote("{"+i+"}"), Matcher.quoteReplacement(string));
			}
		}
		return property;
	}
}
