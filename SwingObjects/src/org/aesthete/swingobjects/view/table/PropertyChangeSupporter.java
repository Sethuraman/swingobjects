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
public abstract class PropertyChangeSupporter extends RowDataBean{

    private SwingPropertyChangeSupport propertyChangeSupport;

    protected PropertyChangeSupporter() {
        this.propertyChangeSupport = new SwingPropertyChangeSupport(this, true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener, String... properties){
        for(String propertyName : properties){
            propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public SwingPropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(SwingPropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;
    }
}
