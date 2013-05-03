package org.aesthete.swingobjects.view.table;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 24/04/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComboBoxEditor extends DefaultCellEditor{

    public ComboBoxEditor(ComboBoxModel model) {
        super(new JComboBox(model));
    }
}
