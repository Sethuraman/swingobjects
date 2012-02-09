package org.aesthete.swingobjects;

import com.jgoodies.forms.layout.LayoutMap;

public class FormLayoutConfig {
	
	public static void init(){
		LayoutMap.getRoot().columnPut("lbl", SwingObjProps.getSwingObjProperty("layout.label"));
		LayoutMap.getRoot().columnPut("tf",SwingObjProps.getSwingObjProperty("layout.textfield"));
		LayoutMap.getRoot().columnPut("columngap",SwingObjProps.getSwingObjProperty("layout.columngap"));
		LayoutMap.getRoot().rowPut("rowgap",SwingObjProps.getSwingObjProperty("layout.rowgap"));
		LayoutMap.getRoot().rowPut("rowsegap",SwingObjProps.getSwingObjProperty("layout.rowsepgap"));
		LayoutMap.getRoot().rowPut("rowbtngap", SwingObjProps.getSwingObjProperty("layout.rowbtngap"));
		LayoutMap.getRoot().rowPut("row", SwingObjProps.getSwingObjProperty("layout.row"));
	}

}
