package org.aesthete.swingobjects.util;


import java.io.File;
import java.util.List;

import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.AuthenticationFailedException;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.util.EmailHelper.EmailDetailsDto.EmailAttachment;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;


public class EmailHelper {

	public static void sendMail(EmailDetailsDto emailDto) throws SwingObjectException{
		MultiPartEmail email=null;
		try {
			if(emailDto.isHtml()) {
				email=new HtmlEmail();
				((HtmlEmail)email).setHtmlMsg(emailDto.getBody());
			}else {
				email = new MultiPartEmail();
				email.setMsg(emailDto.getBody());
			}
			if (emailDto.getAttachments()!=null) {
				for(EmailAttachment attach : emailDto.getAttachments()){
					FileDataSource fd=new FileDataSource(attach.getFileloc());
					fd.setFileTypeMap(mimetypes);
					email.attach(fd, attach.getFilename(),attach.getFilename());
				}
			}
			setCommonAttributes(emailDto, email);
			email.send();
		}catch(SwingObjectException e) {
			throw e;
		}catch(EmailException e) {
			if(e.getCause() instanceof AuthenticationFailedException) {
				throw new SwingObjectException("swingobj.email.authfailed", e,ErrorSeverity.ERROR, EmailHelper.class);
			}
			throw new SwingObjectException("swingobj.emailnotsent",e, ErrorSeverity.SEVERE, EmailHelper.class);
		} catch (Exception e) {
			throw new SwingObjectException("swingobj.emailnotsent",e, ErrorSeverity.SEVERE, EmailHelper.class);
		}
	}

	private static void setCommonAttributes(EmailDetailsDto emailDto, MultiPartEmail email) throws EmailException, SwingObjectException {
		email.setHostName(SwingObjProps.getProperty("emailsmtp"));
		email.setAuthenticator(new DefaultAuthenticator(emailDto.getEmailID(),emailDto.getPassword()));
		email.setFrom(emailDto.getEmailID(), emailDto.getFromName());
		email.setDebug(true);
		email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
		try {
			if(emailDto.getTo().indexOf(",")!=-1){
				String[] splitTo=emailDto.getTo().split(",");
				for(String emailTo : splitTo){
					email.addTo(emailTo.trim());
				}
			}else{
				email.addTo(emailDto.getTo().trim());
			}

			if (emailDto.getCc()!=null) {
				if (emailDto.getCc().indexOf(",") != -1) {
					String[] splitCC = emailDto.getCc().split(",");
					for (String emailCC : splitCC) {
						email.addCc(emailCC.trim());
					}
				} else {
					email.addCc(emailDto.getCc().trim());
				}
			}
		}catch(EmailException e) {
			throw new SwingObjectException("swingobj.email.checkto",e,ErrorSeverity.ERROR, EmailHelper.class);
		}

		email.setSubject(emailDto.getSubj()==null?"":emailDto.getSubj());
	}


	private static MimetypesFileTypeMap mimetypes=getMimeTypesFile();

	private static MimetypesFileTypeMap getMimeTypesFile() {
		MimetypesFileTypeMap mimetypestemp=null;
		try {
			mimetypestemp=new MimetypesFileTypeMap(FileHelper.getFileResource("/mime.types"));
		} catch (Exception e) {
			new SwingObjectRunException(ErrorSeverity.SEVERE, EmailHelper.class);
		}
		return mimetypestemp;
	}

	public static class EmailDetailsDto {
		private String to;
		private String cc;
		private String subj;
		private String emailID;
		private String password;

		private List<EmailAttachment> attachments;
		private String body;
		private String fromName;
		private boolean isHtml;

		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getCc() {
			return cc;
		}
		public void setCc(String cc) {
			this.cc = cc;
		}
		public String getSubj() {
			return subj;
		}
		public void setSubj(String subj) {
			this.subj = subj;
		}
		public List<EmailAttachment> getAttachments() {
			return attachments;
		}
		public void setAttachments(List<EmailAttachment> attachments) {
			this.attachments = attachments;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getFromName() {
			return fromName;
		}
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		public boolean isHtml() {
			return isHtml;
		}
		public void setHtml(boolean isHtml) {
			this.isHtml = isHtml;
		}

		public static class EmailAttachment{
			private String filename;
			private File fileloc;
			public String getFilename() {
				return filename;
			}
			public void setFilename(String filename) {
				this.filename = filename;
			}
			public File getFileloc() {
				return fileloc;
			}
			public void setFileloc(File fileloc) {
				this.fileloc = fileloc;
			}
		}

		public String getEmailID() {
			return emailID;
		}
		public void setEmailID(String emailID) {
			this.emailID = emailID;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}



}
