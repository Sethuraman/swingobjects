package org.aesthete.swingobjects.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 16/04/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToolbarButton extends JButton{

    public ToolbarButton(ImageIcon imageIcon) {
        super(imageIcon);
        makeIntoToolbarButton();
    }

    public ToolbarButton(String text){
        super(text);
        makeIntoToolbarButton();
    }

    private void makeIntoToolbarButton() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
                setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(false);
                setBorderPainted(false);
            }
        });
    }


}
