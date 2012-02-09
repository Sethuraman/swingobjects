package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.view.table.RowDataBean;
import org.aesthete.swingobjects.view.table.SwingObjTable;
import org.jdesktop.swingx.JXFrame;

public class TableDemo {

	public static void main(String[] args) {

		try {

			//For this demo the Framework need not be initialised.. If you plan on using the entire framework, then
			//its best you initialise it before working on anything...
			SwingObjectsInit.init("swingobjects", "application",new Locale("fr", "FR"));

			//Here's the data to show on the table
			final List<Row> rows = new ArrayList<Row>();
			rows.add(new Row("Data 1", "Data 2", "Yes", true));
			rows.add(new Row("Data 3", "Data 4", "No", false));


			//Create the swing table as below.. Provide the Row.class to say that the data in the rows
			// will be from this class
			final SwingObjTable<Row> table = new SwingObjTable<Row>(Row.class);
			table.setData(rows);
			table.setVisibleRowCount(4);

			//Make any column into a combo box by calling the below method.
			//A column can be automatically made into a checkbox, by defining your property in the Row class as a boolean
			table.makeColumnsIntoComboBox(new String[] { "Yes", "No" }, 2);

			//Initialise the frame and show it on the screen
			final JXFrame frame = new JXFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new JScrollPane(table));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Row extends RowDataBean{

		@Column(index=0,key="test.column1",editable=true)
		private String column1;

		@Column(index=1,key="test.column2",editable=true)
		private String column2;

		@Column(index=2,key="test.column3",editable=true)
		private String column3;

		@Column(index=3,key="test.column4",editable=true)
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


