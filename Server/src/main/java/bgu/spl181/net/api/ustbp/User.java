package bgu.spl181.net.api.ustbp;

public abstract class User {
    private String username;
    private final boolean admin;

    public User(String username, boolean admin){
        this.username=username;
        this.admin=admin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }
}
