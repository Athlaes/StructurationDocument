package fr.ul.miage.sd.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsResponse {
    @JsonProperty("tag")
    @JsonAlias({"similarTags", "tags"})
    private List<TagResponseBody> tags;

    public TagsResponse() {}
    
    public TagsResponse(List<TagResponseBody> tagResponses) {
        this.tags = tagResponses;
    }

    public List<TagResponseBody> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseBody> toptags) {
        this.tags = toptags;
    }

    public String toString() {
        String res = "";
        for (TagResponseBody tagResponseBody : tags) {
            res += tagResponseBody.getName() + ",";
        }
        return res;
    }
}
