package org.aesthete.swingobjects.view;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.exceptions.SwingObjectsExceptions;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

public class CommonUI {

	public static void nameComponents(Class<?> clz, Object obj) {
		try {
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				if (CommonUI.isNamedComponent(field.getType())) {
					field.setAccessible(true);
					if (field.get(obj) != null) {
						String name = (String) MethodUtils.invokeMethod(field.get(obj), "getName", null);
						if (StringUtils.isNotEmpty(name)) {
							MethodUtils.invokeMethod(field.get(obj), "setName", field.getName());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	public static boolean isNamedComponent(Class<?> type) {
		if (!JComponent.class.isAssignableFrom(type)) {
			return false;
		}
		if (JPanel.class.isAssignableFrom(type)) {
			return false;
		} else if (JDialog.class.isAssignableFrom(type)) {
			return false;
		} else if (JFrame.class.isAssignableFrom(type)) {
			return false;
		}

		return true;
	}

	public static void showErrorDialogForComponent(final JComponent component, SwingObjectsExceptions e) {
		JXErrorPane errorPane = new JXErrorPane();
		ImageIcon imageIcon = null;
		switch (e.getErrorSeverity()) {
		case SEVERE:
			imageIcon = new javax.swing.ImageIcon(CommonUI.class.getResource("/images/error.png"));
			errorPane.setErrorReporter(new InfoGatherErrorReporterFrame());
			break;
		case ERROR:
			imageIcon = new javax.swing.ImageIcon(CommonUI.class.getResource("/images/error.png"));
			break;
		case WARNING:
			imageIcon = new javax.swing.ImageIcon(CommonUI.class.getResource("/images/warning.png"));
			break;
		case INFO:
			imageIcon = new javax.swing.ImageIcon(CommonUI.class.getResource("/images/info.png"));
			break;
		}
		String detailedMessage = e.getDetailedMessage(false);
		errorPane.setIcon(imageIcon);
		errorPane.setErrorInfo(new ErrorInfo(e.getErrorSeverity().toString(), e.getDetailedMessage(true), detailedMessage, null, (Throwable) e, e
				.getErrorSeverity().getJavaLoggingLevel(), null));
		JXErrorPane.showFrame(null, errorPane);
	}

	public static void showOnScreen(JFrame frame) {
		frame.pack();
		locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	public static void showOnScreen(JDialog frame) {
		frame.pack();
		locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	 /**
     * Locates the given component on the screen's center.
     *
     * @param component   the component to be centered
     */
    public static void locateOnOpticalScreenCenter(Component component) {
    	GraphicsConfiguration gc = component.getGraphicsConfiguration();
    	Rectangle bounds = gc.getBounds();
    	int x = (int) ( bounds.getWidth() - component.getWidth() ) /2 ;
    	int y = (int) ( bounds.getHeight() - component.getHeight() ) /2;
    	component.setLocation( x, y );
    }

    public static void setIconImageForContainer(Window window){
    	String iconimg = SwingObjProps.getProperty("application-icon");
		if(StringUtils.isNotEmpty(iconimg)){
			window.setIconImage(new ImageIcon(CommonUI.class.getResource(iconimg)).getImage());

    	}
    }

    public static ImageIcon getScaledImage(int width, int height, String imageLoc){
		ImageIcon icon=new ImageIcon(CommonUI.class.getResource(imageLoc));
		Image scaledImage=icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon=new ImageIcon(scaledImage);
		return icon;
	}

}
