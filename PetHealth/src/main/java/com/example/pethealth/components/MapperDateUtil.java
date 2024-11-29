package com.example.pethealth.components;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class MapperDateUtil {

    public static String formatTimestamp(Date timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        return dateTime.format(formatter);
    }

    public static boolean checkDate(LocalDate dateNow, LocalDateTime startDate){
        LocalDate localStartDate = startDate.toLocalDate();
        return localStartDate.isEqual(dateNow);
    }

    public static String getDate(LocalDateTime date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.toLocalDate().format(dateFormatter);
    }
    public static String getTime(LocalDateTime time){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.toLocalTime().format(timeFormatter);
    }

}
