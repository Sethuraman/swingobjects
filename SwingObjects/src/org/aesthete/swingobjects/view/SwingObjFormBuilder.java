package org.aesthete.swingobjects.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SwingObjFormBuilder {

	public enum ButtonBarPos{Center,Left,Right};
	public static final int START_FROM_CUR_COL=-1;

	private FormLayout layout;
	private JPanel panel;
	private int columnCount;
	private CellConstraints cc=new CellConstraints();
	private PanelBuilder builder;
	private boolean isDontAddRowsAuto;

	public SwingObjFormBuilder(String colSpecs) {
		this(new FormLayout(colSpecs));
	}

	public SwingObjFormBuilder(String colSpecs,JPanel panel) {
		this(new FormLayout(colSpecs),panel);
	}

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

	public void addLblValAndComp(String value, JComponent component){
		JLabel lbl=new JLabel(value);
		builder.add(lbl,cc.xy(col,row));
		incrementColumnCounter(2);
		builder.add(component,cc.xy(col,row));
		incrementColumnCounter(2);
	}

	public void addComponents(JComponent... components){
		for(JComponent comp : components){
			builder.add(comp,cc.xy(col,row));
			incrementColumnCounter(2);
		}
	}

	public void addComponent(JComponent component,int colSpan){
		builder.add(component,cc.xyw(col,row,colSpan));
		incrementColumnCounter(colSpan+1);
	}

	public void addComponent(JComponent component,int colSpan,int rowSpan){
		builder.add(component,cc.xywh(col,row,colSpan,rowSpan));
		incrementColumnCounter(colSpan+1);
	}

	public void addComponentSpanAllCols(JComponent component){
		builder.add(component,cc.xyw(1,row,layout.getColumnCount()));
		resetColumnCount();
	}

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

	public void addComponent(JComponent component){
		builder.add(component,cc.xy(col,row));
		incrementColumnCounter(2);
	}

	public void addVerticalSeparator(int col){
		JSeparator sep=new JSeparator(SwingConstants.VERTICAL);
		CellConstraints cc=new CellConstraints();
		panel.add(sep,cc.xywh(col, 1,1,layout.getRowCount()));
	}

	public void addButtonBar(int colSpan,ButtonBarPos buttonBarPos,Color bgColor,JButton... buttons){
		addButtonBar("$rowbtngap", "$row", colSpan, buttonBarPos, bgColor, buttons);
	}

	public void addButtonBar(String buttonGap,String button,int colSpan,
			ButtonBarPos buttonBarPos,Color bgColor,JButton... buttons){
		if(!isDontAddRowsAuto){
			builder.appendRow(buttonGap);
			builder.appendRow(button);
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


	public void addSeperator(){
		builder.appendRow("$rowsegap");
		incrementRowCounter(1);
		resetColumnCount();
		builder.addSeparator(null,cc.xyw(col-1,row,columnCount));
		builder.appendRow("$row");
		incrementRowCounter(1);
	}

	public void addLabeledSeparatorFromValue(String value){
		builder.appendRow("$rowsegap");
		incrementRowCounter(1);
		resetColumnCount();
		builder.addSeparator(value,cc.xyw(col,row,columnCount-col));
		builder.appendRow("$row");
		incrementRowCounter(1);
		resetColumnCount();
	}

	public void nextLinePlain(){
		incrementRowCounter(2);
		resetColumnCount();
	}

	public void nextLine(String gap,String row){
		resetColumnCount();
		builder.appendRow(gap);
		builder.appendRow(row);
		incrementRowCounter(2);
	}

	public void nextLine(){
		resetColumnCount();
		builder.appendRow("$rowgap");
		builder.appendRow("$row");
		incrementRowCounter(2);
	}

	public void nextLine(String rowSpec){
		resetColumnCount();
		builder.appendRow("$rowgap");
		builder.appendRow(rowSpec);
		incrementRowCounter(2);
	}

	public void nextLineWithoutGap(String rowSpec){
		resetColumnCount();
		builder.appendRow(rowSpec);
		incrementRowCounter(1);
	}

	public void resetColumnCount() {
		col=2;
	}

	public void incrementColumnCounter(int value){
		col+=value;
	}
	public void incrementRowCounter(int value){
		row+=value;
	}


	public JPanel getPanel() {
		builder.appendRow("$rowgap");
		return panel;
	}

	public JPanel getPanelNoGap() {
		return panel;
	}

	public JPanel getPanel(Color bgColor) {
		builder.appendRow("$rowgap");
		panel.setBackground(bgColor);
		return panel;
	}

	public JPanel getPanelNoGap(Color bgColor) {
		panel.setBackground(bgColor);
		return panel;
	}

	public void complete(){
		builder.appendRow("$rowgap");
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
	public boolean isDontAddRowsAuto() {
		return isDontAddRowsAuto;
	}

	public void setDontAddRowsAuto(boolean isDontAddRowsAuto) {
		this.isDontAddRowsAuto = isDontAddRowsAuto;
	}

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
