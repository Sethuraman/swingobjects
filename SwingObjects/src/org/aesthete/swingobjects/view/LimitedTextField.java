package org.aesthete.swingobjects.view;

import javax.swing.JTextField;

import org.aesthete.swingobjects.view.JTextFieldLimit.Type;

public class LimitedTextField extends JTextField{

	public LimitedTextField(int limit) {
		this.setDocument(new JTextFieldLimit(limit));
	}

	public LimitedTextField(Type type) {
		this.setDocument(new JTextFieldLimit(type));
	}

}
