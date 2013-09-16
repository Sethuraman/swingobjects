package org.aesthete.swingobjects.view;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 09/09/13
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class HintTextField extends JTextField {

    public HintTextField(String hint) {
        setUI(new HintTextFieldUI(hint, false));
    }
}
