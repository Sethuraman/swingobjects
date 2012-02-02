package org.aesthete.swingobjects.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

public class BlinkerLabel extends LabelButton implements Runnable,ActionListener{

	private Timer timer;
	private boolean isVisible;
	private String text;

	public BlinkerLabel(Color bgColor,String text,String image) {
		super(bgColor,text,image);
		this.text=text;
		new Thread(this).start();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setText(BlinkerLabel.this.text);
				setIcon(icon);
				isVisible=true;
				timer.stop();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setText(BlinkerLabel.this.text);
				setIcon(icon);
				isVisible=true;
				timer.start();
			}
		});
	}

	@Override
	public void run() {
		timer=new Timer(1000,this);
		timer.setRepeats(true);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isVisible) {
			setText(null);
			setIcon(null);
			isVisible=false;
		}else {
			setText(text);
			setIcon(super.icon);
			isVisible=true;
		}
	}

}
