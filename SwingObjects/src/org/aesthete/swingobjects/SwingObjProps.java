package org.aesthete.swingobjects;

import java.util.Locale;
import java.util.ResourceBundle;
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

	private static ResourceBundle swingObjProps;
	private static ResourceBundle errorProps;

	public static void init(String swingObjPropsFile, String errorPropsFile,Locale locale) throws SwingObjectException {
		try {
			swingObjProps = ResourceBundle.getBundle(swingObjPropsFile, locale);
			errorProps = ResourceBundle.getBundle(errorPropsFile, locale);
		} catch (Exception e) {
			throw new SwingObjectException("Error loading swingobjects properties", e, ErrorSeverity.SEVERE, SwingObjProps.class);
		}
	}

	public static String getSwingObjProperty(String key,Object... placeHolderValues) {
		return replacePlaceHolders(swingObjProps.getString(key),placeHolderValues);
	}

	public static String getApplicationProperty(String key,Object... placeholderValues){
		return replacePlaceHolders(errorProps.getString(key),placeholderValues);
	}

	public static String replacePlaceHolders(String property,Object[] params) {
		if(params!=null && params.length>0){
			for (int i = 0; i < params.length; i++) {
				Object object = params[i];
				property=property.replaceAll(Pattern.quote("{"+i+"}"), Matcher.quoteReplacement(object.toString()));
			}
		}
		return property;
	}
}
