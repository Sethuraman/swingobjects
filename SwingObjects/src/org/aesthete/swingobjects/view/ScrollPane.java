package org.aesthete.swingobjects.view;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {

	public ScrollPane() {
		getVerticalScrollBar().setUnitIncrement(16);
		getHorizontalScrollBar().setUnitIncrement(16);
	}

}
