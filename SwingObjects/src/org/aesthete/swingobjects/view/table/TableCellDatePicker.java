package org.aesthete.swingobjects.view.table;

import org.aesthete.swingobjects.view.DatePicker;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXTable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class TableCellDatePicker implements Observer{


	private JXTable table;
	private int colThatIsDate;

	public TableCellDatePicker(JXTable table, int colThatIsDate) {
		super();
		this.table = table;
		this.colThatIsDate=colThatIsDate;
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showDatePicker(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showDatePicker(e);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showDatePicker(e);
				}
			}
		});
	}

	protected void showDatePicker(MouseEvent e) {
        if(table.getRowCount()==0){
            return;
        }
		Point p=e.getPoint();
		int col = table.columnAtPoint(p);

		if(col==colThatIsDate) {
			int row=table.rowAtPoint(p);
			int width=table.getColumnModel().getColumn(colThatIsDate).getWidth();

			String data=(String)table.getValueAt(row, col);
			DatePicker dp = new DatePicker(this,row);
			if(StringUtils.isNotEmpty(data)) {
				Date selectedDate = dp.parseDate(data);
				dp.setSelectedDate(selectedDate);
			}
			Rectangle rect=table.getCellRect(row,col,true);
			Point point = table.getLocationOnScreen();
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			int widthOfDp=getFractionedWidth(13,dim);
			int pointx = point.x+rect.x+width;
			if(pointx+widthOfDp>dim.getWidth()) {
				pointx=point.x+rect.x-widthOfDp;
			}
			dp.start(pointx, point.y+rect.y);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Calendar calendar = (Calendar) arg;
		DatePicker dp = (DatePicker) o;
		table.setValueAt(dp.formatDate(calendar), dp.getRow(), colThatIsDate);
	}

	public int getFractionedWidth(double widthPercent,Dimension dim){
		return (int)(dim.getWidth()*widthPercent/100);
	}
}
