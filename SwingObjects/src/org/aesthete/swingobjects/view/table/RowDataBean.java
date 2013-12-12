package org.aesthete.swingobjects.view.table;

import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.datamap.DataWrapper;
import org.aesthete.swingobjects.datamap.SwingObjData;

import java.lang.reflect.Field;

/**
 * Any object you wish to use to represent a table's row, needs to extend this class. It will force you to declare
 * a hashcode and an equals method. The reason being - automatic datamapping
 *
 *  The swing object framework, will automatically set the rows in the table if you update them in the model. Before updating the table,
 *  it will check if the data already present is different from the data being passed in. Only then it flags that this data should be updated in the
 *  table. To make sure that the correct equality check happens, you need to implement this method.
 *
 *  For more information see the below methods. This is where your equality check will happen.
 *  @see SwingObjData#markChanged(String name, Object value)
 *  and @see {@link DataWrapper#equals(Object)}
 */
public abstract class RowDataBean {
	public abstract int hashCode();
	public abstract boolean equals(Object objToCheck);

    public static int indexOfColumn(Class<? extends RowDataBean> clz, String columnName){
        Field[] fields=clz.getDeclaredFields();
        for(Field field : fields){
            Column column=field.getAnnotation(Column.class);
            if(column.name().equals(columnName)){
                return column.index();
            }
        }
        return -1;
    }
}
