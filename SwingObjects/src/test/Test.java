package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.SwingObjFormBuilder;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXFrame;

import com.jgoodies.forms.layout.FormLayout;

public class Test {

	public static class BackgroundImg extends JPanel {
		private Image img;

		public BackgroundImg(String img) {
			this(new ImageIcon(BackgroundImg.class.getResource(img)).getImage());
		}

		public BackgroundImg(Image img) {
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			// setLayout(null);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
		}
	}

	public static void main(String[] args) {
		try {

			SwingObjectsInit.init("/swingobjects.properties", "/error.properties");

			JXFrame frame = new JXFrame();

			BackgroundImg img=new BackgroundImg("/images/waitdialog.png");

			SwingObjFormBuilder builder = new SwingObjFormBuilder(new FormLayout("10dlu:grow,fill:400px,10dlu:grow",
					"10dlu:grow,center:30dlu,20dlu,fill:200dlu:grow,10dlu:grow"),img);

			JXBusyLabel label = new JXBusyLabel();
			label.setText("Please wait");
			label.setBusy(true);
			label.setForeground(Color.white);

			builder.addComponent(label);
			builder.nextLine();

			JTextArea textarea = new JTextArea("Please wait...\nSome processing happening....\n");
			textarea.setForeground(Color.white);
			textarea.setOpaque(false);
			textarea.setBorder(BorderFactory.createLineBorder(Color.white));
			builder.addComponent(textarea);
			//
			// JPanel panel = builder.getPanel();
			// panel.setOpaque(true);
			// layeredPane.add(panel, 1);

			// frame.setUndecorated(true);
			frame.setContentPane(img);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(500, 300);
			CommonUI.locateOnOpticalScreenCenter(frame);
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
