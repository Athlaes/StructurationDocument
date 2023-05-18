package fr.ul.miage.sd.deserializer;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import fr.ul.miage.sd.response.ArtistResponseBody;

/**
 * NullDeserializer
 */
public class ArtistDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser arg0, DeserializationContext arg1) {
        try {
            ArtistResponseBody artistResponseBody = arg0.readValueAs(ArtistResponseBody.class);
            if (Objects.nonNull(artistResponseBody)) {
                return artistResponseBody.getName();
            } else {
                return arg0.readValueAs(String.class);
            }
        } catch (IOException e) {
            try {
                return arg0.readValueAs(String.class).isEmpty()?"":arg0.readValueAs(String.class);
            } catch (IOException e1) {
                return "";
            }
        }
    }

    
}