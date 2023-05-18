package fr.ul.miage.sd.metier;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
    private int listeners;
    @JsonProperty("plays")
    @JsonAlias("playcount")
    private int plays;

    public int getListeners() {
        return listeners;
    }

    public int getPlays() {
        return plays;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }
    
    public void setPlays(int plays) {
        this.plays = plays;
    }
}
