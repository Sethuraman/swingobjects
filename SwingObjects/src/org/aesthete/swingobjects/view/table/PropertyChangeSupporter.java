package org.aesthete.swingobjects.view.table;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 09/05/13
 * Time: 6:28 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyChangeSupporter {

    private SwingPropertyChangeSupport propertyChangeSupport;
    private List<PropertyChangeListener> propertyChangeListeners;

    protected PropertyChangeSupporter() {
        this.propertyChangeSupport = new SwingPropertyChangeSupport(this);
        propertyChangeListeners=new LinkedList<PropertyChangeListener>();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeListeners.add(listener);
    }

    public SwingPropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
}
