package org.aesthete.swingobjects;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.datamap.converters.ConverterUtils;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JComboBoxConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JTextComponentConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JToggleButtonConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.SwingObjTableConverter;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.view.table.SwingObjTable;
import org.apache.log4j.PropertyConfigurator;

public class SwingObjectsInit {

	public static void init(String swingObjPropsFromClasspath,String errorPropsFromClasspath) throws SwingObjectException {
		try {
			configureLog4j();
			SwingObjProps.init(swingObjPropsFromClasspath,errorPropsFromClasspath);
			FormLayoutConfig.init();
			initConverters();
		}catch(SwingObjectException e){
			throw e;
		} catch (Exception e) {
			throw new SwingObjectException(e,ErrorSeverity.SEVERE, SwingObjectsInit.class);
		}
	}

	private static void initConverters() {
		ConverterUtils.registerConverter(JTextComponent.class, new JTextComponentConverter());
		ConverterUtils.registerConverter(JComboBox.class, new JComboBoxConverter());
		ConverterUtils.registerConverter(JToggleButton.class, new JToggleButtonConverter());
		ConverterUtils.registerConverter(SwingObjTable.class, new SwingObjTableConverter());
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
