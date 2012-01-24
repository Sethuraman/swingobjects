package org.aesthete.swingobjects;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.apache.log4j.PropertyConfigurator;

public class SwingObjectsInit {

	public static void init(String swingObjPropsFromClasspath) throws SwingObjectException {
		try {
			configureLog4j();
			SwingObjProps.init(swingObjPropsFromClasspath);
			FormLayoutConfig.init();
		}catch(SwingObjectException e){
			throw e;
		} catch (Exception e) {
			throw new SwingObjectException(
					"Error initialising the swing objects framework", e,
					ErrorSeverity.SEVERE, SwingObjectsInit.class);
		}
	}

	private static void configureLog4j() throws IOException {
		URL resource = SwingObjectsInit.class.getResource("log4j.properties");
		if(resource==null){
			Properties props = new Properties();
			props.load(SwingObjectsInit.class
					.getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(props);
		}else{
			PropertyConfigurator.configure(resource.getFile());
		}
		
	}

}
