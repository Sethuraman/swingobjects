package org.aesthete.swingobjects.view.table;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 24/04/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComboBoxEditor extends DefaultCellEditor{

    private DefaultComboBoxModel model;

    public ComboBoxEditor(DefaultComboBoxModel model, boolean editableComboBox) {
        super(new JComboBox(model));
        JComboBox comboBox = (JComboBox) getComponent();
        comboBox.setEditable(editableComboBox);
        this.model = model;
    }

    public void addValueToModel(Object value){
        model.addElement(value);
    }
}
