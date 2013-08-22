package org.aesthete.swingobjects.util;

import org.aesthete.swingobjects.view.table.PropertyChangeSupporter;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 22/08/13
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyChangeListenerTreeLogDumper {

    public static final Logger LOGGER=Logger.getLogger(PropertyChangeListenerTreeLogDumper.class);


    private Set<PropertyChangeSupporter> set=new HashSet<PropertyChangeSupporter>();

    public void dumpPropertyChangeListenerTreeToLogs(PropertyChangeSupporter supporter,
                                                            Class<? extends PropertyChangeSupporter> filterByClass,
                                                            String propertyName){
        if(!set.add(supporter)){
            return;
        }

        PropertyChangeListener[] propertyChangeListeners=supporter.getPropertyChangeSupport().getPropertyChangeListeners(propertyName);
        for(PropertyChangeListener propertyChangeListener : propertyChangeListeners){

            LOGGER.debug(propertyChangeListener.toString());

            if(filterByClass.isAssignableFrom(propertyChangeListener.getClass())){
                dumpPropertyChangeListenerTreeToLogs((PropertyChangeSupporter) propertyChangeListener, filterByClass, propertyName);
            }
        }
    }
}
