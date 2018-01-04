package bgu.spl181.net.movierental;

public abstract class User {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public boolean correctPassword(String password){
        return this.password==password;
    }
}
