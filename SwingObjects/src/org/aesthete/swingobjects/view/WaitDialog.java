package org.aesthete.swingobjects.view;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.aesthete.swingobjects.SwingObjProps;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

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
	private JXBusyLabel waitImageLabel;
	private JScrollPane scrollpane;
	private JTextArea waitDisplayArea;

	private static WaitDialog instance = new WaitDialog();
	private JXPanel panel;

	public static WaitDialog getInstance() {
		return instance;
	}

	private WaitDialog() {
		super((JFrame) null, true);
		initComponents();
		layoutComponents();
		CommonUI.nameComponents(this.getClass(), this);
	}

	private void layoutComponents() {
		SwingObjFormBuilder builder = new SwingObjFormBuilder("10dlu:grow,fill:100dlu:grow,pref,fill:100dlu:grow,10dlu:grow",panel);
		builder.setColumn(3);
		builder.addComponent(waitImageLabel);
		builder.nextLine("30dlu","fill:100dlu:grow");
		builder.addComponent(scrollpane,3);
		this.setContentPane(builder.getPanel());
		pack();
	}

	private void initComponents() {
		panel = new JXPanel();
		MattePainter mp = new MattePainter(Colors.LightBlue.alpha(0.5f));
		GlossPainter gp = new GlossPainter(Colors.White.alpha(0.3f), GlossPainter.GlossPosition.TOP);
		panel.setBackgroundPainter(new CompoundPainter<JXPanel>(mp, gp));


		scrollpane = new JScrollPane();
		waitDisplayArea = new JTextArea();
		waitDisplayArea.setOpaque(false);
		waitDisplayArea.setText(SwingObjProps.getProperty("waitdialog.title"));
		waitDisplayArea.setEditable(false);
		scrollpane.setViewportView(waitDisplayArea);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.setOpaque(false);
		scrollpane.setBorder(BorderFactory.createEmptyBorder());

		waitImageLabel = new JXBusyLabel();
		waitImageLabel.setText("Please Wait....");
		waitImageLabel.setBusy(true);
		waitImageLabel.setIconTextGap(20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		setUndecorated(true);
	}

	/**
	 * Call this method to show the wait dialog. This method is guaranteed to
	 * execute in the EDT.
	 */
	public static void displayWaitDialog() {
		showHideWaitDialog(true);
	}

	/**
	 * Call this method to hide the wait dialog. This method is guaranteed to
	 * execute in the EDT.
	 */
	public static void hideWaitDialog() {
		showHideWaitDialog(false);
	}

	public static void showHideWaitDialog(final boolean isShow) {
		if (SwingUtilities.isEventDispatchThread()) {
			if (isShow) {
				CommonUI.showOnScreen(WaitDialog.instance);
			} else {
				WaitDialog.instance.setVisible(false);
			}
		} else {
			SwingUtilities.invokeLater(new Runnable() {
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
	public static void appendWaitDialogMessage(String text) {
		instance.waitDisplayArea.setText(instance.waitDisplayArea.getText() + text);
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
	public static void setWaitDialogMessage(String text) {
		instance.waitDisplayArea.setText(text);
	}
}
