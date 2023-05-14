package fr.ul.miage.sd.metier;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacetGroup {
    @JsonProperty(value = "facets")
    private List<Pays> pays;

    public void setPays(List<Pays> pays) {
        this.pays = pays;
    }

    public List<Pays> getPays() {
        return pays;
    }
}