package test;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.aesthete.swingobjects.ActionProcessor;
import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.YesNo;
import org.aesthete.swingobjects.annotations.*;
import org.aesthete.swingobjects.datamap.SwingObjData;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.scope.RequestScopeObject;
import org.aesthete.swingobjects.view.*;
import org.aesthete.swingobjects.view.SwingObjFormBuilder.ButtonBarPos;
import org.aesthete.swingobjects.view.table.SwingObjTable;
import org.aesthete.swingobjects.view.validator.Validator;
import org.aesthete.swingobjects.workers.CommonSwingWorker;

import com.jgoodies.forms.layout.FormLayout;

@DataBeanName("CompTest")
@TitleIconImage("Component Test")
public class CompTest extends JFrame{

	private static final long serialVersionUID = 1L;

	@Required
    @ValidDate
	private DateTextField tftest;

	@Required
	@Trim(YesNo.NO)
	private JTextField tftest1;

	@Required
	private JComboBox cbCombo;

	private JCheckBox chkBx;

    @ConfirmBeforeDoing(message = "Are you sure you want to click this?")
	private JButton btntest;

	private SwingObjTable<TestData> table;

	public CompTest() {
		try {
			btntest = new JButton("Test");
			tftest = new DateTextField("12/12/2012");
			tftest.setColumns(20);
			tftest.setActionCommand("tftest");
			btntest.setActionCommand("btntest");
			tftest1 = new JTextField("123254562634");
			tftest1.setColumns(20);

			table = new SwingObjTable<TestData>(TestData.class);
			List<TestData> list = new ArrayList<TestData>() {
				{
					add(new TestData("test1","test2","No", true));
					add(new TestData("test1", "test2", "Yes", true));
					add(new TestData("test1", "test2", "No", true));
					add(new TestData("test1", "test2", "Yes", true));
					add(new TestData("test1", "test2", "No", true));
				}
			};
			table.setData(list);
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

	@Action(value="tftest")
	public void test1(ActionEvent e) {
		System.out.println(tftest.getText());
	}

	@Action("btntest")
	public void test2(ActionEvent e) {
		System.out.println("button clicked");
		CommonSwingWorker worker=new CommonSwingWorker("btntest") {

			@Override
			public boolean validateAndPopulate(RequestScopeObject scopeObj) {
				SwingObjData objData = (SwingObjData) scopeObj.getObjectFromMap("CompTest");
				if(!"12/12/2012".equals(objData.getValue("tftest").asString())) {
					CommonUI.setErrorBorderAndTooltip(tftest,"Has to be \"12/12/2012\"");
					return false;
				}
				return true;
			}

			@Override
			public void callModel(RequestScopeObject scopeObj) throws SwingObjectException {
				WaitDialog.appendWaitDialogMessage("\nReplaced text with this");
				// sleep to simulate long running task
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SwingObjData objData = (SwingObjData) scopeObj.getObjectFromMap("CompTest");
				System.out.println(objData.getValue("tftest").asString());
				List<TestData> testdata=(List<TestData>)objData.getValue("table").getValue();
				testdata.get(0).setTftest1("Changed row data");
				objData.set("table", testdata);
				System.out.println(testdata.size());
				objData.set("tftest","19/12/2019");
                objData.set("tftest1","Changed tftest2");
			}

			@Override
			public void callConnector(RequestScopeObject scopeObj) {
				JOptionPane.showMessageDialog(CompTest.this, "Model finished, now you can update your GUI");
			}
		};

		ActionProcessor.processAction(this, worker);

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingObjectsInit.init("swingobjects","application");
			CompTest test = FrameFactory.getNewContainer("test", CompTest.class);
            test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			CommonUI.showOnScreen(test);
		} catch (SwingObjectException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
