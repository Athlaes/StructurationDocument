package fr.ul.miage.sd.deserializer;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fr.ul.miage.sd.response.TagsResponse;

public class TagsDeserializer extends JsonDeserializer<TagsResponse> {

    @Override
    public TagsResponse deserialize(JsonParser arg0, DeserializationContext arg1) {
        try {
            TagsResponse res = arg0.readValueAs(TagsResponse.class);
            if (Objects.isNull(res) || Objects.isNull(res.getTags()) || res.getTags().isEmpty()) {
                return null;
            } else {
                return res;
            }
        } catch (IOException e) {
            return null;
        }
    }

    
}
