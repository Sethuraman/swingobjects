package org.aesthete.swingobjects.view.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.aesthete.swingobjects.datamap.converters.ConverterUtils.SwingObjTableConverter;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.table.TableColumnExt;

public class SwingObjTable<T extends RowDataBean> extends JXTable {
	private static final long serialVersionUID = 1L;
	private boolean isSingleSelection = true; // false for multiple selection
	protected Map<Integer, Color> shadedColsWithColor = new HashMap<Integer, Color>();
	protected Map<Integer, Color> shadedRowsWithColor = new HashMap<Integer, Color>();
	private boolean isRowColorPrecedence;
	private T prototypeData;
	private SwingObjTableModel<T> model;

	public SwingObjTable(Class<T> classOfData, T protoDataForSizing) {
		this.prototypeData = protoDataForSizing;
		initTable(classOfData);
	}

	public SwingObjTable(Class<T> classOfData) {
		initTable(classOfData);
	}

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

	public void initTable(Class<T> dataClass) {
		model = new SwingObjTableModel<T>(dataClass);
		initTable(model);
	}

	public void initTable(SwingObjTableModel<T> model) {
		try {
			this.model = model;
			setModel(model);
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
					TableColumnExt columnExt = getColumnExt(colInfo.getIndex());
					columnExt.setPrototypeValue(PropertyUtils.getProperty(
							prototypeData, colInfo.getFieldName()));
				}
			}
			this.setVisibleColumnCount(model.getColumns().size());
			this.addHighlighter(HighlighterFactory.createAlternateStriping());
		} catch (Exception e) {
			throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, SwingObjTable.class);
		}
	}

	public void makeColumnsIntoComboBox(Object[] values,int... cols){
		for(int col : cols){
			getColumnExt(col).setCellRenderer((new DefaultTableRenderer(new ComboBoxProvider(new DefaultComboBoxModel(values)))));
			getColumnExt(col).setCellEditor(new ComboBoxEditor(new DefaultComboBoxModel(values)));
		}
	}

	public static class ComboBoxEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;

		public ComboBoxEditor(ComboBoxModel model) {
            super(new JComboBox(model));
        }
    }

	/**
	 * This method has serious performance issues when huge set of rows (tested with 3000)
	 * is added. This method makes use of the same things described here:
	 * <a href="http://home.java.net/node/688840">http://home.java.net/node/688840</a>
	 * @param cols
	 */
	public void makeColumnsIntoTextArea(int... cols){
		for(int col : cols){
			getColumnExt(col).setCellRenderer((new DefaultTableRenderer(new TextAreaProvider())));
		}
//		setRowHeightEnabled(true);
		addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				for (int row = 0; row < getRowCount(); row++) {
					int rowHeight = 0;
					for (int column = 0; column < getColumnCount(); column++) {
						Component comp = prepareRenderer(
								getCellRenderer(row, column), row, column);
						rowHeight = Math.max(rowHeight,
								comp.getPreferredSize().height);
					}
					setRowHeight(row, rowHeight);
				}
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	/**
	 * Need to supress the warning, otherwise from
	 * {@link SwingObjTableConverter} there will be errors while working with
	 * generic types. Ensure that the List passed in has only T objects stored
	 * in it.
	 *
	 * @param data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setData(List data) {
		model.setData(data);
		packAll();
	}

	public void addRow(T row) {
		model.addRow(row);
		packAll();
	}

	public void addRows(List<T> rows) {
		model.addRows(rows);
		packAll();
	}

	public void delRows(int... rows) {
		model.deleteRows(rows);
		packAll();
	}

	public void setRow(int row, T data) {
		model.setRow(row, data);
		packAll();
	}

	public List<T> getData() {
		return model.getRows();
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

    public int getSelectedRowAfterModelConversion(){
        int selectedRow = getSelectedRow();
        if(selectedRow>-1){
            return convertRowIndexToModel(selectedRow);
        }
        return -1;
    }
}
