package org.aesthete.swingobjects.view.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.util.FieldCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;

public class SwingObjTableModel<T extends Object> extends AbstractTableModel{

	private Class<T> t;
	private static final long serialVersionUID = 1;
	private Map<Integer,ColumnInfo> columns;
	private List<T> rows;
	private boolean isTableEditable;

	public SwingObjTableModel(Class<T> t){
		this.t=t;
		columns=new HashMap<Integer, ColumnInfo>();
		init();
		rows=new ArrayList<T>();
	}

	private void init() {
		ReflectionUtils.iterateOverFields(t, null, new FieldCallback() {
			private Column column;
			@Override
			public boolean filter(Field field) {
				column=field.getAnnotation(Column.class);
				return column!=null;
			}

			@Override
			public void consume(Field field){
				ColumnInfo info=new ColumnInfo();
				info.setFieldName(field.getName());
				info.setIndex(column.index());
				info.setHeading(column.name());
				info.setEditable(column.editable());
				if(column.editable()) {
					isTableEditable=true;
				}
				if(column.type()==Class.class){
					info.setType(ClassUtils.primitiveToWrapper(field.getType()));
				}else{
					info.setType(column.type());
				}
				columns.put(column.index(), info);
			}
		});
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return columns.get(col).getType();
	}

	@Override
	public String getColumnName(int col) {
		return columns.get(col).getHeading();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int col) {
		return columns.get(col).isEditable();
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		try {
			if (rows == null) {
				return;
			} else {
				if (rows.size() <= row) {
					T obj = t.newInstance();
					rows.add(row, obj);
					PropertyUtils.setProperty(obj, columns.get(column).getFieldName(), value);
					fireTableRowsInserted(row, row);
				} else {
					T obj = rows.get(row);
					PropertyUtils.setProperty(obj, columns.get(column).getFieldName(), value);
					fireTableCellUpdated(row, column);
				}

			}
		} catch (Exception e) {
			throw new SwingObjectRunException("Error while setting value into Table Model", e, ErrorSeverity.SEVERE, SwingObjTableModel.class);
		}
	}

	public void setData(List<T> rows){
		this.rows=rows;
		fireTableDataChanged();
	}

	public void addRow(T row){
		if(rows==null){
			rows=new ArrayList<T>();
		}
		int rowInserted = rows.size();
		rows.add(row);
		fireTableRowsInserted(rowInserted, rowInserted);
	}

	public void addRows(List<T> newRows){
		if(rows==null){
			rows=new ArrayList<T>();
		}
		int rowInserted = rows.size();
		rows.addAll(newRows);
		fireTableRowsInserted(rowInserted, rows.size()-1);
	}

	public void deleteRows(int[] rowsToBeDel) {
		if(rows==null){
			return;
		}
		Arrays.sort(rowsToBeDel);
		List<T> newrows=new ArrayList<T>();
		for(int i=0;i<rows.size();i++) {
			if(Arrays.binarySearch(rowsToBeDel, i)<0) {
				newrows.add(rows.get(i));
			}
		}

		rows=newrows;

		fireTableRowsDeleted(rowsToBeDel[0],rowsToBeDel[rowsToBeDel.length-1]);
	}

	public int getColumnCount() {
		return columns.size();
	}

	public int getRowCount() {
		if(rows==null){
			return 0;
		}else{
			return rows.size();
		}
	}

	public Object getValueAt(int row, int column) {
		try {
			if (rows == null) {
				return null;
			} else {
				if (rows.size() <= row) {
					throw new SwingObjectRunException(ErrorSeverity.SEVERE,String.format("SwingObjTableModel rows-%s less than row %s", rows.size(), row), SwingObjTableModel.class);
				} else {
					T t = rows.get(row);
					if (t == null) {
						return null;
					}
					return PropertyUtils.getProperty(t, columns.get(column).getFieldName());
				}
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(ErrorSeverity.SEVERE,"Error while setting value into Table Model", SwingObjTableModel.class);
		}
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void setRow(int i,T row){
		if(rows==null){
			rows=new ArrayList<T>();
		}
		int rowInserted = rows.size();
		if(rowInserted<=i){
			rows.add(row);
		}else{
			rows.set(i, row);
		}
		fireTableRowsInserted(i, i);
	}

	public boolean isTableEditable() {
		return isTableEditable;
	}

	public void setTableEditable(boolean isTableEditable) {
		this.isTableEditable = isTableEditable;
	}

	public Map<Integer, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<Integer, ColumnInfo> columns) {
		this.columns = columns;
	}
}
