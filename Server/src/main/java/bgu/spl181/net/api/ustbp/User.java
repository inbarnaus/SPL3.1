package bgu.spl181.net.api.ustbp;

import com.google.gson.annotations.SerializedName;

public abstract class User {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean admin){
        this.username=username;
        this.isAdmin=admin;
        this.password=password;
    }

    public void setAdmin(boolean admin) { isAdmin = admin; }

    public String getUsername() {
        return username;
    }

    public boolean correctPassword(String password){
        return this.password==password;
    }
}
