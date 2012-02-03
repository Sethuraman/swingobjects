package test;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.event.TableColumnModelExtListener;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;

public class MultiLineCellRenderer {

	public static void main(String[] args) {

		Object[][] rows = new Object[30][];

		for (int i = 0; i < rows.length; i++) {
		rows[i] = new Object[]{
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed ",
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed ",
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed ",
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed ",
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed ",
		"some really, maybe really really long text - wrappit .... where needed ", "some really, maybe really really long text - wrappit .... where needed "};
		}

		DefaultTableModel model = new DefaultTableModel(rows,
		new String[]{
		"Title 1", "Title 2",
		"Title 3", "Title 4",
		"Title 5", "Title 6",
		});

		final JXTable table = new JXTable(model);

		table.setColumnControlVisible(true);
		table.setRowHeightEnabled(true);

		table.getColumnExt(0).setCellRenderer(
				new DefaultTableRenderer(new TextAreaProvider()));
		table.getColumnExt(1).setCellRenderer(
				new DefaultTableRenderer(new TextAreaProvider()));

		table.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				for (int row = 0; row < table.getRowCount(); row++) {
					int rowHeight = 0;
					for (int column = 0; column < table.getColumnCount(); column++) {
						Component comp = table.prepareRenderer(
								table.getCellRenderer(row, column), row, column);
						rowHeight = Math.max(rowHeight,
								comp.getPreferredSize().height);
					}
					table.setRowHeight(row, rowHeight);
				}
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		});

		table.addHighlighter(HighlighterFactory.createAlternateStriping());

		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(table);

		JFrame frame = new JFrame();
		frame.add(jScrollPane);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * use a JTextArea as rendering component.
	 */
	public static class TextAreaProvider extends ComponentProvider<JTextArea> {

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
}
