package org.aesthete.swingobjects.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class LabelButton extends JLabel implements MouseListener{
	private static final long serialVersionUID = -7373584786498596648L;

	private Border highlightBorder = BorderFactory.createLoweredBevelBorder();
	private Border lineBorder=BorderFactory.createLineBorder(Color.black);

	protected String text;
	protected Icon icon;

	public LabelButton(Color bgColor,String text,String image) {
		super(text,new ImageIcon(LabelButton.class.getResource(image)),JLabel.LEFT);
		this.setFocusable(true);
		if(bgColor!=null) {
			this.setBackground(bgColor);
		}
		this.text=text;
		this.icon=getIcon();
		this.addMouseListener(this);
	}

	public LabelButton(Color bgColor,String text) {
		super(text,JLabel.LEFT);
		this.setFocusable(true);
		if(bgColor!=null) {
			this.setBackground(bgColor);
		}
		this.text=text;
		this.icon=getIcon();
		this.addMouseListener(this);
	}


	public void focusLost() {
		this.setBorder(null);
		this.repaint();
		this.revalidate();
		this.getParent().repaint();
		((JPanel)this.getParent()).revalidate();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.requestFocusInWindow();
		this.setBorder(highlightBorder);
		this.repaint();
		this.revalidate();
		this.getParent().repaint();
		((JPanel)this.getParent()).revalidate();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.getBorder()==null) {
			this.setBorder(lineBorder);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.getBorder()==lineBorder) {
			this.setBorder(null);
		}
	}
}
