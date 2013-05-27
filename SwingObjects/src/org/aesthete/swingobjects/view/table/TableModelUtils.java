package org.aesthete.swingobjects.view.table;

import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.util.ReflectionCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 26/05/13
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class TableModelUtils {

    public static int getColumnForFieldName(Class<?> clz, final String fieldName){
        for(Field f : clz.getDeclaredFields()) {
            f.setAccessible(true);
            if(fieldName.equals(f.getName()) && f.getAnnotation(Column.class)!=null){
                return f.getAnnotation(Column.class).index();
            }
        }
        return -1;
    }

}
