package org.aesthete.swingobjects.view.table;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;

public class SwingObjTable<T> extends JXTable{
	private boolean isSingleSelection=true; // false for multiple selection
	protected Map<Integer,Color> shadedColsWithColor=new HashMap<Integer,Color>();
	protected Map<Integer, Color> shadedRowsWithColor=new HashMap<Integer, Color>();
	private boolean isRowColorPrecedence;
	private T prototypeData;

	public Map<Integer, Color> getShadedRowsWithColor() {
		return shadedRowsWithColor;
	}

	public void setShadedRowsWithColor(Map<Integer, Color> shadedRowsWithColor) {
		this.shadedRowsWithColor = shadedRowsWithColor;
	}

	public Map<Integer, Color> getShadedColsWithColor() {
		return shadedColsWithColor;
	}

	public void setShadedColsWithColor(Map<Integer, Color> shadedColsWithColor) {
		this.shadedColsWithColor = shadedColsWithColor;
	}


	public void initTable(Class<T> dataClass){
		SwingObjTableModel<T> model=new SwingObjTableModel<T>(dataClass);
		initTable(model);
	}

	public void initTable(SwingObjTableModel<T> model) {
		try {
			setColumnControlVisible(true);
			setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			if (isSingleSelection) {
				setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			} else {
				setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}
			this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			if (prototypeData != null) {
				for (ColumnInfo colInfo : model.getColumns().values()) {
					getColumnExt(colInfo.getIndex()).setPrototypeValue(PropertyUtils.getProperty(prototypeData, colInfo.getFieldName()));
				}
			}
		} catch (Exception e) {
			throw new SwingObjectRunException("Error while initialising table",e, ErrorSeverity.SEVERE, SwingObjTable.class);
		}
	}

	public boolean isSingleSelection() {
		return isSingleSelection;
	}

	public void setSingleSelection(boolean isSingleSelection) {
		this.isSingleSelection = isSingleSelection;
	}

	public boolean isRowColorPrecedence() {
		return isRowColorPrecedence;
	}

	public void setRowColorPrecedence(boolean isRowColorPrecedence) {
		this.isRowColorPrecedence = isRowColorPrecedence;
	}

	public T getPrototypeData() {
		return prototypeData;
	}

	public void setPrototypeData(T prototypeData) {
		this.prototypeData = prototypeData;
	}
}
