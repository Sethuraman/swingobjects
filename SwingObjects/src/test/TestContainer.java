package test;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;

import test.Test.MyFrame;
import test.Test.MyPanel;

public class TestContainer {


	public static class MyFrame extends JFrame{

		//a field that implements the Components interface and has other JComponents in it
		private MySetOfComponents components;
		private JLabel lbl2;
		private JButton btn2;

		public MyFrame(MySetOfComponents components) {
			this.components=components;
			btn2=new JButton();
			btn2.setActionCommand("PerformTestBtn2");
		}

		@Action("PerformTestBtn2")
		public void performTestBtn2() {

		}
	}

	public static class MySetOfComponents implements Components{
		private JLabel label1;
		private JTextField textfield1;
		private JButton btn1;

		public MySetOfComponents() {
			btn1=new JButton("Test");
			btn1.setActionCommand("PerformTestBtn1");

			textfield1=new JTextField();
			textfield1.setActionCommand("PerformTestTf1");
		}

		public JLabel getLabel1() {
			return label1;
		}
		public void setLabel1(JLabel label1) {
			this.label1 = label1;
		}
		public JTextField getTextfield1() {
			return textfield1;
		}
		public void setTextfield1(JTextField textfield1) {
			this.textfield1 = textfield1;
		}
		public JButton getBtn1() {
			return btn1;
		}
		public void setBtn1(JButton btn1) {
			this.btn1 = btn1;
		}

		@Action("PerformTestBtn1")
		public void performtestbtn1(ActionEvent e) {
			//do something
		}

		@Action("PerformTestTf1")
		public void performtesttextfield1(ActionEvent e) {
			//do something
		}
	}

	public static void main(String[] args) {
		MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class,new MySetOfComponents());
	}
}
