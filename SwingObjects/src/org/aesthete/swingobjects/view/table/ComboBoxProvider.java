package org.aesthete.swingobjects.view.table;

import java.awt.Color;

import javax.swing.*;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;

public class ComboBoxProvider extends ComponentProvider<JComboBox> {

	private static final long serialVersionUID = 1L;
	private JComboBox box;
    private DefaultComboBoxModel model;

    public ComboBoxProvider(DefaultComboBoxModel model, boolean editableComboBox){
        this.model = model;
        box.setModel(model);
        box.setEditable(editableComboBox);
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

    public void addValueToModel(Object value){
       model.addElement(value);
    }
}
