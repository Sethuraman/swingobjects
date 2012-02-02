package org.aesthete.swingobjects.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {

	public enum Type{INT,STRING,DOUBLE, LONG};

	private int limit = -1;
	private Type type;

	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public JTextFieldLimit(Type type) {
		super();
		this.type = type;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null || str.isEmpty())
			return;

		if (limit == -1 || (getLength() + str.length()) <= limit) {
			if (type != null) {
				String oldString = getText(0, getLength());
				String newString = oldString.substring(0, offset) + str + oldString.substring(offset);
				switch(type) {
				case INT:
					try {
						Integer.parseInt(newString);
						super.insertString(offset, str, attr);
					} catch (NumberFormatException e) {
					}
					break;
				case DOUBLE:
					try {
						Double.parseDouble(newString);
						super.insertString(offset, str, attr);
					} catch (NumberFormatException e) {
					}
					break;
				case LONG:
					try {
						Long.parseLong(newString);
						super.insertString(offset, str, attr);
					} catch (NumberFormatException e) {
					}
					break;
				default:
					break;
				}
			} else {
				super.insertString(offset, str, attr);
			}

		}
	}
}
