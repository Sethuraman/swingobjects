package org.aesthete.swingobjects.view.table;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.annotations.AnnotationConstants;
import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.model.tree.GenericTreeNode;
import org.aesthete.swingobjects.util.ReflectionCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreePath;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 24/04/13
 * Time: 8:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwingObjTreeTableModel<$ModelData> extends AbstractTreeTableModel implements PropertyChangeListener{

    private HashMap<Integer, ColumnInfo> columns;
    private Class<$ModelData> t;
    private GenericTreeNode<$ModelData> root;
    private boolean isTableEditable;

    public SwingObjTreeTableModel(Class<$ModelData> t){
        this.t=t;
        columns=new HashMap<Integer, ColumnInfo>();
        init();
    }

    private void init() {
        ReflectionUtils.iterateOverFields(t, null, new ReflectionCallback<Field>() {
            private Column column;

            @Override
            public boolean filter(Field field) {
                column = field.getAnnotation(Column.class);
                return column != null;
            }

            @Override
            public void consume(Field field) {
                ColumnInfo info = new ColumnInfo();
                info.setFieldName(field.getName());
                info.setIndex(column.index());
                if (AnnotationConstants.COLUMN_NAME_USE_KEY.name().equals(column.name())) {
                    info.setHeading(SwingObjProps.getApplicationProperty(column.key()));
                } else {
                    info.setHeading(column.name());
                }
                info.setEditable(column.editable());
                if (column.editable()) {
                    isTableEditable = true;
                }
                if (column.type() == Class.class) {
                    info.setType(ClassUtils.primitiveToWrapper(field.getType()));
                } else {
                    info.setType(column.type());
                }
                columns.put(column.index(), info);
            }
        });
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return columns.get(column).getType();
    }

    @Override
    public String getColumnName(int col) {
        return columns.get(col).getHeading();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        throwExceptionIfNodeDoesntBelongToTree(node);
        GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)node;
        return treeNode.isEditable() && columns.get(column).isEditable();
    }

    @Override
    public Object getChild(Object parent, int index) {
        if(getChildCount(parent)>index){
            GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)parent;
            return treeNode.getChildAt(index);
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)parent;
        return treeNode.getNumberOfChildren();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)parent;
        return treeNode.getChildIndex((GenericTreeNode<$ModelData>) child);
    }

    private void throwExceptionIfNodeDoesntBelongToTree(Object node) {
        boolean result = false;

        if (node instanceof GenericTreeNode) {
            GenericTreeNode<? extends PropertyChangeSupporter> ttn = (GenericTreeNode<? extends PropertyChangeSupporter>) node;

            while (!result && ttn != null) {
                result = ttn == root;

                ttn = ttn.getParent();
            }
        }

        if(!result){
            throw new SwingObjectRunException(ErrorSeverity.SEVERE, "Node does not belong to the tree!", this);
        }

    }

    @Override
    public GenericTreeNode<$ModelData> getRoot() {
        return root;
    }

    /**
     * Gets the value for the {@code node} at {@code column}.
     *
     * @impl delegates to {@code TreeTableNode.getValueAt(int)}
     * @param node
     *            the node whose value is to be queried
     * @param column
     *            the column whose value is to be queried
     * @return the value Object at the specified cell
     * @throws IllegalArgumentException
     *             if {@code node} is not an instance of {@code TreeTableNode}
     *             or is not managed by this model, or {@code column} is not a
     *             valid column index
     */
    @Override
    public Object getValueAt(Object node, int column) {
        throwExceptionIfNodeDoesntBelongToTree(node);

        if (column < 0 || column >= getColumnCount()) {
            throw new SwingObjectRunException(ErrorSeverity.SEVERE, "column must be a valid index", this);
        }

        try{
            GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)node;
            $ModelData t=treeNode.getData();
            return PropertyUtils.getProperty(t, columns.get(column).getFieldName());
        }catch (Exception e){
            throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object value, Object node, int column) {
        throwExceptionIfNodeDoesntBelongToTree(node);

        if (column < 0 || column >= getColumnCount()) {
            throw new IllegalArgumentException("column must be a valid index");
        }


        if (column < getColumnCount()) {

            try{
                GenericTreeNode<$ModelData> treeNode=(GenericTreeNode<$ModelData>)node;
                $ModelData t=treeNode.getData();
                PropertyUtils.setProperty(t, columns.get(column).getFieldName(), value);
                modelSupport.firePathChanged(new TreePath(getPathToRoot(treeNode)));
            }catch (Exception e){
                throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, this);
            }
        }
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf(Object node) {
       throwExceptionIfNodeDoesntBelongToTree(node);

        return !((GenericTreeNode<$ModelData>) node).hasChildren();
    }

    /**
     * Gets the path from the root to the specified node.
     *
     * @param aNode
     *            the node to query
     * @return an array of {@code TreeTableNode}s, where
     *         {@code arr[0].equals(getRoot())} and
     *         {@code arr[arr.length - 1].equals(aNode)}, or an empty array if
     *         the node is not found.
     * @throws NullPointerException
     *             if {@code aNode} is {@code null}
     */
    public GenericTreeNode[] getPathToRoot(GenericTreeNode<$ModelData> aNode) {
        List<GenericTreeNode<$ModelData>> path = new ArrayList<GenericTreeNode<$ModelData>>();
        GenericTreeNode<$ModelData> node = aNode;

        while (node != root) {
            path.add(0, node);

            node = node.getParent();
        }

        if (node == root) {
            path.add(0, node);
        }

        return path.toArray(new GenericTreeNode[0]);
    }

    /**
     * Invoked this to insert newChild at location index in parents children.
     * This will then message nodesWereInserted to create the appropriate event.
     * This is the preferred way to add children as it will create the
     * appropriate event.
     */
    public void insertNodeInto(GenericTreeNode<$ModelData> newChild, GenericTreeNode<$ModelData> parent, int index) {
        if(parent.getTreeModel()!=null){
            if(parent.getTreeModel()!=this){
                throw new SwingObjectRunException(ErrorSeverity.SEVERE,
                        "The parent node has got a different tree model set into it and the child being added to another tree model", this.getClass());
            }
        }

        parent.addChildAt(index, newChild);

        if(parent.getTreeModel()==null){
            modelSupport.fireChildAdded(new TreePath(getPathToRoot(parent)), index, newChild);
        }
    }

    public void insertNodeIntoWithoutUpdatingTheUnderlyingStructure(GenericTreeNode<$ModelData> newChild,
                                                                    GenericTreeNode<$ModelData> parent, int index){
        modelSupport.fireChildAdded(new TreePath(getPathToRoot(parent)), index, newChild);
    }

    /**
     * This method will remove the node from the parent and also update the underlying
     * tree structure
     */
    public void removeNodeFromParent(GenericTreeNode<$ModelData> node) {
        if(node.getTreeModel()!=null){
            if(node.getTreeModel()!=this){
                throw new SwingObjectRunException(ErrorSeverity.SEVERE,
                        "The node has got a different tree model set into it and removeNodeFromParent is being called on another tree model", this.getClass());
            }
        }

        GenericTreeNode<$ModelData> parent = (GenericTreeNode<$ModelData>) node.getParent();

        if (parent == null) {
            throw new IllegalArgumentException("node does not have a parent.");
        }

        int index = parent.getChildIndex(node);
        parent.removeChildAt(index);

        if(node.getTreeModel()==null){
            modelSupport.fireChildRemoved(new TreePath(getPathToRoot(parent)), index, node);
        }
    }

    public void removeNodeFromParentWithoutUpdatingUnderlyingStructure(GenericTreeNode<$ModelData> node, int indexOfThisNodeInItsParent){
        GenericTreeNode<$ModelData> parent = (GenericTreeNode<$ModelData>) node.getParent();
        if (parent == null) {
            throw new IllegalArgumentException("node does not have a parent.");
        }
        modelSupport.fireChildRemoved(new TreePath(getPathToRoot(parent)), indexOfThisNodeInItsParent, node);
    }

    public void updateTreeStructure(GenericTreeNode<$ModelData> node){
        modelSupport.fireTreeStructureChanged(new TreePath(getPathToRoot(node)));
    }

    public void updateNode(GenericTreeNode<$ModelData> node){
        modelSupport.firePathChanged(new TreePath(getPathToRoot(node)));
    }

    public void setData(GenericTreeNode<$ModelData> root){
        this.root=root;
        modelSupport.fireNewRoot();
        root.setTreeModel(this);
    }

    public HashMap<Integer, ColumnInfo> getColumns() {
        return columns;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateNode((GenericTreeNode<$ModelData>) evt.getNewValue());
    }
}
