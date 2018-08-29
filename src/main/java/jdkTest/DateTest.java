package jdkTest;

import java.time.*;

/**
 * zhangjunyang 2018/1/7 15:48
 */
public class DateTest {
    public static void main(String[] args) {
        // Clock类使用时区来返回当前的纳秒时间和日期
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        // LocalDate仅仅包含ISO-8601日历系统中的日期部分；LocalTime则仅仅包含该日历系统中的时间部分
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println(date);
        System.out.println(time);

        LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );

        Duration duration = Duration.between( from, to );
        System.out.println( "Duration in days: " + duration.toDays() );
        System.out.println( "Duration in hours: " + duration.toHours() );
    }
}
