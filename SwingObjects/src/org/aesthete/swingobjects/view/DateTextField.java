package org.aesthete.swingobjects.view;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import org.aesthete.swingobjects.CommonUtilities;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;

public class DateTextField extends JFormattedTextField implements Observer {

	public void update(Observable o, Object arg) {
		Calendar calendar = (Calendar) arg;
		DatePicker dp = (DatePicker) o;
		setText(dp.formatDate(calendar));
	}

	public DateTextField() {
		final Observer obs = this;
		final JTextField tf = this;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tf.isEditable()){
					Point p = e.getPoint();
					if(p.x>=getWidth()-22 && p.x <getWidth()
							&& p.y<=getHeight()) {
						DatePicker dp = new DatePicker(obs);
						Date selectedDate = dp.parseDate(tf.getText());
						dp.setSelectedDate(selectedDate);
						dp.start(tf);
					}
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				if(p.x>=getWidth()-22 && p.x <getWidth()
						&& p.y<=getHeight()) {
					DateTextField.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}else {
					DateTextField.this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
				}
			}
		});
	}

	public DateTextField(String text) {
		this();
		this.setText(text);
	}

	public DateTextField(Date date) {
		this();
		if(date!=null) {
			this.setText(CommonUtilities.getDateInddMMYYYYFormat(date));
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		try {
			BufferedImage img = ImageIO.read(DateTextField.class.getResourceAsStream("/images/calendar.png"));
			int x=getWidth()-26;
			g.drawImage(img,x,1,25,getHeight()-2,null);
		}catch (IOException e) {
			throw new SwingObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}
}
