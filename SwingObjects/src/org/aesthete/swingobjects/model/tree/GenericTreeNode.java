/*
 Copyright 2010 Visin Suresh Paliath
 Distributed under the BSD license
*/

package org.aesthete.swingobjects.model.tree;

import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.table.PropertyChangeSupporter;
import org.aesthete.swingobjects.view.table.SwingObjTreeTableModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericTreeNode<$TreeData> implements PropertyChangeListener{

    private $TreeData data;
    private List<GenericTreeNode<$TreeData>> children;
    private GenericTreeNode<$TreeData> parent;
    private boolean isEditable;
    private boolean isEmptyRootNode;
    private SwingObjTreeTableModel<$TreeData> treeModel;


    public GenericTreeNode() {
        super();
        children = new LinkedList<GenericTreeNode<$TreeData>>();
    }

    public GenericTreeNode($TreeData data) {
        this();
        setData(data);
        if(data instanceof PropertyChangeSupporter){
            ((PropertyChangeSupporter)data).addPropertyChangeListener(this);
        }
    }

    public GenericTreeNode($TreeData data, boolean isEmptyRootNode) {
        this();
        setData(data);
        setEmptyRootNode(isEmptyRootNode);
        if(data instanceof PropertyChangeSupporter){
            ((PropertyChangeSupporter)data).addPropertyChangeListener(this);
        }
    }

    public GenericTreeNode<$TreeData> getParent() {
        return this.parent;
    }

    public List<GenericTreeNode<$TreeData>> getChildren() {
        return this.children;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasChildren() {
        return (getNumberOfChildren() > 0);
    }

    public GenericTreeNode<$TreeData> getChildAt(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }

    public int getChildIndex(GenericTreeNode<$TreeData> child){
        return children.indexOf(child);
    }

    public $TreeData getData() {
        return this.data;
    }

    public String toString() {
        return getData().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj == null) {
           return false;
        }
        if (getClass() != obj.getClass()) {
           return false;
        }
        GenericTreeNode<?> other = (GenericTreeNode<?>) obj;
        if (data == null) {
           if (other.data != null) {
              return false;
           }
        } else if (!data.equals(other.data)) {
           return false;
        }
        return true;
    }

    /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
       final int prime = 31;
       int result = 1;
       result = prime * result + ((data == null) ? 0 : data.hashCode());
       return result;
    }

    public String toStringVerbose() {
        String stringRepresentation = getData().toString() + ":[";

        for (GenericTreeNode<$TreeData> node : getChildren()) {
            stringRepresentation += node.getData().toString() + ", ";
        }

        //Pattern.DOTALL causes ^ and $ to match. Otherwise it won't. It's retarded.
        Pattern pattern = Pattern.compile(", $", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(stringRepresentation);

        stringRepresentation = matcher.replaceFirst("");
        stringRepresentation += "]";

        return stringRepresentation;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isEmptyRootNode() {
        return isEmptyRootNode;
    }

    public void setEmptyRootNode(boolean emptyRootNode) {
        isEmptyRootNode = emptyRootNode;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(treeModel!=null){
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.updateNode(GenericTreeNode.this);
                }
            });
        }
    }


    public void removeChildAt(final int index) throws IndexOutOfBoundsException {
        final GenericTreeNode<$TreeData> removedNode = children.remove(index);
        if(treeModel!=null){
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.removeNodeFromParentWithoutUpdatingUnderlyingStructure(removedNode, index);
                }
            });
        }
    }

    public void removeChildren() {
        this.children = new ArrayList<GenericTreeNode<$TreeData>>();
        if(treeModel!=null){
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.updateTreeStructure(GenericTreeNode.this);
                }
            });
        }
    }

    public GenericTreeNode<$TreeData> addChild(final GenericTreeNode<$TreeData> child) {
        child.parent = this;
        children.add(child);
        if(treeModel!=null){
            child.setTreeModel(treeModel);
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.insertNodeIntoWithoutUpdatingTheUnderlyingStructure(child, GenericTreeNode.this, getNumberOfChildren()-1);
                }
            });
        }
        return child;
    }

    public GenericTreeNode<$TreeData> addChildAt(final int index,final GenericTreeNode<$TreeData> child) throws IndexOutOfBoundsException {
        child.parent = this;
        children.add(index, child);
        if(treeModel!=null){
            child.setTreeModel(treeModel);
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.insertNodeIntoWithoutUpdatingTheUnderlyingStructure(child, GenericTreeNode.this, index);
                }
            });
        }
        return child;
    }

    public void setChildren(List<GenericTreeNode<$TreeData>> children) {
        for(GenericTreeNode<$TreeData> child : children) {
            child.parent = this;
            child.setTreeModel(treeModel);
        }
        if(treeModel!=null){
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.updateTreeStructure(GenericTreeNode.this);
                }
            });
        }
        this.children = children;
    }

    public void setData($TreeData data) {
        this.data = data;
        if(treeModel!=null){
            CommonUI.runInEDT(new Runnable() {
                @Override
                public void run() {
                    treeModel.updateNode(GenericTreeNode.this);
                }
            });
        }
    }

    public void setTreeModel(SwingObjTreeTableModel<$TreeData> treeModel) {
        this.treeModel = treeModel;
    }
}

