package org.aesthete.swingobjects.view.table;

import java.awt.Color;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;

public class ComboBoxProvider extends ComponentProvider<JComboBox> {

	private static final long serialVersionUID = 1L;
	private JComboBox box;

	public ComboBoxProvider(ComboBoxModel model){
		box.setModel(model);
	}

	@Override
	protected void configureState(CellContext context) {
		box.setForeground(Color.black);
	}

	@Override
	protected JComboBox createRendererComponent() {
		box = new JComboBox();
		box.setForeground(Color.black);
		return box;
	}

	@Override
	protected void format(CellContext context) {
		box.setForeground(Color.black);
		rendererComponent.setSelectedItem(context.getValue());
	}
}
