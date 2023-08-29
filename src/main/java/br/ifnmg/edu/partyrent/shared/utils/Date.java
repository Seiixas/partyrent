package br.ifnmg.edu.partyrent.shared.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class Date {
    public static long calculateDaysDifference(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        Duration duration = Duration.between(dateTime1, dateTime2);
        long secondsDifference = duration.getSeconds();
        return (secondsDifference / (60 * 60 * 24)) + 1;
    }
}
