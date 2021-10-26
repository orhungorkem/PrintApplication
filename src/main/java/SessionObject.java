public class SessionObject {

    private int id;
    private String username;
    private boolean authenticated;


    public SessionObject(int id, String username, boolean authenticated) {
        this.id = id;
        this.username = username;
        this.authenticated = authenticated;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}


