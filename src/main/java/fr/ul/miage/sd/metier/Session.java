package fr.ul.miage.sd.metier;

public class Session {
    private String name;

    private String key;

    private int subscriber;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public int getSubscriber() {
        return subscriber;
    }
}
