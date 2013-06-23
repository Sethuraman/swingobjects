package test;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 06/08/12
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class WidgetShowcase extends JFrame{
    public static void main(String[] args) {
        try {
            SwingObjectsInit.init("swingobjects", "application");
            WidgetShowcase test = FrameFactory.getNewContainer("test", WidgetShowcase.class);
            test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            CommonUI.showOnScreen(test);
        } catch (SwingObjectException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  WidgetShowcase(){
        SwingObjFormBuilder builder=new SwingObjFormBuilder("10dlu,fill:200dlu:grow,10dlu", new RoundedPanel());
        BlinkerLabel blinkerLabel=new BlinkerLabel(null,"This is a blinker label","/images/warning.png");
        DateTextField dateTextField=new DateTextField(new Date());
        builder.addComponent(dateTextField);
        builder.nextLine("fill:30dlu");
        builder.addComponent(blinkerLabel);
        builder.nextLine();
        LabelButton labelButton=new LabelButton(null,"This is a Label Button","/images/info.png");
        builder.addComponent(labelButton);
        builder.nextLine();
        TimePicker timePicker=new TimePicker(new Date());
        builder.addComponent(timePicker);
        builder.nextLine();


        SwingObjTabbedPane swingObjTabbedPane = new SwingObjTabbedPane();
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.black);
        swingObjTabbedPane.addTab("Panel 1", panel1);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.DARK_GRAY);
        swingObjTabbedPane.addTab("Panel 2", panel2);

        builder.addComponent(swingObjTabbedPane);
        builder.complete();
        this.setContentPane(builder.getPanel());
    }
}
