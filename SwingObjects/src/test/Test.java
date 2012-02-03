package test;

import java.awt.Color;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

public class Test {

	public static void main(String[] args) {
		JXFrame frame=new JXFrame();
		Object[][] rows = new Object[10][];

		for (int i = 0; i < rows.length; i++) {
		rows[i] = new Object[]{
		"Test data ","Yes"};
		}

		DefaultTableModel model = new DefaultTableModel(rows,
		new String[]{
		"Title 1", "Title 2"
		});

		final JXTable table = new JXTable(model);
		table.getColumnExt(1).setCellRenderer(new DefaultTableRenderer(new ComboBoxProvider(new DefaultComboBoxModel(new String[] {"Yes","No","Maybe"}))));
		table.getColumnExt(1).setCellEditor(new ComboBoxEditor(new DefaultComboBoxModel(new String[] {"Yes","No","Maybe"})));
		table.setVisibleRowCount(10);
		frame.setContentPane(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

class ComboBoxEditor extends DefaultCellEditor {
    public ComboBoxEditor(ComboBoxModel model) {
        super(new JComboBox(model));
    }
}

class ComboBoxProvider extends ComponentProvider<JComboBox> {
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


