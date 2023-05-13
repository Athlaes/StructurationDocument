package fr.ul.miage.sd.metier;

import java.util.Date;

public class Wiki {
    private Date published;
    private String summary;

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getPublished() {
        return published;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
