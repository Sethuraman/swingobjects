package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.YesNo;
import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.annotations.DataBeanName;
import org.aesthete.swingobjects.annotations.Required;
import org.aesthete.swingobjects.annotations.ShouldBeEmpty;
import org.aesthete.swingobjects.annotations.Trim;
import org.aesthete.swingobjects.datamap.DataMapper;
import org.aesthete.swingobjects.datamap.SwingObjData;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.view.SwingObjFormBuilder;
import org.aesthete.swingobjects.view.SwingObjFormBuilder.ButtonBarPos;
import org.aesthete.swingobjects.view.table.SwingObjTable;
import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.layout.FormLayout;

@DataBeanName("CompTest")
public class CompTest extends JFrame {

	private static final long serialVersionUID = 1L;

	@Required
	private JTextField tftest;

	@Required
	@Trim(YesNo.NO)
	private JTextField tftest1;

	@ShouldBeEmpty
	private JComboBox cbCombo;

	private JCheckBox chkBx;

	private JButton btntest;

	private SwingObjTable<TestData> table;

	public CompTest() {
		try {
			btntest = new JButton("Test");
			tftest = new JTextField();
			tftest.setColumns(20);
			tftest.setActionCommand("tftest");
			btntest.setActionCommand("btntest");
			tftest1 = new JTextField();
			tftest1.setColumns(20);
			final TestData e = new TestData(
					"test1",
					"test2asfsdfsdfasfdsaf asdfasdflaslj ahfsadlkjal akjhfalkhas afkljhadf lkafl afhalhfal alskjhfad slkfhaasfdk fafsdlkh adfs",
					"No", true);
			table = new SwingObjTable<TestData>(TestData.class);
			List<TestData> list = new ArrayList<TestData>() {
				{
					add(e);
					add(new TestData("test1", "test2", "Yes", true));
					add(new TestData("test1", "test2", "No", true));
					add(new TestData("test1", "test2", "Yes", true));
					add(new TestData("test1", "test2", "No", true));
				}
			};
			table.setData(list);
			table.makeColumnsIntoTextArea(1);
			table.makeColumnsIntoComboBox(new String[]{"Yes","No"}, 2);
			cbCombo = new JComboBox(new String[] { "Yes", "No" });
			chkBx = new JCheckBox("Is to be checked?");
			SwingObjFormBuilder builder=new SwingObjFormBuilder(new FormLayout("5dlu,100dlu:grow,2dlu,100dlu,2dlu,50dlu,2dlu,100dlu,5dlu"));
			builder.addComponents(tftest,tftest1,cbCombo,chkBx);
			builder.nextLine("150dlu");
			builder.addComponent(new JScrollPane(table), 7);
			builder.addButtonBar(7, ButtonBarPos.Center, null, btntest);
			this.setContentPane(builder.getPanel());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Action("tftest")
	public void test1(ActionEvent e) {
		System.out.println(tftest.getText());
	}

	@Action("btntest")
	public void test2(ActionEvent e) {
		System.out.println("button clicked");
		DataMapper.mapData(this);
		RequestScopeObject scopeObj = RequestScope.getRequestObj();
		SwingObjData objData = (SwingObjData) scopeObj
				.getObjectFromMap("CompTest");
		System.out.println(objData.getValue("tftest").asString());
		List<TestData> testdata=(List<TestData>)objData.getValue("table").getValue();
		System.out.println(testdata.size());
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingObjectsInit.init("/swingobjects.properties");
			CompTest test = FrameFactory
					.getNewContainer("test", CompTest.class);
			test.pack();
			test.setVisible(true);
			test.setPreferredSize(new Dimension(1200, 1200));
		} catch (SwingObjectException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
