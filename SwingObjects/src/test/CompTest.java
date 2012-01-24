package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.annotations.DataClass;
import org.aesthete.swingobjects.annotations.Validate;
import org.aesthete.swingobjects.annotations.ValidateTypes;
import org.aesthete.swingobjects.annotations.Validator;
import org.aesthete.swingobjects.view.FrameFactory;

@DataClass(TestData.class)
public class CompTest extends JFrame{

	private static final long serialVersionUID = 1L;

	@Validate({@Validator({ValidateTypes.Required,ValidateTypes.Date})})
	private JTextField tftest;
	
	@Validate({@Validator({ValidateTypes.Required})})
	private JTextField tftest1;
	
	private JComboBox cbCombo;
	
	private JCheckBox chkBx;
	
	
	private JButton btntest;
	
	public CompTest() {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		btntest=new JButton("Test");
		tftest=new JTextField();
		tftest.setColumns(20);
		tftest.setActionCommand("tftest");
		btntest.setActionCommand("btntest");
		tftest1=new JTextField();
		tftest1.setColumns(20);
		
		cbCombo=new JComboBox(new String[]{"Yes","No"});
		chkBx=new JCheckBox("Is to be checked?");
		panel.add(tftest);
		panel.add(tftest1);
		panel.add(cbCombo);
		panel.add(chkBx);
		panel.add(btntest);
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Action("tftest")
	public void test1(ActionEvent e){
		System.out.println(tftest.getText());
	}
	
	@Action("btntest")
	public void test2(ActionEvent e){
		System.out.println("button clicked");
	}
	public static void main(String[] args) {
		CompTest test=FrameFactory.getNewContainer("test", CompTest.class);
		test.pack();
		test.setVisible(true);
		test.setPreferredSize(new Dimension(200, 200));
	}
}
