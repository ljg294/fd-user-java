package com.fitdine.user.common.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@JsonComponent
public class LocalDateJsonConverter {

    private LocalDateJsonConverter() {

    }

    public static class Serializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(convertDate(localDate));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return convertDate(jsonParser.getText());
        }
    }

    private static String convertDate(LocalDate localDate) {
        if (Objects.isNull(localDate))
            return null;
        return localDate.format(DefaultDateTimeFormat.DATE_FORMAT);
    }

    private static LocalDate convertDate(String date) {
        if (Objects.isNull(date))
            return null;
        return LocalDate.parse(date, DefaultDateTimeFormat.DATE_FORMAT);
    }

}
