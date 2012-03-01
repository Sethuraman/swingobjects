package test;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.view.SwingObjFormBuilder;
import org.aesthete.swingobjects.view.SwingObjFormBuilder.ButtonBarPos;
import org.aesthete.swingobjects.view.table.RowDataBean;
import org.aesthete.swingobjects.view.table.SwingObjTable;

import com.jgoodies.forms.debug.FormDebugPanel;

/**
 * This is to showcase the SwingObjBuilder class. The components in this frame are to be laid out as per the below
 * diagram.
 * <div style="clear:both"/>
 * <img src='BuilderShowcasewithoutgrid.png'/>
 *<div style="clear:both"/>
 *<br/>
 *<br/>
 * To help you understand how the components have been laid out, here's a look at the same image with the grid lines
 * drawn with the help of the FormDebugPanel from jgoodies.
 * <div style="clear:both"/>
 * <img src='BuilderShowcase-withgrid.png'/>
 * @author sethu
 *
 */
public class BuilderShowcase extends JFrame{

	private JTextField tfCol1Tf1;
	private JTextField tfCol2Tf1;
	private JTextField tfCol1Tf2;
	private JTextField tfCol2Tf2;

	private JTable testTable;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnSave;

	public static void main(String[] args) {
		try {
			SwingObjectsInit.init("swingobjects", "application");
			BuilderShowcase showcase=FrameFactory.getNewContainer("test", BuilderShowcase.class);
			showcase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			CommonUI.showOnScreen(showcase);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BuilderShowcase() {
		initComponents();
		layoutComponents();
	}

	private void layoutComponents() {
		SwingObjFormBuilder builder=new SwingObjFormBuilder("10dlu,fill:pref,10dlu,fill:150dlu:grow,20dlu:grow,fill:pref,10dlu,fill:150dlu:grow,5dlu,20dlu,10dlu");
		builder.addComponentsToCenter(null, 9, 2, new JLabel("A Test for the SwingObjects Builder"));
		builder.nextLine();
		builder.addLblValAndComp("Text field 1", tfCol1Tf1);
		builder.addLblValAndComp("Text Field 2", tfCol2Tf1);
		builder.nextLine();
		builder.addLblValAndComp("Text field 3", tfCol1Tf2);
		builder.addLblValAndComp("Text Field 4", tfCol2Tf2);
		builder.addLabeledSeparatorFromValue("There'll be a table below");
		builder.nextFewLines("$rowgap",
															"$row", // for add button and start of table
															"$rowgap",
															"$row",// for del button
															"$rowgap",
															"fill:100dlu:grow");// remaining space for the table

		builder.addComponent(new JScrollPane(testTable),7,5);
		builder.addComponent(btnAdd);
		builder.nextLinePlain();// dont append any more rows.. just increment the row counter
		builder.setColumn(10);//column where the del button needs to be placed.
		builder.addComponent(btnDel);
		//all components addedd.. now skip to the last row
		builder.goToLastRow();
		builder.addButtonBar(9, ButtonBarPos.Center, null, btnSave);
		builder.complete();

		this.setContentPane(builder.getPanel());
	}

	private void initComponents() {
		testTable=new SwingObjTable<Row>(Row.class);
		btnAdd=new JButton("+");
		btnDel=new JButton("-");
		btnSave=new JButton("Save");
		tfCol1Tf1=new JTextField();
		tfCol1Tf2=new JTextField();
		tfCol2Tf1=new JTextField();
		tfCol2Tf2=new JTextField();
	}

	public static class Row extends RowDataBean{

		@Column(index=0,name="Column1",editable=true)
		private String column1;

		@Column(index=1,name="Column2",editable=true)
		private String column2;

		@Column(index=2,name="Column3",editable=true)
		private String column3;

		@Column(index=3,name="Column4",editable=true)
		private boolean column4;

		public Row(String column1, String column2, String column3, boolean column4) {
			super();
			this.column1 = column1;
			this.column2 = column2;
			this.column3 = column3;
			this.column4 = column4;
		}
		public String getColumn1() {
			return column1;
		}
		public void setColumn1(String column1) {
			this.column1 = column1;
		}
		public String getColumn2() {
			return column2;
		}
		public void setColumn2(String column2) {
			this.column2 = column2;
		}
		public String getColumn3() {
			return column3;
		}
		public void setColumn3(String column3) {
			this.column3 = column3;
		}
		public boolean getColumn4() {
			return column4;
		}
		public void setColumn4(boolean column4) {
			this.column4 = column4;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((column1 == null) ? 0 : column1.hashCode());
			result = prime * result + ((column2 == null) ? 0 : column2.hashCode());
			result = prime * result + ((column3 == null) ? 0 : column3.hashCode());
			result = prime * result + (column4 ? 1231 : 1237);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Row other = (Row) obj;
			if (column1 == null) {
				if (other.column1 != null)
					return false;
			} else if (!column1.equals(other.column1))
				return false;
			if (column2 == null) {
				if (other.column2 != null)
					return false;
			} else if (!column2.equals(other.column2))
				return false;
			if (column3 == null) {
				if (other.column3 != null)
					return false;
			} else if (!column3.equals(other.column3))
				return false;
			if (column4 != other.column4)
				return false;
			return true;
		}
	}
}
