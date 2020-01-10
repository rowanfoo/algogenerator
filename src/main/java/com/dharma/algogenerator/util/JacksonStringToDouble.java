package com.dharma.algogenerator.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JacksonStringToDouble extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        System.out.println("--------------Deserialize----------------------");
        String floatString = parser.getText();
        System.out.println("--------------Deserialize----------value------------" + floatString);

        int percent = floatString.indexOf("%");
        String value = "";
        if (percent == -1) value = floatString;
        else value = floatString.substring(0, percent);
        System.out.println("--------------Deserialize----------DOUBLE ------------" + value);

        return Double.valueOf(value);
    }

}

