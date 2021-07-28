package br.com.biblioteca.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return WEEKEND.contains(dayOfWeek);
    }

}
