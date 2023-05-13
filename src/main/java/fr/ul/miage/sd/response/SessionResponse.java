package fr.ul.miage.sd.response;

import fr.ul.miage.sd.metier.Session;

public class SessionResponse {
    private String message;
    private String error;
    private Session session;
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
