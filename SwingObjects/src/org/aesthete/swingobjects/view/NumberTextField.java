package org.aesthete.swingobjects.view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberTextField extends JFormattedTextField{

    public NumberTextField(NumberFormat format) {
        super(new NumberFormatter(format){
            @Override
            public Object stringToValue(String text) throws ParseException {
                if(text.trim().isEmpty()){
                    return null;
                }
                return super.stringToValue(text);
            }
        });
    }
}
