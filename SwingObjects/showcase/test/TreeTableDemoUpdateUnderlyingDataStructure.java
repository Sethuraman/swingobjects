package test;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.model.tree.GenericTreeNode;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.table.PropertyChangeSupporter;
import org.aesthete.swingobjects.view.table.SwingObjTreeTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 24/04/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTableDemoUpdateUnderlyingDataStructure {

    public static void main(String[] args) throws SwingObjectException {
        CommonUI.runInEDT(new Runnable() {
            @Override
            public void run() {
                try {
                    SwingObjectsInit.init("swingobjects", "application", new Locale("fr", "FR"));

                    final GenericTreeNode<Row> root = new GenericTreeNode<Row>(new Row(0, "-", "-"));
                    root.setEmptyRootNode(true);

                    final GenericTreeNode<Row> child = new GenericTreeNode<Row>(new Row(1, "Garfield1", "Kitty"));
                    root.addChild(child);
                    child.addChild(new GenericTreeNode<Row>(new Row(2, "Garfield2", "Kitty")));
                    child.addChild(new GenericTreeNode<Row>(new Row(3, "Garfield3", "Kitty")));
                    root.addChild(new GenericTreeNode<Row>(new Row(4, "Garfield4", "Kitty")));

                    final SwingObjTreeTable<Row> treeTable = new SwingObjTreeTable<Row>(Row.class, root);

                    final JFrame frame = new JFrame();
                    JPanel panel=new JPanel(new BorderLayout());
                    frame.setContentPane(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setPreferredSize(new Dimension(400, 400));


                    panel.add(new JScrollPane(treeTable));

                    JButton comp = new JButton("Add Node To Root");

                    comp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            child.addChild(new GenericTreeNode<Row>(new Row(5, "Employee Name","Department")));
                        }
                    });

                    panel.add(comp, BorderLayout.SOUTH);

                    frame.pack();
                    frame.setVisible(true);
                } catch (SwingObjectException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static class Row extends PropertyChangeSupporter{
        @Column(index = 0, name = "Employee ID", editable = true)
        private int employeeID;
        @Column(index = 1, name = "Employee Name", editable = true)
        private String employeeName;
        @Column(index = 2, name = "Department", editable = true)
        private String department;


        public Row(int employeeID, String employeeName, String department) {
            this.employeeID = employeeID;
            this.employeeName = employeeName;
            this.department = department;
        }

        public int getEmployeeID() {
            return employeeID;
        }

        public void setEmployeeID(int employeeID) {
            this.employeeID = employeeID;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
