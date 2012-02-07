package org.aesthete.swingobjects.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.aesthete.swingobjects.ActionProcessor;
import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectsExceptions;
import org.aesthete.swingobjects.util.EmailHelper.EmailDetailsDto;
import org.aesthete.swingobjects.util.EmailHelper;
import org.aesthete.swingobjects.util.HTMLUtils;
import org.aesthete.swingobjects.view.SwingObjFormBuilder.ButtonBarPos;
import org.aesthete.swingobjects.workers.CommonSwingWorker;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.error.ErrorReporter;

import com.jgoodies.forms.layout.FormLayout;

public class InfoGatherErrorReporterFrame extends JFrame implements ErrorReporter, ActionListener {

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
		builder.addComponent(CommonComponentFactory.getBoldLabelFromValue("Please briefly describe what you were doing when this error occured"));
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
		CommonSwingWorker worker =new CommonSwingWorker() {
			@Override
			public void callModel() throws SwingObjectException {
				EmailDetailsDto dto=new EmailDetailsDto();
				dto.setBody(SwingObjProps.getProperty("swingobj.infogather.emailbody"
								,HTMLUtils.convertAllLineBreaksToHtml(txtArea.getText()),
								((SwingObjectsExceptions)info.getErrorException()).getDetailedMessage(false).replaceAll("<html>|</html>","")));
				dto.setEmailID(SwingObjProps.getProperty("sendemailto"));
				dto.setPassword(SwingObjProps.getProperty("emailpassword"));
				dto.setTo(SwingObjProps.getProperty("sendemailto"));
				dto.setFromName("Error In Application");
				dto.setSubj("Error in Application");
				EmailHelper.sendMail(dto);
			}

			@Override
			public void callConnector() {
				JOptionPane.showMessageDialog(InfoGatherErrorReporterFrame.this, SwingObjProps.getProperty("swingobj.errorreport.sent"));
				InfoGatherErrorReporterFrame.this.dispose();
			}
		};
		ActionProcessor.processAction(this, worker);
	}
}
