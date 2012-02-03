package org.aesthete.swingobjects.view.table;

import javax.swing.JTextArea;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;

public class TextAreaProvider extends ComponentProvider<JTextArea> {

	private static final long serialVersionUID = 1L;
	@Override
	protected void configureState(CellContext context) {
		JXTable table = (JXTable) context.getComponent();
		int columnWidth = table.getColumn(context.getColumn()).getWidth();
		rendererComponent.setSize(columnWidth, Short.MAX_VALUE);
	}

	@Override
	protected JTextArea createRendererComponent() {
		JTextArea area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setOpaque(true);
		return area;
	}

	@Override
	protected void format(CellContext context) {
		rendererComponent.setText(getValueAsString(context));
	}
}
