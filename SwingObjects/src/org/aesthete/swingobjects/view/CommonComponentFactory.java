package org.aesthete.swingobjects.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import com.aesthete.csmart.fw.common.CommonConstants;
import com.aesthete.csmart.fw.common.config.Properties;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.Sizes;

public class CommonComponentFactory {

	public static JLabel getLabel(String value){
		return new JLabel(value);
	}
	public static JLabel getBoldLabelFromValue(String value){
		return new JLabel(getHtmlString(0, value,"black",true));
	}

	public static JLabel getBoldLabel(String appropkey){
		return new JLabel(getHtmlString(0, Properties.getAppProps(appropkey),"black",true));
	}

	public static JLabel getVanillaWhiteLabelFromKey(String appropkey){
		return new JLabel(getHtmlString(0, Properties.getAppProps(appropkey),"white", false));
	}

	public static JLabel getVanillaLabelFromKey(String appropkey){
		return new JLabel(Properties.getAppProps(appropkey));
	}

	public static String getErrorTextString(String appropkey){
		return getHtmlString(0, Properties.getAppProps(appropkey), "red", false);
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
		return UIUtils.getHtmlString(heading, value, colour, isBold);
	}

	public static JLabel getH1WrappedLabelFromKey(String appropkey){
		return getH1WrappedLabelFromString(Properties.getAppProps(appropkey));
	}
	public static JLabel getH2WrappedLabelFromKey(String appropkey){
		return getH2WrappedLabelFromString(Properties.getAppProps(appropkey));
	}
	public static JLabel getH3WrappedLabelFromKey(String appropkey){
		return getH3WrappedLabelFromString(Properties.getAppProps(appropkey));
	}
	public static JLabel getH4WrappedLabelFromKey(String appropkey){
		return getH4WrappedLabelFromString(Properties.getAppProps(appropkey));
	}

	public static JButton getButton(String appropkey){
		JButton button=new JButton(Properties.getAppProps(appropkey));
		button.setPreferredSize(new Dimension(150,20));
		return button;
	}

	public static JButton getButtonFromValue(String value){
		JButton button=new JButton(value);
		button.setPreferredSize(new Dimension(150,20));
		return button;
	}

	public static JButton getBiggerButton(String appropkey){
		JButton button=new JButton(Properties.getAppProps(appropkey));
		button.setPreferredSize(new Dimension(250,20));
		return button;
	}

	public static JButton getBiggerButtonFromString(String string){
		JButton button=new JButton(string);
		button.setPreferredSize(new Dimension(250,20));
		return button;
	}

	public static JButton getSmallerButton(String appropkey){
		JButton button=new JButton(Properties.getAppProps(appropkey));
		button.setPreferredSize(new Dimension(80,20));
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

	public static JPanel buildCenteredButtonBar(Color bgColor,JButton... buttons){
    	JPanel buttonbar=ButtonBarFactory.buildCenteredBar(buttons);
    	if(bgColor!=null)
    		buttonbar.setBackground(bgColor);
    	return buttonbar;
    }
    public static JPanel buildAlignedButtonBar(JButton[] buttons,boolean isAlignRight,Color bgColor){
    	JPanel buttonbar=null;
    	if(isAlignRight){
    		buttonbar=ButtonBarFactory.buildRightAlignedBar(buttons);
    	}else{
    		buttonbar=ButtonBarFactory.buildLeftAlignedBar(buttons);
    	}
    	if(bgColor!=null)
    		   	buttonbar.setBackground(bgColor);
    	return buttonbar;
    }

    public static JSplitPane setScrollPaneSizesLeftAndDoc(JPanel leftSide, JPanel rightSide){
    	JScrollPane leftSideScrlPane=new JScrollPane(leftSide);
    	JScrollPane docTableScrlPane=new JScrollPane(rightSide);

		leftSideScrlPane.setPreferredSize(new Dimension(CommonUI.getFractionedWidth(58),
														CommonUI.getFractionedHeight(65)));
		leftSideScrlPane.setMinimumSize(new Dimension(CommonUI.getFractionedWidth(30),CommonUI.getFractionedHeight(64)));


		docTableScrlPane.setPreferredSize(new Dimension(CommonUI.getFractionedWidth(32),
														CommonUI.getFractionedHeight(65)));
		docTableScrlPane.setMinimumSize(new Dimension(CommonUI.getFractionedWidth(20),CommonUI.getFractionedHeight(65)));

		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftSideScrlPane,docTableScrlPane);
		return splitPane;
    }

    public static void setScrollPaneSizeOnlyOne(JScrollPane mainScrlPn){
    	mainScrlPn.setPreferredSize(new Dimension(CommonUI.getFractionedWidth(99),CommonUI.getFractionedHeight(65)));
    }

	public static int showConfirmDialog(Component parent,String appropkey){
		return JOptionPane.showConfirmDialog(parent,
				Properties.getAppProps(appropkey),"Warning",JOptionPane.YES_NO_OPTION);
	}
	public static void setScrollPaneSizeForFormMinutes(JScrollPane scrlPnFormPanel,Component component) {
		scrlPnFormPanel.setPreferredSize(new Dimension(Sizes.dialogUnitXAsPixel(CommonConstants.FORM_MINUTES_PANEL_WIDTH, component),
													Sizes.dialogUnitYAsPixel(CommonConstants.FORM_MINUTES_PANEL_HEIGHT, component)));
	}

	public static JXTaskPaneContainer createCollapsibleContainer(JXTaskPane... taskPanes) {
		JXTaskPaneContainer container=new JXTaskPaneContainer();
		for(JXTaskPane taskPane : taskPanes) {
			container.add(taskPane);
		}
		return container;
	}
}
