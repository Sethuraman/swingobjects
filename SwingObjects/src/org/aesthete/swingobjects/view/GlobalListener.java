package org.aesthete.swingobjects.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.lang3.StringUtils;

/**
 *  Take a look at the below code example:
	 *
	 * <pre>
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
			// called when the button btn2 is clicked
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
			//called when the button btn1 is clicked
		}

		@Action("PerformTestTf1")
		public void performtesttextfield1(ActionEvent e) {
			// called when you hit an enter on the text field textfield1
		}
	}

	public static void main(String[] args) {
		MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class,new MySetOfComponents());
	}
}
	 * </pre>
	 *
	 * In this case textfield1 and btn1 inside MySetOfComponents will get an instance of the GlobalListener and the
	 * btn2 inside MyFrame, will get another instance of the GlobalListener.
	 *
	 * The listener instance will be dormant unless it spots a method with <pre>@Action("<action>")</pre> annotation that matches
	 * the action command set for that component.
	 *
	 * A GlobalListener instance is created when you register or create a container using the methods of  {@link FrameFactory}.
 * @author sethu
 *
 */
public class GlobalListener implements ActionListener{

	private Object comp;
	private boolean isInited;
	private Map<String,Method> actions;

	public GlobalListener(Object comp) {
		this.comp=comp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action=e.getActionCommand();
		if(StringUtils.isNotEmpty(action)){
			init();
			Method m = actions.get(action);
			if(m!=null){
				try {
					m.invoke(comp, e);
				} catch (Exception exp) {
					throw new SwingObjectRunException(exp.getCause(),ErrorSeverity.SEVERE, FrameFactory.class);
				}
			}
		}

	}

	private void init() {
		if(!isInited){
			Method[] methods = comp.getClass().getMethods();
			for(Method method : methods){
				Action a=method.getAnnotation(Action.class);
				if(a!=null){
					if(actions==null){
						actions=new HashMap<String, Method>();
					}
					for(String s : a.value()){
						actions.put(s, method);
					}
				}
			}
			isInited=true;
		}
	}

}
