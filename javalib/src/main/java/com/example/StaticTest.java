package com.example;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

/**
 * Created by zeting
 * Date 19/2/12.
 */


public class StaticTest {
    public static void main(String[] args) {
        testLocalData();
        staticFunction();
    }

    private static void testLocalData() {
        System.out.println("时间：" + Instant.now().toString());
        System.out.println("时间：" + Instant.now().toEpochMilli());
        System.out.println("时间：" + System.currentTimeMillis());
// 获取当前日期
        LocalDate now = LocalDate.now();
// 设置日期
        LocalDate localDate = LocalDate.of(2019, 9, 10);
// 获取年
        int year = localDate.getYear();     //结果：2019
        int year1 = localDate.get(ChronoField.YEAR); //结果：2019
// 获取月
        Month month = localDate.getMonth();   // 结果：SEPTEMBER
        int month1 = localDate.get(ChronoField.MONTH_OF_YEAR); //结果：9
// 获取日
        int day = localDate.getDayOfMonth();   //结果：10
        int day1 = localDate.get(ChronoField.DAY_OF_MONTH); // 结果：10
// 获取星期
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();   //结果：TUESDAY
        int dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK); //结果：2

        System.out.println("时间：year:" + year + " - year1:" + year1 + " - month:" +
                month + " - month1:" + month1 + " - day:" + day + " - day1:" + day1 +
                " - dayOfWeek:" + dayOfWeek + " - dayOfWeek1:" + dayOfWeek1);

// 设置时间
        LocalTime localTime = LocalTime.of(13, 51, 10);
//获取小时
        int hour = localTime.getHour();    // 结果：13
        int hour1 = localTime.get(ChronoField.HOUR_OF_DAY); // 结果：13
//获取分
        int minute = localTime.getMinute();  // 结果：51
        int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR); // 结果：51
//获取秒
        int second = localTime.getSecond();   // 结果：10
        int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE); // 结果：10
        System.out.println("时间：hour:" + hour + " - hour1:" + hour1 + " - minute:" +
                minute + " - minute1:" + minute1 + " - second:" + second + " - second1:" + second1
        );

        // 获取当前日期时间
        LocalDateTime localDateTime = LocalDateTime.now();
// 设置日期
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, Month.SEPTEMBER, 10, 14, 46, 56);
        System.out.println("时间：localDateTime1:" + localDateTime1);
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate, localTime);
        System.out.println("时间：localDateTime2:" + localDateTime2);
        LocalDateTime localDateTime3 = localDate.atTime(localTime);
        System.out.println("时间：localDateTime3:" + localDateTime3);
        LocalDateTime localDateTime4 = localTime.atDate(localDate);
        System.out.println("时间：localDateTime4:" + localDateTime4);
// 获取LocalDate
        LocalDate localDate2 = localDateTime.toLocalDate();
        System.out.println("时间：localDate2:" + localDate2);
// 获取LocalTime
        LocalTime localTime2 = localDateTime.toLocalTime();
        System.out.println("时间：localTime2:" + localTime2);


        // 创建Instant对象
        Instant instant = Instant.now();
// 获取秒
        long currentSecond = instant.getEpochSecond();
        System.out.println("时间：currentSecond:" + currentSecond);
// 获取毫秒
        long currentMilli = instant.toEpochMilli();
        System.out.println("时间：currentMilli:" + currentMilli);


        LocalDateTime localDateTime5 = LocalDateTime.of(2019, Month.SEPTEMBER, 10,
                14, 46, 56);
        //增加一年
        localDateTime3 = localDateTime5.plusYears(1);
        localDateTime4 = localDateTime5.plus(1, ChronoUnit.YEARS);
        //减少一个月
        localDateTime3 = localDateTime5.minusMonths(1);
        localDateTime4 = localDateTime5.minus(1, ChronoUnit.MONTHS);

        //修改年为2019
        localDateTime3 = localDateTime5.withYear(2020);
        //修改为2022
        localDateTime4 = localDateTime5.with(ChronoField.YEAR, 2022);
        LocalDate localDate1 = localDate.with(firstDayOfYear());


        localDate = LocalDate.of(2019, 9, 10);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
//自定义格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = localDate.format(dateTimeFormatter);

        localDate1 = LocalDate.parse("20190910", DateTimeFormatter.BASIC_ISO_DATE);
        localDate2 = LocalDate.parse("2019-09-10", DateTimeFormatter.ISO_LOCAL_DATE);
    }

    static StaticTest st = new StaticTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}