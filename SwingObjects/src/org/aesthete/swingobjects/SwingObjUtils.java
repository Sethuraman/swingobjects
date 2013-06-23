package org.aesthete.swingobjects;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 13/06/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwingObjUtils {

    public static boolean isWindows(){
        String os=System.getProperty("os.name").toLowerCase();
        return os.indexOf("windows")!=-1;
    }

    public static boolean isLinux() {
        String os=System.getProperty("os.name").toLowerCase();
        return os.indexOf("linux")!=-1;
    }
}
