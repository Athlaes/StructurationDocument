package fr.ul.miage.sd.metier;

public class Comment {
    private String userSession;
    private String userName;
    private String mbid;
    private String comment;

    public Comment() {}

    public Comment(String userSession, String userName, String mbid, String comment) {
        this.userName = userName;
        this.userSession = userSession;
        this.mbid = mbid;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSession() {
        return userSession;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

}
