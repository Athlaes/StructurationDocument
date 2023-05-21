package fr.ul.miage.sd.response;

import java.util.Objects;

import fr.ul.miage.sd.metier.Wiki;

public class TagResponseBody {
    private String name;
    private int reach;
    private Wiki wiki;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public Wiki getWiki() {
        return wiki;
    }

    public boolean hasPartialData() {
        boolean partial = false;
        if (Objects.isNull(this.getName()) ||
            Objects.isNull(this.getReach()) ||
            Objects.isNull(this.getWiki())){
            partial = true;
        }
        return partial;
    }

    public String toString() {
        String res = String.format("Nom : %s%n", this.name);
        if (Objects.nonNull(wiki)) {
            res += String.format("Description : %s%n Publiée le : %s%n", this.wiki.getSummary(), this.wiki.getPublished());
        }
        return res;
    }
}
