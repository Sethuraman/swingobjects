package org.aesthete.swingobjects.view.table;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;

public class ComboBoxProvider extends ComponentProvider<JComboBox> {

	private static final long serialVersionUID = 1L;
	private JComboBox box;
	
	public ComboBoxProvider(Object[] objs){
		box.setModel(new DefaultComboBoxModel(objs));
	}
	
	@Override
	protected void configureState(CellContext context) {
		rendererComponent.setSelectedItem(context.getValue());
		JXTable table = (JXTable) context.getComponent();
		int columnWidth = table.getColumn(context.getColumn()).getWidth();
		rendererComponent.setSize(columnWidth, Short.MAX_VALUE);
	}

	@Override
	protected JComboBox createRendererComponent() {
		box = new JComboBox();
		return box;
	}

	@Override
	protected void format(CellContext context) {
		rendererComponent.setSelectedItem(context.getValue());
	}
}
