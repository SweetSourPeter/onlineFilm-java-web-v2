package edu.hitech.onlinefilm.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

public class DateUtils {
        private  static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private  static final SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyy-MM-dd");

        public static Date getDateByYMDHMS(String dateStr) {
                Date result=null;
                try {
                        result = formatter.parse(dateStr);
                }catch (ParseException e){
                        e.printStackTrace();
                }
                return result;
        }


        public static Date getDateByYMD(String dateStr){

                Date result=null;
                try {
                        result = ymdFormatter.parse(dateStr);
                }catch (ParseException e){
                        e.printStackTrace();
                }
                return result;
        }

        public static String fmtYmdHms(Date showTime) {
                return formatter.format(showTime);

        }
}
