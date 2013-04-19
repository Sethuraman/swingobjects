package org.aesthete.swingobjects.util;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 29/09/12
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    private static final Map<Pattern,String> patterns=getPatterns();

    private static Map<Pattern,String> getPatterns() {
        String[] acceptabledateformatses = StringUtils.split(SwingObjProps.getApplicationProperty("acceptabledateformats"),"~");
        Map<Pattern,String> localPatterns=new HashMap<Pattern, String>();
        for(int i=0;i<acceptabledateformatses.length;i++){
            String[] split = acceptabledateformatses[i].trim().split("#");
            localPatterns.put(Pattern.compile(split[0].trim()),split[1].trim());
        }
        return localPatterns;
    }


    public static Date getDateFromFormatOfString(String date) throws ParseException {
        for(Map.Entry<Pattern,String> entry : patterns.entrySet()){
            if(entry.getKey().matcher(date).matches()){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(entry.getValue());
                simpleDateFormat.setLenient(false);
                return simpleDateFormat.parse(date);
            }
        }
        throw new ParseException(date, 0);
    }

    public static void getCurrentDateFromGoogle(){
        try{
            URLConnection urlConnection=new URL("http://www.google.com").openConnection();
            String date = urlConnection.getHeaderField("Date");
            System.out.println(date);

        }catch (Exception e){
            throw new SwingObjectRunException(e,DateUtils.class);
        }

    }

    public static void main(String[] args) throws SwingObjectException {
        SwingObjectsInit.init("swingobjects", "application");
        DateUtils.getCurrentDateFromGoogle();
    }

}
