package org.aesthete.swingobjects;

import org.aesthete.swingobjects.datamap.converters.ConverterUtils;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JComboBoxConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JTextComponentConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.JToggleButtonConverter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils.SwingObjTableConverter;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.WaitDialog;
import org.aesthete.swingobjects.view.WaitDialogImpl;
import org.aesthete.swingobjects.view.table.SwingObjTable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.Locale;
import java.util.MissingResourceException;

public class SwingObjectsInit {

    public static void init(String swingObjPropsBundleBaseName,String applicationPropsBundleBaseName,Locale locale) throws SwingObjectException {
        try {

            SwingObjProps.init(swingObjPropsBundleBaseName,applicationPropsBundleBaseName,locale);
            FormLayoutConfig.init();
            initConverters();
            initWaitDialog();
        }catch(SwingObjectException e){
            throw e;
        } catch (Exception e) {
            throw new SwingObjectException(e,ErrorSeverity.SEVERE, SwingObjectsInit.class);
        }
    }

    private static void initWaitDialog() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        CommonUI.runInEDT(new Runnable() {
            @Override
            public void run() {
                try {
                    try{
                        String waitDialogClassName = SwingObjProps.getSwingObjProperty("waitdialog.classname");
                        WaitDialog.setInstance((WaitDialog)Class.forName(waitDialogClassName).newInstance());
                    }catch (MissingResourceException e){
                        WaitDialog.setInstance(new WaitDialogImpl());
                    }
                } catch (Exception e) {
                    new SwingObjectException(e,ErrorSeverity.SEVERE, SwingObjectsInit.class);
                }
            }
        });
    }

    public static void init(String swingObjPropsBundleBaseName,String applicationPropsBundleBaseName) throws SwingObjectException {
        init(swingObjPropsBundleBaseName,applicationPropsBundleBaseName,Locale.getDefault());
    }

    private static void initConverters() {
        ConverterUtils.registerConverter(JTextComponent.class, new JTextComponentConverter());
        ConverterUtils.registerConverter(JComboBox.class, new JComboBoxConverter());
        ConverterUtils.registerConverter(JToggleButton.class, new JToggleButtonConverter());
        ConverterUtils.registerConverter(SwingObjTable.class, new SwingObjTableConverter());
    }



}
