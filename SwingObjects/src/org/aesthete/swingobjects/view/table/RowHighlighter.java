package org.aesthete.swingobjects.view.table;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 27/06/13
 * Time: 6:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class RowHighlighter extends ColorHighlighter {

    private Map<Integer,String> rows;
    private SwingObjTable<?> table;

    public RowHighlighter(SwingObjTable<?> table, Color cellBackground, Color cellForeground) {
        super(cellBackground, cellForeground);
        this.table = table;
        rows=new HashMap<Integer, String>();
        setHighlightPredicate(new HighlightPredicate() {
            @Override
            public boolean isHighlighted(Component component, ComponentAdapter componentAdapter) {
                return rows.containsKey(componentAdapter.row);
            }
        });
        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row=RowHighlighter.this.table.rowAtPoint(e.getPoint());
                if(rows.containsKey(row)){
                    RowHighlighter.this.table.setToolTipText(rows.get(row));
                }
            }
        });
        table.addHighlighter(this);
    }

    public void addRowToHighlight(int row, String tooltip){
        rows.put(row, tooltip);

    }

    public void clearAllRows(){
        rows.clear();
    }


}
