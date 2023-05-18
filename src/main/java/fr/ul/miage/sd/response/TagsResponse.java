package fr.ul.miage.sd.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsResponse {
    @JsonProperty("tag")
    @JsonAlias({"similarTags", "tags"})
    private List<TagResponse> tags;

    public TagsResponse() {}
    
    public TagsResponse(List<TagResponse> tagResponses) {
        this.tags = tagResponses;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> toptags) {
        this.tags = toptags;
    }
}
