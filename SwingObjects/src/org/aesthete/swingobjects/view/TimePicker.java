package org.aesthete.swingobjects.view;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.aesthete.swingobjects.SwingObjProps;

public class TimePicker extends JSpinner {

	private SpinnerDateModel sm;

	public TimePicker(Date date) {
		super();
		constructObj(date);
	}

	public TimePicker() {
		super();
		constructObj(null);
	}

	private void constructObj(Date date) {
		sm = new SpinnerDateModel(date==null?new Date():date, null, null, Calendar.HOUR_OF_DAY);
	    this.setModel(sm);
	    JSpinner.DateEditor de = new JSpinner.DateEditor(this, SwingObjProps.getSwingObjProperty("timepicker.timeformat"));
	    this.setEditor(de);
	}


	public Date getText() {
		return sm.getDate();
	}

	public void setText(Date time) {
		sm.setValue(time);
	}
}
