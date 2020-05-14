package com.tom.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("String to date converter fail ...");
        }
    }
}
