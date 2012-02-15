package org.aesthete.swingobjects.view;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.util.FieldCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

/**
 * It all starts here. You will use the FrameFactory, to instantiate your
 * Dialogs, Frames that you wish to show on screen. To create a new Frame you
 * will have to call
 * {@link FrameFactory#getNewContainer(String, Class, Object...)}
 * 
 * <pre>
 * public class Test {
	public static class MyFrame extends JFrame{
		private JPanel panel;
		public MyFrame() {
			//create a new panel
			panel=FrameFactory.getNewContainer("TestSetOfContainers", MyPanel.class, "Test");
			panel.setName("testpanel");
			setContentPane(panel);
		}
	}
	
	public static class MyPanel extends JPanel{
		private JButton btnTest;
		private JButton btnTest2;
		public MyPanel(String data) {
			btnTest=new JButton("Test");
			btnTest2=new JButton("Test2");
			btnTest2.setActionCommand("dosomething");
		}
		
	
	
	   //This method is automatically invoked when btnTest is clicked. The String inside action is 
	   //the label/text set in the button
	
		@Action("Test")
		public void dotestwork(ActionEvent e) {
			//handle Test
		}
		
		
	    //This method is invoked when btnTest2 is clicked. The String inside action is 
		//the action command set in the button. This takes precedence over the action 
		//invoked by using just the Label of the button.
		
		@Action("dosomething")
		public void handleSomething(ActionEvent e) {
			// handle do something
		}
		
	}
	public static void main(String[] args) {
		//create a new frame
		MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class);
		frame.setName("frame");
		
		
		//get hold of the panel in it
		MyPanel panel=FrameFactory.getContainer("TestSetOfContainers", MyPanel.class);
		
		// or get hold with the name
		panel=FrameFactory.getContainer("TestSetOfContainers", "testpanel");
		

		// do other work...
		
		
		//when you are finished dispose all the containers as below in one shot :
		FrameFactory.dispose("TestSetOfContainers");
		
		//=========>OR dispose individually<==========
		
		FrameFactory.dispose("TestSetOfContainers", MyPanel.class);
		FrameFactory.dispose("TestSetOfContainers", MyFrame.class);
		
		
		//=========>OR dispose individually with name<==========
		FrameFactory.dispose("TestSetOfContainers", "testpanel");
		FrameFactory.dispose("TestSetOfContainers", "frame");
		
	}
}
 </pre>
 * 
 * 
 * @author sethu
 * 
 */
public class FrameFactory {

	/**
	 * the data structure used to store all the containers managed by the swing objects framework. 
	 * 
	 * Each container will have child containers. Like in the case of a JFrame with a JPanel with a JTabbedPane with many JPanels in it.
	 * In that case you would associate one FrameSet ID with all these components.
	 * 
	 * This Frameset ID will be the key and all the JFrames and JPanels will be put into a Set under it. 
	 *
	 */
	private static final ConcurrentHashMap<String, Set<Component>> frames = new ConcurrentHashMap<String, Set<Component>>();

	/**
	 * This will instantiate a new container. The class passed in must extend from Container at the minimum. You 
	 * would generally pass in a JFrame, JPanel, JDialog, JTabbedPane, etc.
	 * 
	 * This method will automatically map your ActionListeners to be handled by providing the 
	 * Action annotation above your method. Look at the class level documentation for an example.
	 * 
	 * @param framesetid - This is a String ID provided to a set of Containers.
	 * @param clz Class object of the container you would like to instantiate
	 * @param objs Constructor arguments to pass to the Container's constructor
	 * @return the instantiated container, without requiring a type cast. 
	 * @throws SwingObjectRunException Runtime exception that you don't need to catch.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T getNewContainer(String framesetid, Class<T> clz, Object... objs)
			throws SwingObjectRunException {
		Component comp = null;
		try {
			comp = (Component) ConstructorUtils.invokeConstructor(clz, objs);
			putContainerInMap(framesetid, comp);
		} catch (SwingObjectRunException e) {
			throw e;
		} catch (Exception e) {
			throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
		return (T) comp;
	}

	/**
	 * If you would like to store a Container created in your code into the Swing objects framework, call this method.
	 * This method will result in the mapping of your action listeners as described in {@link FrameFactory#getNewContainer(String, Class, Object...)}
	 * @param framesetid
	 * @param comp
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void putContainerInMap(String framesetid, Component comp) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		try {
			Set<Component> setOfComps = null;
			if (frames.containsKey(framesetid)) {
				setOfComps = new HashSet<Component>();
			} else {
				frames.put(framesetid, setOfComps = new HashSet<Component>());
			}
			setOfComps.add(comp);
			registerActionlistener(comp);
		} catch (Exception e) {
			throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * get the container previously stored. 
	 * @param framesetid This is a String ID provided to a set of Containers.
	 * @param clz Class of the container to be returned
	 * @return the container if found, null if not
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T getContainer(String framesetid, Class<? extends Component> clz) {
		Set<Component> comps = frames.get(framesetid);
		if (comps != null) {
			for (Component comp : comps) {
				if (comp.getClass() == clz) {
					return (T) comp;
				}
			}
		}
		return null;
	}

	/**
	 * get the container previously stored. 
	 * @param framesetid This is a String ID provided to a set of Containers.
	 * @param componentName Name of the container ( it must have been set with Component.setName() first)
	 * @return the container if found, null if not
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T getContainer(String framesetid, String componentName) {
		Set<Component> comps = frames.get(framesetid);
		if (comps != null) {
			for (Component comp : comps) {
				if (componentName.equals(comp.getName())) {
					return (T) comp;
				}
			}
		}
		return null;
	}

	/**
	 * Disposes of the 
	 * @param framesetid
	 */
	public static void dispose(String framesetid) {
		Set<Component> comps = frames.remove(framesetid);
		if (comps != null) {
			for (Component comp : comps) {
				try {
					MethodUtils.invokeMethod(comp, "dispose", null);
				} catch (NoSuchMethodException e) {
					comp.setVisible(false);
				} catch (Exception e) {
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
				}
			}
		}
	}

	public static void dispose(String framesetid, Class<? extends Component> clz) {
		Set<Component> comps = frames.get(framesetid);
		if (comps != null) {
			Set<Component> toRemove=new HashSet<Component>();
			for (Component comp : comps) {
				if (comp.getClass() == clz) {
					try {
						toRemove.add(comp);
						MethodUtils.invokeMethod(comp, "dispose", null);
					} catch (NoSuchMethodException e) {
						comp.setVisible(false);
					} catch (Exception e) {
						throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
					}
				}
			}
			comps.removeAll(toRemove);
		}
	}
	
	public static void dispose(String framesetid,String name) {
		Set<Component> comps = frames.get(framesetid);
		if (comps != null) {
			for (Component comp : comps) {
				if (name.equals(comp.getName())) {
					try {
						MethodUtils.invokeMethod(comp, "dispose", null);
					} catch (NoSuchMethodException e) {
						comp.setVisible(false);
					} catch (Exception e) {
						throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
					}
				}
			}
		}
	}

	private static void registerActionlistener(final Object comp) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		final GlobalListener listener = new GlobalListener(comp);
		ReflectionUtils.iterateOverFields(comp.getClass(), null, new FieldCallback() {
			private Object prop;

			@Override
			public boolean filter(Field field) {
				try {
					prop = field.get(comp);
					if (Components.class.isAssignableFrom(field.getType())) {
						registerActionlistener(field.get(comp));
					} else if (prop instanceof AbstractButton || prop instanceof JComboBox || prop instanceof JTextField) {
						return true;
					}

				} catch (Exception e) {
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
				}
				return false;
			}

			@Override
			public void consume(Field field) {
				try {
					MethodUtils.invokeMethod(prop, "addActionListener", listener);
				} catch (Exception e) {
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
				}
			}
		});
	}

	private static int frameno;

	public static synchronized String getNewFrameSetId() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmssSSSSS" + frameno++);
		return sdf.format(new Date());
	}

}
