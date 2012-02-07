package org.aesthete.swingobjects.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.aesthete.swingobjects.SwingObjProps;

import com.jgoodies.forms.layout.FormLayout;

/**
 * Plain wait dialog, that has an indeterminate wait image on the top
 * followed by a text area in a scrollpane. The text area can be updated
 * from anywhere in the application by calling the below methods:
 * @see WaitDialog#appendWaitDialogMessage(String)
 * @see WaitDialog#setWaitDialogMessage(String)
 * @author sethu
 */
/**
 * @author GOKU
 *
 */
public class WaitDialog extends JDialog {

	private static final long serialVersionUID = 1L;
    private JLabel waitImageLabel;
    private JScrollPane scrollpane;
    private JTextArea waitDisplayArea;

    private static WaitDialog instance=new WaitDialog();

    public static WaitDialog getInstance(){
    	return instance;
    }

    private WaitDialog() {
   		super((JFrame)null,true);
        initComponents();
        layoutComponents();
        CommonUI.nameComponents(this.getClass(), this);
    }

    private void layoutComponents() {
		FormLayout layout=new FormLayout(
				"10dlu:grow,center:280dlu,10dlu:grow",
				"25dlu," +
				"5dlu," +
				"100dlu:grow," +
				"5dlu");
		SwingObjFormBuilder builder=new SwingObjFormBuilder(layout);
		builder.addComponent(waitImageLabel);
		builder.nextLinePlain();
		builder.addComponent(scrollpane);
		this.setContentPane(builder.getPanel());
		pack();
	}
    private void initComponents() {
    	scrollpane=new JScrollPane();
    	scrollpane.setBackground(this.getBackground());
    	waitDisplayArea=new JTextArea();
    	waitDisplayArea.setBackground(this.getBackground());
    	waitDisplayArea.setText(SwingObjProps.getProperty("waitdialog.title"));
    	scrollpane.setPreferredSize(new Dimension(175,175));
    	scrollpane.setViewportView(waitDisplayArea);

    	waitImageLabel = new JLabel();
    	waitImageLabel.setIcon(new ImageIcon(getClass().getResource(SwingObjProps.getProperty("waitdialog.waitimage"))));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(true);
    }

    /**
     * Call this method to show the wait dialog. This method is guaranteed
     * to execute in the EDT.
     */
    public static void displayWaitDialog(){
    	showHideWaitDialog(true);
    }

    /**
     * Call this method to hide the wait dialog. This method is guaranteed
     * to execute in the EDT.
     */
    public static void hideWaitDialog(){
    	showHideWaitDialog(false);
    }

	public static void showHideWaitDialog(final boolean isShow) {
		if(SwingUtilities.isEventDispatchThread()){
			if(isShow) {
				CommonUI.showOnScreen(WaitDialog.instance);
			}else {
				WaitDialog.instance.setVisible(false);
			}
    	}else{
    		SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(isShow) {
						CommonUI.showOnScreen(WaitDialog.instance);
					}else {
						WaitDialog.instance.setVisible(false);
					}
				}
			});
    	}
	}


    /**
     * Append text to the wait dialog. Use this method to provide visual
     * input to your users on the status of a particular long running task.
     * This method can be executed from any thread. Meaning you dont have to
     * be in the Event Despatch Thread (EDT) to call this method since a
     * {@link JTextArea#setText(String)} is thread-safe.
     * @param text text to update. HTML not supported. Use \n to break lines.
     */
    public static void appendWaitDialogMessage(String text){
    	instance.waitDisplayArea.setText(instance.waitDisplayArea.getText()+text);
    }

    /**
     * Replace the text in the wait dialog. Use this method to provide visual
     * input to your users on the status of a particular long running task.
     * This method can be executed from any thread. Meaning you dont have to
     * be in the Event Despatch Thread (EDT) to call this method since a
     * {@link JTextArea#setText(String)} is thread-safe.
     * @param text text to update. HTML not supported. Use \n to break lines.
     */
    public static void setWaitDialogMessage(String text){
    	instance.waitDisplayArea.setText(text);
    }
}
