package org.aesthete.swingobjects.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.aesthete.swingobjects.SwingObjProps;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.error.ErrorReporter;

import com.jgoodies.forms.layout.FormLayout;

public class InfoGatherErrorReporterFrame implements ErrorReporter, ActionListener {

	private static final long serialVersionUID = 835506162586804557L;
	private ErrorInfo info;
	private JButton btnSendEmail;
	private JTextArea txtArea;
	private JScrollPane pnTxtArea;

	@Override
	public void reportError(ErrorInfo info) throws NullPointerException {
		this.info = info;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CommonUI.showOnScreen(InfoGatherErrorReporterFrame.this);
			}
		});
	}

	public InfoGatherErrorReporterFrame() {
		initComponents();
		layoutComponents();
	}
	
	protected void layoutComponents() {
		FormLayout layout = new FormLayout("10dlu:grow,fill:200dlu:grow,10dlu:grow", "");
		SwingObjFormBuilder builder = new SwingObjFormBuilder(layout);
		builder.addComponent(CommonComponentFactory.getBoldLabel("error.errorreport.enterdtls"));
		builder.nextLine("fill:100dlu");
		builder.addComponent(pnTxtArea);
		builder.addButtonBar(1, ButtonBarPos.Center, null, btnSendEmail);
		builder.complete();
		builder.complete();
		this.setContentPane(builder.getPanel());
	}

	private void initComponents() {
		this.setTitle(SwingObjProps.getProperty("errorreport.gatherinfo.title"));
		CommonUI.setIconImageForContainer(this);
		txtArea = new JTextArea();
		pnTxtArea = new JScrollPane(txtArea);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		btnSendEmail = new JButton("Send Email");
		btnSendEmail.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		EmailErrorReporter reporter = new EmailErrorReporter();
//		try {
//			reporter.reportError(info, txtArea.getText());
//			JOptionPane.showMessageDialog(this, Properties.getAppProps("errorreport.sent"));
//			this.dispose();
//		} catch (CsmartException e1) {
//			if (CommonPropertyConstants.ERROR_EMAIL_AUTHFAILED.equals(e1.getErrorCode())) {
//				JOptionPane.showMessageDialog(this, e1.getMessage());
//			} else {
//				JOptionPane.showMessageDialog(
//						this,
//						Properties.getAppProps("errorreport.notsent", Properties.getDefaultProperty("emailerrorreportto"),
//								FilenameUtils.separatorsToWindows(Properties.getUserDefaults("installationfolder"))));
//				this.dispose();
//			}
//		}
	}
}
