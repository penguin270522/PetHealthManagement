package com.example.pethealth.utils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConverDateTime {
    public static String convertDatetime(String date) {
        // Chỉ định định dạng cho chuỗi đầu vào
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);

        // Định dạng để hiển thị ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateTime.format(outputFormatter);

        return formattedDate;
    }

    public static String convertDateTimeSchedule(String date){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);

        // Định dạng để hiển thị ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateTime.format(outputFormatter);

        return formattedDate;
    }
}
