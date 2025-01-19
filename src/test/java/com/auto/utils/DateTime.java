package com.auto.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTime {

    public static String convertDateWithFormat(String zone, String format) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        ZoneId zoneIdLosAngeles = ZoneId.of(zone);
        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
        return zdtNowLosAngeles.format(inputFormat);
    }

    public static long convertServerDateTimeToMilliseconds(Date d) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = formatter.parse(formatter.format(d));
        return date.getTime();
    }

    public static String getUTCDateTime(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(d);
    }

    public static long getDaysBetween2Dates(String stringDate1, String stringDate2, String pattern) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat(pattern);
        Date date1 = myFormat.parse(stringDate1);
        Date date2 = myFormat.parse(stringDate2);
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String convertEpochTimeStampToDateTime(String time, String GMT) {
        long seconds = Long.parseLong(time);
        Date d = new Date(seconds * 1000);
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf1.setTimeZone(TimeZone.getTimeZone(GMT));
        return sdf1.format(d);
    }
}
