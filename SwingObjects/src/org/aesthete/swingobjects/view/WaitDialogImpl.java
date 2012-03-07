package org.aesthete.swingobjects.view;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.aesthete.swingobjects.SwingObjProps;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

public class WaitDialogImpl extends WaitDialog {

	private JXBusyLabel waitImageLabel;
	private JScrollPane scrollpane;
	private JTextArea waitDisplayArea;
	private JXPanel panel;

	public WaitDialogImpl() {
		initComponents();
		layoutComponents();
		CommonUI.nameComponents(this.getClass(), this);
	}

	public void layoutComponents() {
		SwingObjFormBuilder builder = new SwingObjFormBuilder("10dlu:grow,fill:100dlu:grow,pref,fill:100dlu:grow,10dlu:grow",getPanel());
		builder.nextLine();
		builder.setColumn(3);
		builder.addComponent(getWaitImageLabel());
		builder.nextLine("30dlu","fill:100dlu:grow");
		builder.addComponent(getScrollpane(),3);
		this.setContentPane(builder.getPanel());
		pack();
	}

	public void initComponents() {
		setPanel(new JXPanel());
		paintTheBackground();


		setScrollpane(new JScrollPane());
		setWaitDisplayArea(new JTextArea());
		getWaitDisplayArea().setOpaque(false);
		getWaitDisplayArea().setText(SwingObjProps.getApplicationProperty("waitdialog.title"));
		getWaitDisplayArea().setEditable(false);
		getWaitDisplayArea().setWrapStyleWord(true);
		getWaitDisplayArea().setLineWrap(true);
		getScrollpane().setViewportView(getWaitDisplayArea());
		getScrollpane().getViewport().setOpaque(false);
		getScrollpane().setOpaque(false);
		getScrollpane().setBorder(BorderFactory.createEmptyBorder());

		setWaitImageLabel(new JXBusyLabel());
		getWaitImageLabel().setText("Please Wait....");
		getWaitImageLabel().setBusy(true);
		getWaitImageLabel().setIconTextGap(20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		setUndecorated(true);
	}

	public void paintTheBackground() {
		MattePainter mp = new MattePainter(Colors.LightBlue.alpha(0.5f));
		GlossPainter gp = new GlossPainter(Colors.White.alpha(0.3f), GlossPainter.GlossPosition.TOP);
		getPanel().setBackgroundPainter(new CompoundPainter<JXPanel>(mp, gp));
	}

	public JXBusyLabel getWaitImageLabel() {
		return waitImageLabel;
	}

	public void setWaitImageLabel(JXBusyLabel waitImageLabel) {
		this.waitImageLabel = waitImageLabel;
	}

	public JScrollPane getScrollpane() {
		return scrollpane;
	}

	public void setScrollpane(JScrollPane scrollpane) {
		this.scrollpane = scrollpane;
	}

	public JTextArea getWaitDisplayArea() {
		return waitDisplayArea;
	}

	public void setWaitDisplayArea(JTextArea waitDisplayArea) {
		this.waitDisplayArea = waitDisplayArea;
	}

	public JXPanel getPanel() {
		return panel;
	}

	public void setPanel(JXPanel panel) {
		this.panel = panel;
	}
}
