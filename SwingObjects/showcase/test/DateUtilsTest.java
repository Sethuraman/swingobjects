package test;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.util.DateUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 19/05/13
 * Time: 8:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtilsTest {

    public static void main(String[] args) throws SwingObjectException {
        SwingObjectsInit.init("swingobjects", "application");
        System.out.println(DateUtils.getDateFromFormatOfString("05/12/2013"));
    }
}
