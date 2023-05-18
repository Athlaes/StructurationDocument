package fr.ul.miage.sd.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ul.miage.sd.metier.FacetGroup;

public class CountryResponse {
    @JsonProperty(value = "facet_groups")
    private List<FacetGroup> facetGroups;

    public void setFacetGroups(List<FacetGroup> facetGroups) {
        this.facetGroups = facetGroups;
    }

    public List<FacetGroup> getFacetGroups() {
        return facetGroups;
    }
}

