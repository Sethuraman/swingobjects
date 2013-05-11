package org.aesthete.swingobjects.view.table;

import org.aesthete.swingobjects.model.tree.GenericTree;
import org.aesthete.swingobjects.model.tree.GenericTreeNode;
import org.aesthete.swingobjects.model.tree.GenericTreeTraversalOrderEnum;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 24/04/13
 * Time: 8:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwingObjTreeTable<$ModelData extends PropertyChangeSupporter> extends JXTreeTable{
    private $ModelData prototypeData;
    private SwingObjTreeTableModel<$ModelData> model;

    public SwingObjTreeTable(Class<$ModelData> classOfData, GenericTreeNode<$ModelData> root) {
        super(new SwingObjTreeTableModel<$ModelData>(classOfData));
        model= (SwingObjTreeTableModel<$ModelData>) getTreeTableModel();
        initTable(root);
    }

    public SwingObjTreeTable(SwingObjTreeTableModel<$ModelData> model, GenericTreeNode<$ModelData> root){
        super(model);
        initTable(root);
    }

    private void initTable(GenericTreeNode<$ModelData> root) {
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        this.setVisibleColumnCount(model.getColumnCount());
        this.addHighlighter(HighlighterFactory.createAlternateStriping());

        model.setData(root);
        for(GenericTreeNode<$ModelData> node : new GenericTree<$ModelData>(root).build(root, GenericTreeTraversalOrderEnum.PRE_ORDER)){
            node.setTreeModel(model);
        }
    }

    public void makeColumnsIntoComboBox(Object[] values,int... cols){
        for(int col : cols){
            getColumnExt(col).setCellRenderer((new DefaultTableRenderer(new ComboBoxProvider(new DefaultComboBoxModel(values)))));
            getColumnExt(col).setCellEditor(new ComboBoxEditor(new DefaultComboBoxModel(values)));
        }
    }

    public void makeColumnsIntoADate(int... cols) {
        for(int col : cols) {
            new TableCellDatePicker(this, col);
            DefaultTableCellRenderer renderer =new DefaultTableCellRenderer();
            renderer.setToolTipText("<html>Right click to launch the Calendar<br/>" +
                    "Else enter date in dd/mm/yyyy format</html>");
            getColumnExt(col).setCellRenderer(renderer);
            this.getColumnModel().getColumn(col).setCellRenderer(renderer);
        }
    }

    /**
     * This method has serious performance issues when huge set of rows (tested with 3000)
     * is added. This method makes use of the same things described here:
 * <a href="http://home.java.net/node/688840">http://home.java.net/node/688840</a>
     * @param cols
     */
    public void makeColumnsIntoTextArea(int... cols){
        for(int col : cols){
            getColumnExt(col).setCellRenderer((new DefaultTableRenderer(new TextAreaProvider())));
        }
//		setRowHeightEnabled(true);
        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                for (int row = 0; row < getRowCount(); row++) {
                    int rowHeight = 0;
                    for (int column = 0; column < getColumnCount(); column++) {
                        Component comp = prepareRenderer(
                                getCellRenderer(row, column), row, column);
                        rowHeight = Math.max(rowHeight,
                                comp.getPreferredSize().height);
                    }
                    setRowHeight(row, rowHeight);
                }
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    public int getSelectedRowAfterModelConversion(){
        int selectedRow = getSelectedRow();
        if(selectedRow>-1){
            return convertRowIndexToModel(selectedRow);
        }
        return -1;
    }

    public GenericTreeNode<$ModelData> getNodeForIndex(int index){
        if(index<0){
            return null;
        }
        return (GenericTreeNode<$ModelData>) getPathForRow(index).getLastPathComponent();
    }

    public void addRow(GenericTreeNode<$ModelData> row, GenericTreeNode<$ModelData> parent){
        model.insertNodeInto(row, parent, parent.getNumberOfChildren());
    }

    public void addRow(GenericTreeNode<$ModelData> row, GenericTreeNode<$ModelData> parent, int index){
        model.insertNodeInto(row, parent, index);
    }

    public void removeRow(GenericTreeNode<$ModelData> row){
        model.removeNodeFromParent(row);
    }

    public void setData(GenericTreeNode<$ModelData> root){
        model.setData(root);
    }

    public GenericTreeNode<$ModelData> getRoot(){
        return model.getRoot();
    }

    public void reload(){
        model.setData(model.getRoot());
    }
}
