package com.kttss.counter.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static String convertDate2String(Date date, String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        return  dateFormat.format(date);
    }

    public static Date convertString2Date(String date, String format) throws ParseException {
        DateFormat dateFormat=new SimpleDateFormat(format);
        return  dateFormat.parse(date);
    }


}
