package org.aesthete.swingobjects.view;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.annotations.TitleIconImage;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.util.ReflectionCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * It all starts here. You will use the FrameFactory, to instantiate your
 * Dialogs, Frames, Panels or other containers that you wish to show on screen. To create a new container you
 * will have to call
 * {@link FrameFactory#getNewContainer(String, Class, Object...)}
 * <p/>
 * <pre>
 * public class Test {
 * public static class MyFrame extends JFrame{
 * private JPanel panel;
 * public MyFrame() {
 * //create a new panel
 * panel=FrameFactory.getNewContainer("TestSetOfContainers", MyPanel.class, "Test");
 * panel.setName("testpanel");
 * setContentPane(panel);
 * }
 * }
 *
 * public static class MyPanel extends JPanel{
 * private JButton btnTest;
 * private JButton btnTest2;
 * public MyPanel(String data) {
 * btnTest=new JButton("Test");
 * btnTest2=new JButton("Test2");
 * btnTest2.setActionCommand("dosomething");
 * }
 *
 *
 *
 * //This method is automatically invoked when btnTest is clicked. The String inside action is
 * //the label/text set in the button
 *
 * @author sethu
 * @Action("Test") public void dotestwork(ActionEvent e) {
 * //handle Test
 * }
 * <p/>
 * <p/>
 * //This method is invoked when btnTest2 is clicked. The String inside action is
 * //the action command set in the button. This takes precedence over the action
 * //invoked by using just the Label of the button.
 * @Action("dosomething") public void handleSomething(ActionEvent e) {
 * // handle do something
 * }
 * <p/>
 * }
 * public static void main(String[] args) {
 * //create a new frame
 * MyFrame frame=FrameFactory.getNewContainer("TestSetOfContainers", MyFrame.class);
 * frame.setName("frame");
 * <p/>
 * <p/>
 * //get hold of the panel in it
 * MyPanel panel=FrameFactory.getContainer("TestSetOfContainers", MyPanel.class);
 * <p/>
 * // or get hold with the name
 * panel=FrameFactory.getContainer("TestSetOfContainers", "testpanel");
 * <p/>
 * <p/>
 * // do other work...
 * <p/>
 * <p/>
 * //when you are finished dispose all the containers as below in one shot :
 * FrameFactory.dispose("TestSetOfContainers");
 * <p/>
 * //=========>OR dispose individually<==========
 * <p/>
 * FrameFactory.dispose("TestSetOfContainers", MyPanel.class);
 * FrameFactory.dispose("TestSetOfContainers", MyFrame.class);
 * <p/>
 * <p/>
 * //=========>OR dispose individually with name<==========
 * FrameFactory.dispose("TestSetOfContainers", "testpanel");
 * FrameFactory.dispose("TestSetOfContainers", "frame");
 * <p/>
 * }
 * }
 * </pre>
 */
public class FrameFactory {

    /**
     * the data structure used to store all the containers managed by the swing objects framework.
     * <p/>
     * Each container will have child containers. Like in the case of a JFrame with a JPanel with a JTabbedPane with many JPanels in it.
     * In that case you would associate one FrameSet ID with all these components.
     * <p/>
     * This Frameset ID will be the key and all the JFrames and JPanels will be put into a Set under it.
     */
    private static final ConcurrentHashMap<String, Set<Component>> frames = new ConcurrentHashMap<String, Set<Component>>();
    private static final ConcurrentHashMap<Component, String> framesetIDs = new ConcurrentHashMap<Component, String>();

    private static String frameSetIDInUse;

    /**
     * This will instantiate a new container. The class passed in must extend from Container at the minimum. You
     * would generally pass in a JFrame, JPanel, JDialog, JTabbedPane, etc.
     * <p/>
     * This method will automatically map your ActionListeners to be handled by providing the
     * Action annotation above your method. Look at the class level documentation for an example.
     *
     * @param framesetid - This is a String ID provided to a set of Containers.
     * @param clz        Class object of the container you would like to instantiate
     * @param objs       Constructor arguments to pass to the Container's constructor
     * @return the instantiated container, without requiring a type cast.
     * @throws SwingObjectRunException Runtime exception that you don't need to catch.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Component> T getNewContainer(String framesetid, Class<? extends Component> clz, Object... objs)
            throws SwingObjectRunException {
        Component comp = null;
        try {
            frameSetIDInUse = framesetid;
            comp = (Component) ConstructorUtils.invokeConstructor(clz, objs);
            handleWindows(framesetid, clz, comp);
            putContainerInMap(framesetid, comp);
            frameSetIDInUse = null;
        } catch (SwingObjectRunException e) {
            throw e;
        } catch (Exception e) {
            throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
        }
        return (T) comp;
    }


    public static <T extends Component> T getNewContainerIfNotAlreadyExists(String framesetid, Class<T> clz, Object... objs) {
        Component comp = null;
        if ((comp = getContainer(framesetid, clz)) == null) {
            comp = getNewContainer(framesetid, clz, objs);
        }
        return (T) comp;
    }

    private static <T> void handleWindows(final String framesetid, final Class<? extends Component> clz, Component comp) {
        if (comp instanceof Window) {
            TitleIconImage title = clz.getAnnotation(TitleIconImage.class);
            if (title != null) {
                setWindowTitle(comp, title);
                setIconImage(comp, title);
            }

            boolean addWindowCloseListener=true;
            if(comp instanceof JFrame){
                if(((JFrame)comp).getDefaultCloseOperation()==JFrame.DO_NOTHING_ON_CLOSE){
                    addWindowCloseListener=false;
                }
            }
            if(addWindowCloseListener){
                ((Window) comp).addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        FrameFactory.dispose(framesetid, clz);
                        super.windowClosing(e);
                    }
                });
            }
        }
    }

    private static void setIconImage(Component comp, TitleIconImage title) {
        String icon = null;
        if ("DEFAULT".equals(title.iconPath())) {
            try {
                icon = SwingObjProps.getSwingObjProperty("application-icon");
            } catch (MissingResourceException e) {

            }
        } else {
            icon = title.iconPath();
        }
        if (StringUtils.isNotEmpty(icon)) {
            ((Window) comp).setIconImage(new javax.swing.ImageIcon(FrameFactory.class.getResource(icon)).getImage());
        }
    }

    private static void setWindowTitle(Component comp, TitleIconImage title) {
        String compTitle = null;
        if (StringUtils.isNotEmpty(title.value())) {
            compTitle = title.value();

        } else if (StringUtils.isNotEmpty(title.key())) {
            compTitle = SwingObjProps.getApplicationProperty(title.key());
        }
        if (compTitle != null) {
            try {
                MethodUtils.invokeMethod(comp, "setTitle", title.value());
            } catch (Exception e) {

            }
        }
    }

    /**
     * If you would like to store a Container created in your code into the Swing objects framework, call this method.
     * This method will result in the mapping of your action listeners as described in {@link FrameFactory#getNewContainer(String, Class, Object...)}
     *
     * @param framesetid
     * @param comp
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void putContainerInMap(String framesetid, Component comp) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        try {
            Set<Component> setOfComps = frames.get(framesetid);
            if (setOfComps == null) {
                frames.put(framesetid, setOfComps = new HashSet<Component>());
            }
            framesetIDs.put(comp, framesetid);
            setOfComps.add(comp);
            registerActionlistener(comp);
        } catch (Exception e) {
            throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
        }
    }

    /**
     * Gets the container previously stored.
     *
     * @param framesetid This is a String ID provided to a set of Containers.
     * @param clz        Class of the container to be returned
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
     * Gets the container previously stored.
     *
     * @param framesetid    This is a String ID provided to a set of Containers.
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
     * Disposes of all the containers having the same frameset id. For an example, see the class level docs.
     *
     * @param framesetid the framesetid used to storing/creating the container with the methods - {@link FrameFactory#getNewContainer(String, Class, Object...)} or
     *                   {@link FrameFactory#putContainerInMap(String, Component)}
     */
    public static void dispose(String framesetid) {
        Set<Component> comps = frames.remove(framesetid);
        if (comps != null) {
            for (Component comp : comps) {
                framesetIDs.remove(comp);
                disposeComponent(comp);
            }
        }
    }


    public static void dispose(Component component) {
        String framesetId = getFramesetIDForComponent(component);
        if (framesetId != null) {
            Set<Component> components = frames.get(framesetId);
            if (component != null) {
                framesetIDs.remove(component);
                components.remove(component);
                disposeComponent(component);
            }
        }

    }

    private static void disposeComponent(Component comp) {
        try {
            MethodUtils.invokeMethod(comp, "dispose", null);
        } catch (NoSuchMethodException e) {
            comp.setVisible(false);
        } catch (Exception e) {
            throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
        }
    }

    /**
     * Disposes of all the containers having the same frameset id and class. For an example see the class level docs.
     *
     * @param framesetid the framesetid used to storing/creating the container with the methods - {@link FrameFactory#getNewContainer(String, Class, Object...)} or
     *                   {@link FrameFactory#putContainerInMap(String, Component)}
     * @param clz        the class object of the container that needs to be disposed.
     */
    public static void dispose(String framesetid, Class<? extends Component> clz) {
        Set<Component> comps = frames.get(framesetid);
        if (comps != null) {
            for (Iterator<Component> it=comps.iterator();it.hasNext();) {
                Component comp=it.next();
                if (comp.getClass() == clz) {
                    framesetIDs.remove(comp);
                    it.remove();
                    disposeComponent(comp);
                }
            }
        }
    }

    /**
     * Disposes of all the containers having the same frameset id and name. For an example see the class level docs.
     *
     * @param framesetid the framesetid used to storing/creating the container with the methods - {@link FrameFactory#getNewContainer(String, Class, Object...)} or
     *                   {@link FrameFactory#putContainerInMap(String, Component)}
     * @param name       the name of the container that needs to be disposed. Needs to have been set with Component.setName() first before invoking this method.
     */
    public static void dispose(String framesetid, String name) {
        Set<Component> comps = frames.get(framesetid);
        if (comps != null) {
            for (Iterator<Component> it=comps.iterator();it.hasNext();) {
                Component comp=it.next();
                if (name.equals(comp.getName())) {
                    framesetIDs.remove(comp);
                    disposeComponent(comp);
                    it.remove();
                }
            }
        }
    }

    public static void disposeAll(Class<? extends Component> clz){
        for(Set<Component> components : frames.values()){
            Set<Component> disposal=new HashSet<Component>();
            for(Component component : components){
                if(component.getClass().equals(clz)){
                    disposal.add(component);
                }
            }
            for(Component component : disposal){
                dispose(component);
            }
        }
    }

    /**
     * This method will loop through all the fields of the container, and its superclasses and obtain a list of fields
     * that are one of :
     * <ul>
     * <li>{@link AbstractButton}</li>
     * <li>{@link JComboBox}</li>
     * <li>{@link JTextField}</li>
     * <li>A fields that implements the {@link Components} marker interface</li>
     * </ul>
     * <p/>
     * For the first 3, it will call the method "addActionListener()" and provide an instance of the {@link GlobalListener}
     * to it. For the last one, it will recursively call itself (registerActionListener()) again by passing the Components field.
     * <p/>
     * For more information take a look at the class level docs of the {@link GlobalListener}
     *
     * @param comp
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private static void registerActionlistener(final Object comp) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final GlobalListener listener = new GlobalListener(comp);
        ReflectionUtils.iterateOverFields(comp.getClass(), null, new ReflectionCallback<Field>() {
            private Object prop;

            @Override
            public boolean filter(Field field) {
                try {
                    prop = field.get(comp);
                    if (prop == null) {
                        return false;
                    }
                    if (Components.class.isAssignableFrom(prop.getClass())) {
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
                    if(!isGlobalListenerAlreadyAdded()){
                        MethodUtils.invokeMethod(prop, "addActionListener", listener);
                    }
                } catch (Exception e) {
                    throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
                }
            }

            private boolean isGlobalListenerAlreadyAdded() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
                ActionListener[] actionListeners = (ActionListener[]) MethodUtils.invokeMethod(prop, "getActionListeners", new Object[]{});
                boolean isAlreadyAdded = false;
                for (ActionListener actionListener : actionListeners) {
                    if (actionListener instanceof GlobalListener) {
                        isAlreadyAdded = true;
                        break;
                    }
                }
                return isAlreadyAdded;
            }
        });
    }

    private static int frameno;

    /**
     * Use this method to construct a framesetid, if you dont want to use something of your own.
     *
     * @return a unique framesetid String.
     */
    public static synchronized String getNewFrameSetId() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmssSSSSS" + frameno++);
        return sdf.format(new Date());
    }

    public static Iterator<Component> iterate(String framesetid) {
        Set<Component> frameSet = frames.get(framesetid);
        if (frameSet == null) {
            return new HashSet<Component>().iterator();
        } else {
            return frameSet.iterator();
        }
    }

    public static Set<Component> getComponentsForFrameset(String framesetid) {
        Set<Component> frameSet = frames.get(framesetid);
        if (frameSet == null) {
            return new HashSet<Component>();
        } else {
            return frameSet;
        }
    }

    public static String getFramesetIDForComponent(Component component) {
        return framesetIDs.get(component);
    }

    public static String getFrameSetIDInUse() {
        return frameSetIDInUse;
    }

    public static void setFrameSetIDInUse(String frameSetIDInUse) {
        FrameFactory.frameSetIDInUse = frameSetIDInUse;
    }
}
