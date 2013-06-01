/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
*/

package org.aesthete.swingobjects.model.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericTree<$ModelData> {

    private GenericTreeNode<$ModelData> root;

    public GenericTree() {
        super();
    }

    public GenericTree(GenericTreeNode<$ModelData> root) {
        this.root = root;
    }

    public GenericTreeNode<$ModelData> getRoot() {
        return this.root;
    }

    public void setRoot(GenericTreeNode<$ModelData> root) {
        this.root = root;
    }

    public int getNumberOfNodes() {
        int numberOfNodes = 0;

        if(root != null) {
            numberOfNodes = auxiliaryGetNumberOfNodes(root) + 1; //1 for the root!
        }

        return numberOfNodes;
    }

    private int auxiliaryGetNumberOfNodes(GenericTreeNode<$ModelData> node) {
        int numberOfNodes = node.getNumberOfChildren();

        for(GenericTreeNode<$ModelData> child : node.getChildren()) {
            numberOfNodes += auxiliaryGetNumberOfNodes(child);
        }

        return numberOfNodes;
    }

    public boolean exists($ModelData dataToFind) {
        return (find(dataToFind) != null);
    }

    public GenericTreeNode<$ModelData> find($ModelData dataToFind) {
        GenericTreeNode<$ModelData> returnNode = null;

        if(root != null) {
            returnNode = auxiliaryFind(root, dataToFind);
        }

        return returnNode;
    }

    private GenericTreeNode<$ModelData> auxiliaryFind(GenericTreeNode<$ModelData> currentNode, $ModelData dataToFind) {
        GenericTreeNode<$ModelData> returnNode = null;
        int i = 0;

        if (currentNode.getData().equals(dataToFind)) {
            returnNode = currentNode;
        }

        else if(currentNode.hasChildren()) {
            i = 0;
            while(returnNode == null && i < currentNode.getNumberOfChildren()) {
                returnNode = auxiliaryFind(currentNode.getChildAt(i), dataToFind);
                i++;
            }
        }

        return returnNode;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public List<GenericTreeNode<$ModelData>> build(GenericTreeTraversalOrderEnum traversalOrder) {
        List<GenericTreeNode<$ModelData>> returnList = null;

        if(root != null) {
            returnList = build(root, traversalOrder);
        }

        return returnList;
    }

    public List<GenericTreeNode<$ModelData>> build(GenericTreeNode<$ModelData> node, GenericTreeTraversalOrderEnum traversalOrder) {
        List<GenericTreeNode<$ModelData>> traversalResult = new ArrayList<GenericTreeNode<$ModelData>>();

        if(traversalOrder == GenericTreeTraversalOrderEnum.PRE_ORDER) {
            buildPreOrder(node, traversalResult);
        }

        else if(traversalOrder == GenericTreeTraversalOrderEnum.POST_ORDER) {
            buildPostOrder(node, traversalResult);
        }

        return traversalResult;
    }

    private void buildPreOrder(GenericTreeNode<$ModelData> node, List<GenericTreeNode<$ModelData>> traversalResult) {
        traversalResult.add(node);

        for(GenericTreeNode<$ModelData> child : node.getChildren()) {
            buildPreOrder(child, traversalResult);
        }
    }

    private void buildPostOrder(GenericTreeNode<$ModelData> node, List<GenericTreeNode<$ModelData>> traversalResult) {
        for(GenericTreeNode<$ModelData> child : node.getChildren()) {
            buildPostOrder(child, traversalResult);
        }

        traversalResult.add(node);
    }

    public Map<GenericTreeNode<$ModelData>, Integer> buildWithDepth(GenericTreeTraversalOrderEnum traversalOrder) {
        Map<GenericTreeNode<$ModelData>, Integer> returnMap = null;

        if(root != null) {
            returnMap = buildWithDepth(root, traversalOrder);
        }

        return returnMap;
    }

    public Map<GenericTreeNode<$ModelData>, Integer> buildWithDepth(GenericTreeNode<$ModelData> node, GenericTreeTraversalOrderEnum traversalOrder) {
        Map<GenericTreeNode<$ModelData>, Integer> traversalResult = new LinkedHashMap<GenericTreeNode<$ModelData>, Integer>();

        if(traversalOrder == GenericTreeTraversalOrderEnum.PRE_ORDER) {
            buildPreOrderWithDepth(node, traversalResult, 0);
        }

        else if(traversalOrder == GenericTreeTraversalOrderEnum.POST_ORDER) {
            buildPostOrderWithDepth(node, traversalResult, 0);
        }

        return traversalResult;
    }

    private void buildPreOrderWithDepth(GenericTreeNode<$ModelData> node, Map<GenericTreeNode<$ModelData>, Integer> traversalResult, int depth) {
        traversalResult.put(node, depth);

        for(GenericTreeNode<$ModelData> child : node.getChildren()) {
            buildPreOrderWithDepth(child, traversalResult, depth + 1);
        }
    }

    private void buildPostOrderWithDepth(GenericTreeNode<$ModelData> node, Map<GenericTreeNode<$ModelData>, Integer> traversalResult, int depth) {
        for(GenericTreeNode<$ModelData> child : node.getChildren()) {
            buildPostOrderWithDepth(child, traversalResult, depth + 1);
        }

        traversalResult.put(node, depth);
    }

    public String toString() {
        /*
        We're going to assume a pre-order traversal by default
         */

        String stringRepresentation = "";

        if(root != null) {
            stringRepresentation = build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString();

        }

        return stringRepresentation;
    }

    public String toStringWithDepth() {
        /*
        We're going to assume a pre-order traversal by default
         */

        String stringRepresentation = "";

        if(root != null) {
            stringRepresentation = buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER).toString();
        }

        return stringRepresentation;
    }
}
