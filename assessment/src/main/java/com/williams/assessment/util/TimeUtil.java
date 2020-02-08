package com.williams.assessment.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    public static String getIsoTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return timestamp.toLocalDateTime().format(formatter);
    }

    public static String getIsoTime(Date date) {
        if (date == null) {
            return null;
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return getIsoTime(timestamp);
    }

    public static Timestamp getIsoTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    @SuppressWarnings("WeakerAccess")
    public static Timestamp futureMonths(int months) {
        LocalDateTime futureTime = LocalDateTime.now().plusMonths(months);
        return new Timestamp(futureTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public static Timestamp pastTime(int seconds) {
        return futureTime(-1 * seconds);
    }

    public static Timestamp pastMonths(int months) {
        return futureMonths(-1 * months);
    }

    public static LocalDateTime localDateTimefromEpochMilli(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
    }

    public static Timestamp now() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static Timestamp futureTime(int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, seconds);
        Date expiryDate = cal.getTime();
        return new Timestamp(expiryDate.getTime());
    }

    public static Integer timestampToUnixSeconds() {
        Date date = new Date();
        return (int) (date.getTime() / 1000);
    }

    public static String getGMTTime() {
        final Date currentTime = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(currentTime);
    }

    public static Date convertStringToDate(String inDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh24:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(inDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String fromISO8601UTC(String dateString) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(timeZone);
        try {
            return String.valueOf(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
