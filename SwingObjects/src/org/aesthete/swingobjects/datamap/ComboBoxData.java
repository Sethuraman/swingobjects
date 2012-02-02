package org.aesthete.swingobjects.datamap;

public class ComboBoxData {
	private int selectedIndex;
	private Object selectedItem;
	private boolean isIndexSet;

	public ComboBoxData(int selectedIndex, Object selectedItem) {
		super();
		this.selectedIndex = selectedIndex;
		this.selectedItem = selectedItem;
	}
	public int getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(int selectedIndex) {
		isIndexSet=true;
		this.selectedIndex = selectedIndex;
	}
	public Object getSelectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = selectedItem;
	}
	public boolean isIndexSet() {
		return isIndexSet;
	}
	public void setIndexSet(boolean isIndexSet) {
		this.isIndexSet = isIndexSet;
	}
}
