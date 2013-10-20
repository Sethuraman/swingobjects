package org.aesthete.swingobjects.view;

import java.awt.Color;

import javax.swing.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class is to be used to build your panels. It builds using the PanelBuilder provided by the Jgoodies Forms. It
 * provides a lot more functionality and makes life a lit easier. If you would like add more code to this, extend it and add
 * your functionality. Else build your own with this as an example if you need any reference.
 *
 * For an example on how to use this, refer the {@link test.BuilderShowcase} class.
 * @author sethu
 *
 */
public class SwingObjFormBuilder {

    private JDialog dialog;

    public enum ButtonBarPos{Center,Left,Right};
	public static final int START_FROM_CUR_COL=-1;

	private FormLayout layout;
	private JPanel panel;
	private int columnCount;
	private CellConstraints cc=new CellConstraints();
	private PanelBuilder builder;
	private boolean isDontAddRowsAuto;


	/**
	 * Provide just the colspecs in this form via a comma separated string. The assumption is
	 * you will leave 1 column in the beginning and 1 column in the end as empty space. If you
	 * dont want to leave empty space around your components, then start with a 0dlu column.
	 *
	 *<pre>
	 * 9 column spec could look like this:
	 * "10dlu,pref,10dlu,fill:100dlu,5dlu,pref,10dlu,fill:100dlu,10dlu"
	 * The builder will start adding your component from column no. 2 onwards-  starting at pref.
	 *
	 * </pre>
	 * To understand how to use the columns specs in joodies, refer to Karsten Lentzsch whitepaper here
	 * <a href="http://www.jgoodies.com/articles/forms.pdf">White Paper</a>.
	 *
	 *A new Jpanel will be automatically created and all components will be added to that panel.
	 *
	 *2 rows will be appended to the layout automatically. The assumption is you will leave a gap around your components.
	 *So the first row will be a gap.
	 *
	 *
	 * @param colSpecs
	 */
	public SwingObjFormBuilder(String colSpecs) {
		this(new FormLayout(colSpecs));
	}

	/**
	 * Same as the above constructor, except that you are allowed to provide your panel.
	 * @param colSpecs
	 * @param panel
	 */
	public SwingObjFormBuilder(String colSpecs,JPanel panel) {
		this(new FormLayout(colSpecs),panel);
	}

    public SwingObjFormBuilder(String colSpecs,JFrame frame) {
        this(new FormLayout(colSpecs));
        frame.setContentPane(panel);
    }

    public SwingObjFormBuilder(String colSpecs,JDialog dialog) {
        this(new FormLayout(colSpecs));
        dialog.setContentPane(panel);
    }

	/**
	 * Same as above except that instead of String encoded column specs, you can provide a FormLayout object. In the Form layout object, if you specify rows
	 * as well, then the builder will NOT add the first 2 rows automatically. You will have to provide at a minimum provide the first 2 rows in the layout and then
	 * append additional rows by calling {@link SwingObjFormBuilder#nextLine()}
	 * @param layout
	 */
	public SwingObjFormBuilder(FormLayout layout) {
		this.layout=layout;
		columnCount=layout.getColumnCount();
		panel=new JPanel();
		builder=new PanelBuilder(layout,panel);
		if(layout.getRowCount()==0){
			builder.appendRow("$rowgap");
			builder.appendRow("$row");
		}else{
			isDontAddRowsAuto=true;
		}
		incrementRowCounter(1);
		incrementColumnCounter(1);
	}

	public SwingObjFormBuilder(FormLayout layout,JPanel panel) {

		this.layout=layout;
		this.panel=panel;
		columnCount=layout.getColumnCount();
		builder=new PanelBuilder(layout,panel);
		if(layout.getRowCount()==0){
			builder.appendRow("$rowgap");
			builder.appendRow("$row");
		}else{
			isDontAddRowsAuto=true;
		}
		incrementRowCounter(1);
		incrementColumnCounter(1);
	}

	private int col=1;


	private int row=1;

	/**
	 * Add a Jlabel with value and a component side by side separated by column. Meaning if the
	 * label is added at column x then the component will be added at column x+2.
	 * @param value
	 * @param component
	 */
	public void addLblValAndComp(String value, JComponent component){
		JLabel lbl=new JLabel(value);
		builder.add(lbl,cc.xy(col,row));
		incrementColumnCounter(2);
		builder.add(component,cc.xy(col,row));
		incrementColumnCounter(2);
	}

	/**
	 * Add an array of components on the same line. There should be a gap between each component.
	 * Meaning between components[i] and components[i+1] will be put into column x and
	 * column x+2.
	 * @param components
	 */
	public void addComponents(JComponent... components){
		for(JComponent comp : components){
			builder.add(comp,cc.xy(col,row));
			incrementColumnCounter(2);
		}
	}

	/**
	 * Add a component to span across columns.
	 *
	 * @param component component to add
	 * @param colSpan integer representing the no. of columns to span
	 */
	public void addComponent(JComponent component,int colSpan){
		builder.add(component,cc.xyw(col,row,colSpan));
		incrementColumnCounter(colSpan+1);
	}

	/**
	 * Add a component to span across columns and rows.
	 * @param component component to add
	 * @param colSpan integer representing the no. of columns to span
	 * @param rowSpan integer representing the no. of rows to span
	 */
	public void addComponent(JComponent component,int colSpan,int rowSpan){
		builder.add(component,cc.xywh(col,row,colSpan,rowSpan));
		incrementColumnCounter(colSpan+1);
	}

	/**
	 * Add a component to span across all the defined columns.
	 *
	 * @param component component to add
	 */
	public void addComponentSpanAllCols(JComponent component){
		builder.add(component,cc.xyw(1,row,layout.getColumnCount()));
		resetColumnCount();
	}

	/**
	 * Creates a JPanel and add components to the center of it.
	 *
	 * @param bgColor Provide a color for the panel that will be created. null if the look and feels colour needs to be used.
	 * @param colSpan Number of columns to span
	 * @param startCol The starting column
	 * @param components the components to add. There will be a gap $columngap appended between each column automatically
	 */
	public void addComponentsToCenter(Color bgColor,int colSpan,int startCol,JComponent... components){
		FormLayout centreLayout=new FormLayout();
		PanelBuilder tempbuilder=new PanelBuilder(centreLayout);
		tempbuilder.appendRow("$row");
		tempbuilder.appendGlueColumn();
		CellConstraints cc=new CellConstraints();
		int x=2;
		int y=1;
		for(JComponent comp : components){
			tempbuilder.appendColumn("$lbl");
			tempbuilder.appendColumn("$columngap");
			tempbuilder.add(comp,cc.xy(x,y));
			x+=2;
		}
		tempbuilder.appendGlueColumn();
		int tempstartcol=startCol==-1?col:startCol;
		JPanel temppanel = tempbuilder.getPanel();
		temppanel.setBackground(bgColor);
		builder.add(temppanel, cc.xyw(tempstartcol,row,colSpan));
	}

	/**
	 * Add component at the current column.
	 * @param component
	 */
	public void addComponent(JComponent component){
		builder.add(component,cc.xy(col,row));
		incrementColumnCounter(2);
	}

	/**
	 * Add a vertical separator at the specified column
	 * @param col column where the vertical separator should be added
	 */
	public void addVerticalSeparator(int col){
		JSeparator sep=new JSeparator(SwingConstants.VERTICAL);
		CellConstraints cc=new CellConstraints();
		panel.add(sep,cc.xywh(col, 1,1,layout.getRowCount()));
	}

    public void addButtonBar(JButton... btns){
        addButtonBar(columnCount-2, ButtonBarPos.Center, null, btns);
    }

	/**
	 * Adds a button bar which honours the platform's ordering of buttons. A $rowbtngap will be appended first
	 * then the button bar will be added onto a new $row after that.
	 * @param colSpan no. of columns to span
	 * @param buttonBarPos position of the buttons on the bar.
	 * @param bgColor a colour of the panel if required. Else null.
	 * @param buttons the button array to be added.
	 */
	public void addButtonBar(int colSpan,ButtonBarPos buttonBarPos,Color bgColor,JButton... buttons){
		addButtonBar("$rowbtngap", "$row", colSpan, buttonBarPos, bgColor, buttons);
	}

	/**
	 * Same as {@link SwingObjFormBuilder#addButtonBar(int, ButtonBarPos, Color, JButton...)} except that you
	 * are allowed to specify the button row gap and button row's row specs.
	 * @param buttonRowGap row specs representing the gap between the panel and button bar
	 * @param buttonRow row specs representing the row on which the buttons will be put on
	 * @param colSpan number of columns the button bar will span
	 * @param buttonBarPos position of the buttons on the bar
	 * @param bgColor Background colour. Null if none.
	 * @param buttons the button you wish to add
	 */
	public void addButtonBar(String buttonRowGap,String buttonRow,int colSpan,
			ButtonBarPos buttonBarPos,Color bgColor,JButton... buttons){
		if(!isDontAddRowsAuto){
			builder.appendRow(buttonRowGap);
			builder.appendRow(buttonRow);
			incrementRowCounter(2);
			resetColumnCount();
		}
		JPanel buttonBar=null;
		switch(buttonBarPos){
			case Center: buttonBar=ButtonBarFactory.buildCenteredBar(buttons); break;
			case Left: buttonBar=ButtonBarFactory.buildLeftAlignedBar(buttons); break;
			case Right: buttonBar=ButtonBarFactory.buildRightAlignedBar(buttons); break;
		}
		if(bgColor!=null){
			buttonBar.setBackground(bgColor);
		}
		builder.add(buttonBar,cc.xyw(col,row,colSpan));
		incrementColumnCounter(colSpan+1);
	}

	/**
	 * Adds a horizontal separator spanning all columns. It appends a $rowsegap first and then add the separator on a new $row
	 */
	public void addSeperator(){
		builder.appendRow("$rowsegap");
		incrementRowCounter(1);
		resetColumnCount();
		builder.addSeparator(null,cc.xyw(col-1,row,columnCount));
		builder.appendRow("$row");
		incrementRowCounter(1);
	}


    /**
     * Adds a horizontal separator spanning all columns. It appends a $rowsegap first and then add the separator on a new $row
     */
    public void addSeperator(String rowGapSpec, String rowSpec){
        builder.appendRow(rowGapSpec);
        incrementRowCounter(1);
        resetColumnCount();
        builder.addSeparator(null,cc.xyw(col-1,row,columnCount));
        builder.appendRow(rowSpec);
        incrementRowCounter(1);
    }


    /**
	 * Adds a labeled horizontal separator spanning all columns. It appends a $rowsegap first and then add the separator on a new $row
	 * @param value the label to be used on the separator.
	 */
	public void addLabeledSeparatorFromValue(String value){
		builder.appendRow("$rowsegap");
		incrementRowCounter(1);
		resetColumnCount();
		builder.addSeparator(value,cc.xyw(col,row,columnCount-col));
		builder.appendRow("$row");
		incrementRowCounter(1);
		resetColumnCount();
	}

	/**
	 * Provide a set of row specs to be appeneded to the builder. Have a look at
	 * {@link test.BuilderShowcase} for an example of where this is useful.
	 * @param rowspecs
	 */
	public void nextFewLines(String... rowspecs) {
		incrementRowCounter(2);
		for(String rowspec : rowspecs) {
			builder.appendRow(rowspec);
		}
		resetColumnCount();
	}

	/**
	 * Increment the row counter by 2 and dont append any rows. It also resets the column count
	 */
	public void nextLinePlain(){
		incrementRowCounter(2);
		resetColumnCount();
	}


	/**
	 * Append a row as a row gap and then append a row.
	 * @param gap row gap spec
	 * @param row row spec
	 */
	public void nextLine(String gap,String row){
		resetColumnCount();
		builder.appendRow(gap);
		builder.appendRow(row);
		incrementRowCounter(2);
	}

	/**
	 * Appends a $rowgap row first and then a $row row.
	 */
	public void nextLine(){
		resetColumnCount();
		builder.appendRow("$rowgap");
		builder.appendRow("$row");
		incrementRowCounter(2);
	}

	/**
	 * Appends a $rowgap first and then a row based on the rowspec provided
	 * @param rowSpec row spec to be added
	 */
	public void nextLine(String rowSpec){
		resetColumnCount();
		builder.appendRow("$rowgap");
		builder.appendRow(rowSpec);
		incrementRowCounter(2);
	}

	/**
	 * Appends a new row based on the rowspec without any gap.
	 * @param rowSpec
	 */
	public void nextLineWithoutGap(String rowSpec){
		resetColumnCount();
		builder.appendRow(rowSpec);
		incrementRowCounter(1);
	}

	/**
	 * Resets the column counter to 2
	 */
	public void resetColumnCount() {
		col=2;
	}

	/**
	 * Increments the column counter by value
	 * @param value
	 */
	public void incrementColumnCounter(int value){
		col+=value;
	}

	/**
	 * Increments the row counter by value
	 * @param value
	 */
	public void incrementRowCounter(int value){
		row+=value;
	}

	/**
	 * Returns the panel constructed
	 * @return
	 */
	public JPanel getPanel() {
		return panel;
	}


	/**
	 * Returns the panel constructed after applying a background colour to it.
	 * @param bgColor
	 * @return
	 */
	public JPanel getPanel(Color bgColor) {
		builder.appendRow("$rowgap");
		panel.setBackground(bgColor);
		return panel;
	}


/**
 * Complete laying out a panel by appending a $rowgap at the end.
 */
	public void complete(){
		builder.appendRow("$rowgap");
	}

	/**
	 * Sets the rowcounter to the last row.
	 */
	public void goToLastRow() {
		this.row=layout.getRowCount();
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public void setColumn(int col) {
		this.col = col;
	}

	/**
	 * Tells the builder not append rows automatically.
	 * @return
	 */
	public boolean isDontAddRowsAuto() {
		return isDontAddRowsAuto;
	}

	/**
	 * Tells the builder not append rows automatically.
	 * @return
	 */
	public void setDontAddRowsAuto(boolean isDontAddRowsAuto) {
		this.isDontAddRowsAuto = isDontAddRowsAuto;
	}

	/**
	 * Create a new JPanel based on the column specs provided and add the components to it. Make sure the colspecs includes a gap before and after adding
	 * all components.
	 * @param colSpan
	 * @param bgcolor
	 * @param layout
	 * @param components
	 */
	public void addComponentsInNewPanel(int colSpan,Color bgcolor,String layout,JComponent... components) {
		FormLayout innerLayout=new FormLayout(layout);
		SwingObjFormBuilder builder=new SwingObjFormBuilder(innerLayout);
		builder.addComponents(components);
		if(bgcolor==null) {
			this.addComponent(builder.getPanel(),colSpan);
		}else {
			this.addComponent(builder.getPanel(bgcolor),colSpan);
		}
	}

	/**
	 * 	Create a new JPanel based on the column and row specs provided and add the components to it. Make sure the colspecs and row specs
	 *  include a gap before and after adding all components.
	 * @param colSpan
	 * @param bgcolor
	 * @param collayout
	 * @param rowlayout
	 * @param components
	 * @return
	 */
	public JPanel addComponentsInNewPanel(int colSpan,Color bgcolor,String collayout,String rowlayout,JComponent... components) {
		FormLayout innerLayout=new FormLayout(collayout,rowlayout);
		SwingObjFormBuilder builder=new SwingObjFormBuilder(innerLayout);
		builder.addComponents(components);
		JPanel panel=null;
		if(bgcolor==null) {
			panel=builder.getPanel();
		}else {
			panel=builder.getPanel(bgcolor);
		}
		this.addComponent(panel,colSpan);

		return panel;
	}
}
