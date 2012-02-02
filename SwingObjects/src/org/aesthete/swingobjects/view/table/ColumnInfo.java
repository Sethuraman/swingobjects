package org.aesthete.swingobjects.view.table;

public class ColumnInfo {
	private String heading;
	private int index;
	private Class<?> type;
	private String fieldName;
	private boolean isEditable;
	private String protoData;
	public String getHeading() {
		return heading;
	}
	public void setHeading(String name) {
		this.heading = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	public String getProtoData() {
		return protoData;
	}
	public void setProtoData(String protoData) {
		this.protoData = protoData;
	}
}
