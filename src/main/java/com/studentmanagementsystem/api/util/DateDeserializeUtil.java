package com.studentmanagementsystem.api.util;

import java.io.IOException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializeUtil extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(WebServiceUtil.APP_DATE_FORMAT);

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {

        String date = p.getText();  // e.g. "20-08-2025"
        return LocalDate.parse(date, FORMATTER);  // ✅ Correct
    }
}

