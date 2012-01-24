package org.aesthete.swingobjects;

import com.jgoodies.forms.layout.LayoutMap;

public class FormLayoutConfig {
	
	public static void init(){
		LayoutMap.getRoot().columnPut("lbl", SwingObjProps.getProperty("layout.label"));
		LayoutMap.getRoot().columnPut("tf",SwingObjProps.getProperty("layout.textfield"));
		LayoutMap.getRoot().columnPut("columngap",SwingObjProps.getProperty("layout.columngap"));
		LayoutMap.getRoot().rowPut("rowgap",SwingObjProps.getProperty("layout.rowgap"));
		LayoutMap.getRoot().rowPut("rowsegap",SwingObjProps.getProperty("layout.rowsepgap"));
		LayoutMap.getRoot().rowPut("rowbtngap", SwingObjProps.getProperty("layout.rowbtngap"));
		LayoutMap.getRoot().rowPut("row", SwingObjProps.getProperty("layout.row"));
	}

}
