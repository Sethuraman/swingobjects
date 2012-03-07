package org.aesthete.swingobjects.view;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import org.aesthete.swingobjects.SwingObjProps;

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
public abstract class WaitDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private static WaitDialog instance;

	public static WaitDialog getInstance() {
		return instance;
	}

	public static void setInstance(WaitDialog dialog) {
		instance=dialog;
	}

	/**
	 * Call this method to show the wait dialog. This method is guaranteed to
	 * execute in the EDT.
	 */
	public static  void displayWaitDialog() {
		showHideWaitDialog(true);
	}

	/**
	 * Call this method to hide the wait dialog. This method is guaranteed to
	 * execute in the EDT.
	 */
	public static void hideWaitDialog() {
		instance.getWaitDisplayArea().setText(SwingObjProps.getApplicationProperty("waitdialog.title"));
		showHideWaitDialog(false);
	}

	public static  void showHideWaitDialog(final boolean isShow) {
		CommonUI.runInEDT(new Runnable() {
			@Override
			public void run() {
				if (isShow) {
					CommonUI.showOnScreen(WaitDialog.instance);
				} else {
					WaitDialog.instance.setVisible(false);
				}
			}
		});
	}

	/**
	 * Append text to the wait dialog. Use this method to provide visual input
	 * to your users on the status of a particular long running task. This
	 * method can be executed from any thread. Meaning you dont have to be in
	 * the Event Despatch Thread (EDT) to call this method since a
	 * {@link JTextArea#setText(String)} is thread-safe.
	 *
	 * @param text
	 *            text to update. HTML not supported. Use \n to break lines.
	 */
	public static  void appendWaitDialogMessage(String text) {
		instance.getWaitDisplayArea().setText(instance.getWaitDisplayArea().getText() + text);
		CommonUI.runInEDT(new Runnable() {
			@Override
			public void run() {
				instance.getWaitDisplayArea().scrollRectToVisible(new Rectangle(0,instance.getWaitDisplayArea().getHeight()-2,1,1));
			}
		});
	}

	/**
	 * Replace the text in the wait dialog. Use this method to provide visual
	 * input to your users on the status of a particular long running task. This
	 * method can be executed from any thread. Meaning you dont have to be in
	 * the Event Despatch Thread (EDT) to call this method since a
	 * {@link JTextArea#setText(String)} is thread-safe.
	 *
	 * @param text
	 *            text to update. HTML not supported. Use \n to break lines.
	 */
	public static  void setWaitDialogMessage(String text) {
		instance.getWaitDisplayArea().setText(text);
	}

	public abstract  JTextArea getWaitDisplayArea();
}
