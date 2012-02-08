package org.aesthete.swingobjects.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.util.HTMLUtils;
import org.apache.log4j.Logger;


public class SwingObjectException extends Exception implements SwingObjectsExceptions{

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private ErrorSeverity errorSeverity;
    private String message;
    private String[] placeHolderValues;

    public SwingObjectException(ErrorSeverity errorSeverity,Class<?> classFromWhereThrown){
		this("swingobj.severe",null,errorSeverity,classFromWhereThrown);
	}

	public SwingObjectException(Throwable e, ErrorSeverity errorSeverity,Class<?> classFromWhereThrown){
		this("swingobj.severe",e,errorSeverity,classFromWhereThrown);
	}

	public SwingObjectException(String errorCode,Throwable e, ErrorSeverity errorSeverity,Class<?> clz,String... placeholders){
		super(e);
		this.errorCode=errorCode;
		this.errorSeverity=errorSeverity;
		this.placeHolderValues=placeholders;
		Logger logger=Logger.getLogger(clz);
		logger.error(formatMessage(), e==null?null:(e.getCause()==null?e:e.getCause()));
	}

	public String getMessage(String errorCode,String[] placeholders) {
        return SwingObjProps.getErrorProperty(errorCode, placeHolderValues);
    }

	/* (non-Javadoc)
	 * @see org.aesthete.swingobjects.exceptions.SwingObjectsExceptions#formatMessage()
	 */
	@Override
	public String formatMessage(){
        StringBuilder builder=new StringBuilder();
        builder.append("\n");
        builder.append("Error Code:"+errorCode);
        builder.append("\n");
        builder.append("Error Description:"+SwingObjProps.getErrorProperty(errorCode,placeHolderValues));
        builder.append("\n");
        builder.append("Error Severity:"+errorSeverity.toString());
        builder.append("\n");
        builder.append("Message:"+message);
        builder.append("\n");
        return builder.toString();
    }

	/* (non-Javadoc)
	 * @see org.aesthete.swingobjects.exceptions.SwingObjectsExceptions#getErrorCode()
	 */
	@Override
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/* (non-Javadoc)
	 * @see org.aesthete.swingobjects.exceptions.SwingObjectsExceptions#getErrorSeverity()
	 */
	@Override
	public ErrorSeverity getErrorSeverity() {
		return errorSeverity;
	}

	public void setErrorSeverity(ErrorSeverity errorSeverity) {
		this.errorSeverity = errorSeverity;
	}

	/* (non-Javadoc)
	 * @see org.aesthete.swingobjects.exceptions.SwingObjectsExceptions#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailedMessage(boolean isBasic) {
		StringWriter writer=new StringWriter();
		if(getCause()!=null) {
			getCause().printStackTrace(new PrintWriter(writer,true));
		}else {
			printStackTrace(new PrintWriter(writer,true));
		}

		writer.flush();
		StringBuilder builder=new StringBuilder("<b>");
		builder.append(HTMLUtils.convertAllLineBreaksToHtml(SwingObjProps.getErrorProperty(getErrorCode(),placeHolderValues)));
		builder.append("</b>");
		if(isBasic) {
			return "<html><font family=\"times new roman\">"+builder.toString()+"</font></html>";
		}
		StringBuilder builder2=new StringBuilder("<html><b>Message</b> <br/> <br/>");
		builder2.append(builder)
				.append("<br/> <br/>")
				.append("<hr>")
				.append("<br/>")
				.append("<b>Stacktrace</b>")
				.append("<br/> <br/>")
				.append(HTMLUtils.convertAllLineBreaksToHtml(writer.getBuffer().toString()))
				.append("</html>");

		return builder2.toString();
	}
}
