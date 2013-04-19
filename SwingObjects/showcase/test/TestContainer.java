package test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;

import test.Test.MyFrame;
import test.Test.MyPanel;

public class TestContainer {


	public static class MyFrame extends JFrame{

		//a field that implements the Components interface and has other JComponents in it
		private JPanel components;
		private JLabel lbl2;
		private JButton btn2;

		public MyFrame(JPanel components) {
			this.components=components;
			btn2=new JButton("Button 2");
            this.setLayout(new FlowLayout());
            this.add(components);
            this.add(btn2);
		}

		@Action("Button 2")
		public void performTestBtn2() {
            System.out.println("test btn 2 invoked");
		}

        @Action(value = "Button 1",  overrideWeight = 1)
        public void performTestBtn1() {
            System.out.println("perform test button 1 override weight of 1");
        }
	}

	public static class MySetOfComponents extends JPanel implements Components{
		private JLabel label1;
		private JTextField textfield1;
		private JButton btn1;

		public MySetOfComponents() {
			btn1=new JButton("Button 1");

			textfield1=new JTextField();
			textfield1.setActionCommand("PerformTestTf1");
            this.setLayout(new FlowLayout());
            this.add(textfield1);
            this.add(btn1);

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

		@Action("Button 1")
		public void performtestbtn1(ActionEvent e) {
            System.out.println("perform test button 1 override weight of 0");
		}

		@Action("PerformTestTf1")
		public void performtesttextfield1(ActionEvent e) {
			//do something
		}
	}

	public static void main(String[] args) throws SwingObjectException {
//        MyFrame frame=new MyFrame(new MySetOfComponents());
//        CommonUI.showOnScreen(frame);

//
        SwingObjectsInit.init("swingobjects", "application");
		MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class,new MySetOfComponents());
        CommonUI.showOnScreen(frame);

	}
}
