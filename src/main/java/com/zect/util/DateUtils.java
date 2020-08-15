package com.zect.util;

import com.zect.domain.States;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * string转date
     * @param strTime
     * @return
     */
    public static Date strToDate(String strTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);
        LocalDateTime localDateTime = LocalDateTime.parse(strTime, dateTimeFormatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static void main(String[] args)throws Exception {
        System.out.println(States.getStateById("1"));
    }
}
