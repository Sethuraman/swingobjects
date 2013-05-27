package org.aesthete.swingobjects.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.util.HTMLUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SwingObjectRunException extends RuntimeException implements SwingObjectsExceptions {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private ErrorSeverity errorSeverity;
    private String message;
    private String[] placeHolderValues;


    public SwingObjectRunException(ErrorSeverity errorSeverity,Object objFromWhereExpIsThrown){
		this("swingobj.severe",null,errorSeverity,objFromWhereExpIsThrown.getClass());
	}

    public SwingObjectRunException(ErrorSeverity errorSeverity,String errorMsg,Object objFromWhereExpIsThrown){
		this("swingobj.severe",null,errorSeverity,objFromWhereExpIsThrown.getClass());
		this.message=errorMsg;
	}

	public SwingObjectRunException(Throwable e, ErrorSeverity errorSeverity,Object objFromWhereExpIsThrown){
		this("swingobj.severe",e,errorSeverity,objFromWhereExpIsThrown.getClass());
	}

	public SwingObjectRunException(Throwable e,Class<?> clz){
		this("swingobj.severe",e,ErrorSeverity.SEVERE,clz);
	}

	public SwingObjectRunException(Throwable e, ErrorSeverity errorSeverity,Class<?> clz){
		this("swingobj.severe",e,errorSeverity,clz);
	}

	public SwingObjectRunException(String errorCode,Throwable e, ErrorSeverity errorSeverity,Class<?> clz,String... placeholders){
		super(e);
		this.errorCode=errorCode;
		this.errorSeverity=errorSeverity;
		this.placeHolderValues=placeholders;
		Logger logger=Logger.getLogger(clz);
		logger.error(formatMessage(), e);
	}

    public SwingObjectRunException(String errorCode, Class<?> clz) {
        this(errorCode, null, ErrorSeverity.ERROR, clz);
    }

    public String getMessage(String errorCode,String[] placeholders) {
        return SwingObjProps.getApplicationProperty(errorCode, placeHolderValues);
    }

	public String formatMessage(){
        StringBuilder builder=new StringBuilder();
        builder.append("\n");
        builder.append("Error Code:"+errorCode);
        builder.append("\n");
        builder.append("Error Description:"+SwingObjProps.getApplicationProperty(errorCode,placeHolderValues));
        builder.append("\n");
        builder.append("Error Severity:"+errorSeverity.toString());
        builder.append("\n");
        builder.append("Message:"+StringUtils.defaultString(message));
        builder.append("\n");
        return builder.toString();
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorSeverity getErrorSeverity() {
		return errorSeverity;
	}

	public void setErrorSeverity(ErrorSeverity errorSeverity) {
		this.errorSeverity = errorSeverity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getPlaceHolderValues() {
		return placeHolderValues;
	}

	public void setPlaceHolderValues(String[] placeHolderValues) {
		this.placeHolderValues = placeHolderValues;
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
		builder.append(HTMLUtils.convertAllLineBreaksToHtml(SwingObjProps.getApplicationProperty(getErrorCode(),getPlaceHolderValues())));
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
