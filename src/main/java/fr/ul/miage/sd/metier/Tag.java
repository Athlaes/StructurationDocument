package fr.ul.miage.sd.metier;

public class Tag {
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
}
