package com.studentmanagementsystem.api.util;

import java.io.IOException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializeUtil extends JsonSerializer<LocalDate>  {

	 private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(WebServiceUtil.APP_DATE_FORMAT);

	    @Override
	    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
	        gen.writeString(value.format(FORMATTER));
	    }	
	}
