package test;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 25/04/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
import org.aesthete.swingobjects.model.tree.GenericTree;
import org.aesthete.swingobjects.model.tree.GenericTreeNode;
import org.aesthete.swingobjects.model.tree.GenericTreeTraversalOrderEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class GenericTreeTest {

    @Test
    public void testPreOrderTraversal(){
        GenericTreeNode<String> A = new GenericTreeNode<String>("1");
        GenericTreeNode<String> T = A.addChild(new GenericTreeNode<String>("2")).addChild(new GenericTreeNode<String>("3"));
        T.addChild(new GenericTreeNode<String>("4"));
        T.addChild(new GenericTreeNode<String>("5"));
        T.addChild(new GenericTreeNode<String>("6"));
        A.addChild(new GenericTreeNode<String>("7")).addChild(new GenericTreeNode<String>("8"));
        A.addChild(new GenericTreeNode<String>("9")).addChild(new GenericTreeNode<String>("10"));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", new GenericTree<String>(A).build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString());
    }

    @Test
    public void testSameAddedToRootAndAsChild(){
        GenericTreeNode<String> root=new GenericTreeNode<String>("root");
        GenericTreeNode<String> A=new GenericTreeNode<String>("A");
        root.addChild(A);
        GenericTreeNode<String> R = new GenericTreeNode<String>("R");
        A.addChild(new GenericTreeNode<String>("T")).addChild(R);
        A=new GenericTreeNode<String>("A1");
        A.addChild(new GenericTreeNode<String>("T1")).addChild(R);
        root.addChild(R);
        assertEquals("[root, A, T, R, A1, T1, R, R]", new GenericTree<String>(A).build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString());

    }

}
