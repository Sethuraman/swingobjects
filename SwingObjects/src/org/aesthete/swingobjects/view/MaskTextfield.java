package org.aesthete.swingobjects.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class MaskTextfield extends JFormattedTextField{

    public MaskTextfield(String mask) throws ParseException {
        super(new MaskFormatter(mask){
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


