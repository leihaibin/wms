package com._520it.wms.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //获取指定时间那一天的最后一秒
    //2017-08-22 00:00:00 ---> 2017-08-22 23:59:59
   public static Date getEndDate(Date now){
       Calendar c=Calendar.getInstance();
       c.setTime(now);
       c.set(Calendar.HOUR_OF_DAY,23);
       c.set(Calendar.MINUTE,59);
       c.set(Calendar.SECOND,59);
       return c.getTime();
   }

}
