package org.aesthete.swingobjects.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.aesthete.swingobjects.util.HTMLUtils;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

public class CommonComponentFactory {

	public static JLabel getLabel(String value){
		return new JLabel(value);
	}
	public static JLabel getBoldLabelFromValue(String value){
		return new JLabel(getHtmlString(0, value,"black",true));
	}

	public static JLabel getH1WrappedLabelFromString(String value){
		return new JLabel(getHtmlString(1, value, "white", false));
	}
	public static JLabel getH2WrappedLabelFromString(String value){
		return new JLabel(getHtmlString(2, value, "white", false));
	}
	public static JLabel getH3WrappedLabelFromString(String value){
		return new JLabel(getHtmlString(3, value, "white", false));
	}
	public static JLabel getH4WrappedLabelFromString(String value){
		return new JLabel(getHtmlString(4, value, "white", false));
	}

	public static String getHtmlString(int heading,String value,String colour,boolean isBold){
		return HTMLUtils.getHtmlString(heading, value, colour, isBold);
	}

	public static JButton getButtonFromValue(String value){
		JButton button=new JButton(value);
		button.setPreferredSize(new Dimension(150,20));
		return button;
	}

	public static JButton getBiggerButtonFromString(String string){
		JButton button=new JButton(string);
		button.setPreferredSize(new Dimension(250,20));
		return button;
	}

	public static JButton getSmallerButtonValue(String value){
		JButton button=new JButton(value);
		button.setPreferredSize(new Dimension(80,20));
		return button;
	}

	public static JButton getIconButton(String imageFile) {
		JButton btn=new JButton(CommonUI.getScaledImage(19, 22,imageFile));
		return btn;
	}
	public static JXTaskPaneContainer createCollapsibleContainer(JXTaskPane... taskPanes) {
		JXTaskPaneContainer container=new JXTaskPaneContainer();
		for(JXTaskPane taskPane : taskPanes) {
			container.add(taskPane);
		}
		return container;
	}
}
