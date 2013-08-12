package org.aesthete.swingobjects.util;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 28/07/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwingObjPropertyChangeSupport extends PropertyChangeSupport{
    /**
     * Constructs a SwingObjPropertyChangeSupport object.
     *
     * @param sourceBean  The bean to be given as the source for any
     *        events.
     * @throws NullPointerException if {@code sourceBean} is 
     *         {@code null}
     */
    public SwingObjPropertyChangeSupport(Object sourceBean) {
        this(sourceBean, false);
    }

    /**
     * Constructs a SwingObjPropertyChangeSupport object.
     *
     * @param sourceBean the bean to be given as the source for any events
     * @param notifyOnEDT whether to notify listeners on the <i>Event
     *        Dispatch Thread</i> only
     *
     * @throws NullPointerException if {@code sourceBean} is 
     *         {@code null}
     * @since 1.6
     */
    public SwingObjPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT) {
        super(sourceBean);
        this.notifyOnEDT = notifyOnEDT;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If {@link #isNotifyOnEDT} is {@code true} and called off the
     * <i>Event Dispatch Thread</i> this implementation uses 
     * {@code SwingUtilities.invokeLater} to send out the notification
     * on the <i>Event Dispatch Thread</i>. This ensures  listeners
     * are only ever notified on the <i>Event Dispatch Thread</i>.
     *
     * @throws NullPointerException if {@code evt} is 
     *         {@code null}
     * @since 1.6
     */
    public void firePropertyChange(final PropertyChangeEvent evt) {
        if (evt == null) {
            throw new NullPointerException();
        }
        if(evt.getNewValue()==evt.getOldValue()){
            return;
        }

        if (! isNotifyOnEDT()
                || SwingUtilities.isEventDispatchThread()) {
            super.firePropertyChange(evt);
        } else {
            SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            firePropertyChange(evt);
                        }
                    });
        }
    }

    /**
     * Returns {@code notifyOnEDT} property.
     *
     * @return {@code notifyOnEDT} property
     * @see #SwingObjPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT)
     * @since 1.6
     */
    public final boolean isNotifyOnEDT() {
        return notifyOnEDT;
    }

    // Serialization version ID
    static final long serialVersionUID = 7162625831330845068L;

    /**
     * whether to notify listeners on EDT
     *
     * @serial
     * @since 1.6
     */
    private final boolean notifyOnEDT;
}
