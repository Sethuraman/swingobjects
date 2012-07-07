package test;

import org.aesthete.swingobjects.datamap.SwingObjData;

/**
 * Created with IntelliJ IDEA.
 * User: sethu
 * Date: 07/07/12
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestSwingObjData {

    public static void main(String[] args) {
        SwingObjData objData=new SwingObjData();
        objData.set("key",null);

        System.out.println(objData.getValue("key").asString());
    }
}
