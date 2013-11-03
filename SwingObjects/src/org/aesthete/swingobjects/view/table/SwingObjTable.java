package org.aesthete.swingobjects.view.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.SwingObjTableConverter;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.table.TableColumnExt;

public class SwingObjTable<$ModelData extends RowDataBean> extends JXTable {
	private static final long serialVersionUID = 1L;
	private boolean isSingleSelection = true; // false for multiple selection
	protected Map<Integer, Color> shadedColsWithColor = new HashMap<Integer, Color>();
	protected Map<Integer, Color> shadedRowsWithColor = new HashMap<Integer, Color>();
	private boolean isRowColorPrecedence;
	private $ModelData prototypeData;
	private SwingObjTableModel<$ModelData> model;
    private Class<$ModelData> classOfData;

    public SwingObjTable(Class<$ModelData> classOfData, $ModelData protoDataForSizing) {
        this.classOfData = classOfData;
        this.prototypeData = protoDataForSizing;
		initTable(classOfData);
	}

	public SwingObjTable(Class<$ModelData> classOfData) {
        this.classOfData = classOfData;
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

	public void initTable(Class<$ModelData> dataClass) {
		model = new SwingObjTableModel<$ModelData>(dataClass);
		initTable(model);
	}

	public void initTable(SwingObjTableModel<$ModelData> model) {
		try {
			this.model = model;
			setModel(model);
			setColumnControlVisible(true);
			setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
			getColumnExt(col).setCellRenderer((new DefaultTableRenderer(new ComboBoxProvider(new DefaultComboBoxModel(values), false))));
			getColumnExt(col).setCellEditor(new ComboBoxEditor(new DefaultComboBoxModel(values), false));
		}
	}

    public void makeColumnsIntoEditableComboBox(Object[] initialValues, int... cols){
        for(int col : cols){
            getColumnExt(col).setCellRenderer(new DefaultTableRenderer(new ComboBoxProvider(new DefaultComboBoxModel(initialValues), true)));
            getColumnExt(col).setCellEditor(new ComboBoxEditor(new DefaultComboBoxModel(initialValues), true));
        }
    }

    public void addValueToComboBoxColumn(Object value, int col){
        ((ComboBoxProvider)((DefaultTableRenderer)getColumnExt(col).getCellRenderer()).getComponentProvider()).addValueToModel(value);
        ((ComboBoxEditor)getColumnExt(col).getCellEditor()).addValueToModel(value);
    }

    public void makeColumnsIntoADate(int... cols) {
        for(int col : cols) {
            new TableCellDatePicker(this, col);
            DefaultTableCellRenderer renderer =new DefaultTableCellRenderer();
            renderer.setToolTipText("<html>Right click to launch the Calendar<br/>" +
                    "Else enter date in dd/mm/yyyy format</html>");
            getColumnExt(col).setCellRenderer(renderer);
            this.getColumnModel().getColumn(col).setCellRenderer(renderer);
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
	 * generic types. Ensure that the List passed in has only $ModelData objects stored
	 * in it.
	 *
	 * @param data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setData(List data) {
		model.setData(data);
		packAll();
	}

    public void addRow(int i, $ModelData row){
        model.addRow(i, row);
        packAll();
    }

	public void addRow($ModelData row) {
		model.addRow(row);
		packAll();
	}

	public void addRows(List<$ModelData> rows) {
		model.addRows(rows);
		packAll();
	}

	public void delRows(int... rows) {
		model.deleteRows(rows);
		packAll();
	}

	public void setRow(int row, $ModelData data) {
		model.setRow(row, data);
		packAll();
	}

	public List<$ModelData> getData() {
		return model.getRows();
	}

	public boolean isSingleSelection() {
		return isSingleSelection;
	}

	public void setSingleSelection(boolean isSingleSelection) {
		this.isSingleSelection = isSingleSelection;
        if (isSingleSelection) {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
	}

	public boolean isRowColorPrecedence() {
		return isRowColorPrecedence;
	}

	public void setRowColorPrecedence(boolean isRowColorPrecedence) {
		this.isRowColorPrecedence = isRowColorPrecedence;
	}

	public $ModelData getPrototypeData() {
		return prototypeData;
	}

	public void setPrototypeData($ModelData prototypeData) {
		this.prototypeData = prototypeData;
	}

    public int getSelectedRowAfterModelConversion(){
        int selectedRow = getSelectedRow();
        if(selectedRow>-1){
            return convertRowIndexToModel(selectedRow);
        }
        return -1;
    }

    public int getColumnIndex(String columnVariableName){
        for(Class<?> c=classOfData; c!=null  ; c=c.getSuperclass()) {
            for(Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                Column column = f.getAnnotation(Column.class);
                if(column!=null && f.getName().equals(columnVariableName)){
                    return column.index();
                }
            }
        }
        return -1;
    }
}
