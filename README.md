2 line description
This is a framework that allows you to build a desktop application using the MVC pattern. It packs a lot of tools and utilities and UI widgets that you can use out of the box. All you will need to do is concentrate on GUI, Model and the swing objects library will string them all up for you nicely.

This is a work in progress project. I will keep coming back and updating this page as and when I build new stuff. I have built and am using this framework for a couple of years now. The framework is tightly coupled into my business project and hence I need to spend sometime in separating and extracting a generic framework that others can use.

Will keep posting updates as and when I get time.

Please leave a message if you are interested.

Introduction
Swing Objects is driven out of the lack of a basic framework for swing desktop applications. When I started learning the nuances of swing I found it initially daunting. There were no frameworks to make your life easy. To top it the layout managers in the jre are very difficult to work with! Also, the whole thing about the event dispatch thread is another. All of these things become clear as and when you start working with swing and develop on it. So as my project grew, so did the framework that I was working on. Now I think its mature enough for others to benefit from it. Also, if there are improvements made to it then we can all take it to the next level.

Ok.. so what is Swing Objects?

Take a look the below code blocks:

Automatic mapping of ActionListeners. No messy if else ladders or anonymous inner classes of the ActionListener interface

    public class TestContainer {
        public static class MyFrame extends JFrame{
        
                //a field that implements the Components interface and has other JComponents in it
                private MySetOfComponents components;
                private JLabel lbl2;
                private JButton btn2;
        
                public MyFrame(MySetOfComponents components) {
                        this.components=components;
                        btn2=new JButton();
                        btn2.setActionCommand("PerformTestBtn2");
                }
        
                @Action("PerformTestBtn2")
                public void performTestBtn2() {
                        // called when the button btn2 is clicked
                }
        }

        public static class MySetOfComponents implements Components{
                private JLabel label1;
                private JTextField textfield1;
                private JButton btn1;

                public MySetOfComponents() {
                        btn1=new JButton("Test");
                        btn1.setActionCommand("PerformTestBtn1");

                        textfield1=new JTextField();
                        textfield1.setActionCommand("PerformTestTf1");
                }

                public JLabel getLabel1() {
                        return label1;
                }
                public void setLabel1(JLabel label1) {
                        this.label1 = label1;
                }
                public JTextField getTextfield1() {
                        return textfield1;
                }
                public void setTextfield1(JTextField textfield1) {
                        this.textfield1 = textfield1;
                }
                public JButton getBtn1() {
                        return btn1;
                }
                public void setBtn1(JButton btn1) {
                        this.btn1 = btn1;
                }

                @Action("PerformTestBtn1")
                public void performtestbtn1(ActionEvent e) {
                        //called when the button btn1 is clicked
                }

                @Action("PerformTestTf1")
                public void performtesttextfield1(ActionEvent e) {
                        // called when you hit an enter on the text field textfield1
                }
        }

        public static void main(String[] args) {
                MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class,new MySetOfComponents());
        }
}
A simple Table Model that will make everyone's life easy. Declare your columns with the @Column annotation as below:

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Locale;

    import javax.swing.JFrame;
    import javax.swing.JScrollPane;

    import org.aesthete.swingobjects.SwingObjectsInit;
    import org.aesthete.swingobjects.annotations.Column;
    import org.aesthete.swingobjects.view.table.RowDataBean;
    import org.aesthete.swingobjects.view.table.SwingObjTable;
    import org.jdesktop.swingx.JXFrame;
    
    public class TableDemo {

        public static void main(String[] args) {

                try {

                        //For this demo the Framework need not be initialised.. If you plan on using the entire framework, then
                        //its best you initialise it before working on anything...
                        SwingObjectsInit.init("swingobjects", "application",new Locale("fr", "FR"));

                        //Here's the data to show on the table
                        final List<Row> rows = new ArrayList<Row>();
                        rows.add(new Row("Data 1", "Data 2", "Yes", true));
                        rows.add(new Row("Data 3", "Data 4", "No", false));


                        //Create the swing table as below.. Provide the Row.class to say that the data in the rows
                        // will be from this class
                        final SwingObjTable<Row> table = new SwingObjTable<Row>(Row.class);
                        table.setData(rows);
                        table.setVisibleRowCount(4);

                        //Make any column into a combo box by calling the below method.
                        //A column can be automatically made into a checkbox, by defining your property in the Row class as a boolean
                        table.makeColumnsIntoComboBox(new String[] { "Yes", "No" }, 2);

                        //Initialise the frame and show it on the screen
                        final JXFrame frame = new JXFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setContentPane(new JScrollPane(table));
                        frame.pack();
                        frame.setVisible(true);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static class Row extends RowDataBean{

                @Column(index=0,name="Column1",editable=true)
                private String column1;

                @Column(index=1,name="Column2",editable=true)
                private String column2;

                @Column(index=2,name="Column3",editable=true)
                private String column3;

                @Column(index=3,name="Column4",editable=true)
                private boolean column4;

                public Row(String column1, String column2, String column3, boolean column4) {
                        super();
                        this.column1 = column1;
                        this.column2 = column2;
                        this.column3 = column3;
                        this.column4 = column4;
                }
                public String getColumn1() {
                        return column1;
                }
                public void setColumn1(String column1) {
                        this.column1 = column1;
                }
                public String getColumn2() {
                        return column2;
                }
                public void setColumn2(String column2) {
                        this.column2 = column2;
                }
                public String getColumn3() {
                        return column3;
                }
                public void setColumn3(String column3) {
                        this.column3 = column3;
                }
                public boolean getColumn4() {
                        return column4;
                }
                public void setColumn4(boolean column4) {
                        this.column4 = column4;
                }
                @Override
                public int hashCode() {
                        final int prime = 31;
                        int result = 1;
                        result = prime * result + ((column1 == null) ? 0 : column1.hashCode());
                        result = prime * result + ((column2 == null) ? 0 : column2.hashCode());
                        result = prime * result + ((column3 == null) ? 0 : column3.hashCode());
                        result = prime * result + (column4 ? 1231 : 1237);
                        return result;
                }
                @Override
                public boolean equals(Object obj) {
                        if (this == obj)
                                return true;
                        if (obj == null)
                                return false;
                        if (getClass() != obj.getClass())
                                return false;
                        Row other = (Row) obj;
                        if (column1 == null) {
                                if (other.column1 != null)
                                        return false;
                        } else if (!column1.equals(other.column1))
                                return false;
                        if (column2 == null) {
                                if (other.column2 != null)
                                        return false;
                        } else if (!column2.equals(other.column2))
                                return false;
                        if (column3 == null) {
                                if (other.column3 != null)
                                        return false;
                        } else if (!column3.equals(other.column3))
                                return false;
                        if (column4 != other.column4)
                                return false;
                        return true;
                }
        }
}
An implementation of the SwingWorker is provided for you. If your background task takes more than 100ms (configurable) to complete, an indeterminate wait dialog will show up. You have control over a text area shown on this wait dialog, to display information about the progress of the background task.

    package test;
    
    import java.awt.event.ActionEvent;
    import java.util.ArrayList;
    import java.util.List;
    
    import javax.swing.JButton;
    import javax.swing.JCheckBox;
    import javax.swing.JComboBox;
    import javax.swing.JFrame;
    import javax.swing.JOptionPane;
    import javax.swing.JScrollPane;
    import javax.swing.JTextField;
    import javax.swing.UIManager;
    
    import org.aesthete.swingobjects.ActionProcessor;
    import org.aesthete.swingobjects.SwingObjectsInit;
    import org.aesthete.swingobjects.YesNo;
    import org.aesthete.swingobjects.annotations.Action;
    import org.aesthete.swingobjects.annotations.DataBeanName;
    import org.aesthete.swingobjects.annotations.Required;
    import org.aesthete.swingobjects.annotations.Trim;
    import org.aesthete.swingobjects.datamap.SwingObjData;
    import org.aesthete.swingobjects.exceptions.SwingObjectException;
    import org.aesthete.swingobjects.scope.RequestScopeObject;
    import org.aesthete.swingobjects.view.CommonUI;
    import org.aesthete.swingobjects.view.FrameFactory;
    import org.aesthete.swingobjects.view.SwingObjFormBuilder;
    import org.aesthete.swingobjects.view.SwingObjFormBuilder.ButtonBarPos;
    import org.aesthete.swingobjects.view.table.SwingObjTable;
    import org.aesthete.swingobjects.view.validator.Validator;
    import org.aesthete.swingobjects.workers.CommonSwingWorker;
    
    import com.jgoodies.forms.layout.FormLayout;
    
    @DataBeanName("CompTest")
    public class CompTest extends JFrame implements Validator{

        private static final long serialVersionUID = 1L;

        @Required
        private JTextField tftest;

        @Required
        @Trim(YesNo.NO)
        private JTextField tftest1;

        @Required
        private JComboBox cbCombo;

        private JCheckBox chkBx;

        private JButton btntest;

        private SwingObjTable<TestData> table;

        public CompTest() {
                try {
                        btntest = new JButton("Test");
                        tftest = new JTextField("Check this out");
                        tftest.setColumns(20);
                        tftest.setActionCommand("tftest");
                        btntest.setActionCommand("btntest");
                        tftest1 = new JTextField("123254562634");
                        tftest1.setColumns(20);

                        table = new SwingObjTable<TestData>(TestData.class);
                        List<TestData> list = new ArrayList<TestData>() {
                                {
                                        add(new TestData("test1","test2","No", true));
                                        add(new TestData("test1", "test2", "Yes", true));
                                        add(new TestData("test1", "test2", "No", true));
                                        add(new TestData("test1", "test2", "Yes", true));
                                        add(new TestData("test1", "test2", "No", true));
                                }
                        };
                        table.setData(list);
                        table.makeColumnsIntoComboBox(new String[]{"Yes","No"}, 2);
                        cbCombo = new JComboBox(new String[] { "Yes", "No" });
                        chkBx = new JCheckBox("Is to be checked?");
                        SwingObjFormBuilder builder=new SwingObjFormBuilder(new FormLayout("5dlu,100dlu:grow,2dlu,100dlu,2dlu,50dlu,2dlu,100dlu,5dlu"));
                        builder.addComponents(tftest,tftest1,cbCombo,chkBx);
                        builder.nextLine("150dlu");
                        builder.addComponent(new JScrollPane(table), 7);
                        builder.addButtonBar(7, ButtonBarPos.Center, null, btntest);
                        this.setContentPane(builder.getPanel());
                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        @Action(value="tftest")
        public void test1(ActionEvent e) {
                System.out.println(tftest.getText());
        }

        @Action("btntest")
        public void test2(ActionEvent e) {
                System.out.println("button clicked");
                CommonSwingWorker worker=new CommonSwingWorker("btntest") {

                        @Override
                        public boolean validateAndPopulate(RequestScopeObject scopeObj) {
                                SwingObjData objData = (SwingObjData) scopeObj.getObjectFromMap("CompTest");
                                if(!"I like sugar".equals(objData.getValue("tftest").asString())) {
                                        CommonUI.setErrorBorderAndTooltip(tftest,"Has to be \"I like sugar\"");
                                        return false;
                                }
                                return true;
                        }

                        @Override
                        public void callModel(RequestScopeObject scopeObj) throws SwingObjectException {

                                // sleep to simulate long running task
                                try {
                                        Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                SwingObjData objData = (SwingObjData) scopeObj.getObjectFromMap("CompTest");
                                System.out.println(objData.getValue("tftest").asString());
                                List<TestData> testdata=(List<TestData>)objData.getValue("table").getValue();
                                testdata.get(0).setTftest1("Changed to short");
                                objData.set("table", testdata);
                                System.out.println(testdata.size());
                                objData.set("tftest","12345");
                        }

                        @Override
                        public void callConnector(RequestScopeObject scopeObj) {
                                JOptionPane.showMessageDialog(CompTest.this, "Connector called successfully");
                        }
                };

                ActionProcessor.processAction(this, worker);

        }

        public static void main(String[] args) {
                try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        SwingObjectsInit.init("swingobjects","application");
                        CompTest test = FrameFactory.getNewContainer("test", CompTest.class);
                        CommonUI.showOnScreen(test);
                } catch (SwingObjectException e) {
                        e.printStackTrace();
                }catch(Exception e){
                        e.printStackTrace();
                }

        }

        @Override
        public boolean validate(String action) {

                return false;
        }

        @Override
        public boolean continueIfError(String action) {
                return false;
        }
}
