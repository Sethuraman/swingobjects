package test;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.view.FrameFactory;


public class Test {
	public static class MyFrame extends JFrame{
		private JPanel panel;
		public MyFrame() {
			//create a new panel
			panel=FrameFactory.getNewContainer("TestSetOfContainers", MyPanel.class, "Test");
			panel.setName("testpanel");
			setContentPane(panel);
		}
	}
	
	public static class MyPanel extends JPanel{
		private JButton btnTest;
		private JButton btnTest2;
		public MyPanel(String data) {
			btnTest=new JButton("Test");
			btnTest2=new JButton("Test2");
			btnTest2.setActionCommand("dosomething");
		}
		
		/*
		 * This method is automatically invoked when btnTest is clicked. The String inside action is 
		 * the label/text set in the buttonAction based on Label
		 */
		@Action("Test")
		public void dotestwork(ActionEvent e) {
			//handle Test
		}
		
		/*
		 * This method is invoked when btnTest2 is clicked. The String inside action is 
		 * the action command set in the buttonAction based on Label
		 */
		@Action("dosomething")
		public void handleSomething(ActionEvent e) {
			// handle do something
		}
		
	}
	public static void main(String[] args) {
		//create a new frame
		MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class);
		frame.setName("frame");
		
		
		//get hold of the panel in it
		MyPanel panel=FrameFactory.getContainer("TestSetOfContainers", MyPanel.class);
		
		// or get hold with the name
		panel=FrameFactory.getContainer("TestSetOfContainers", "testpanel");
		

		// do other work...
		
		
		//when you are finished dispose all the containers as below in one shot :
		FrameFactory.dispose("TestSetOfContainers");
		
		//=========>OR dispose individually<==========
		
		FrameFactory.dispose("TestSetOfContainers", MyPanel.class);
		FrameFactory.dispose("TestSetOfContainers", MyFrame.class);
		
		
		//=========>OR dispose individually with name<==========
		FrameFactory.dispose("TestSetOfContainers", "testpanel");
		FrameFactory.dispose("TestSetOfContainers", "frame");
		
	}
}
