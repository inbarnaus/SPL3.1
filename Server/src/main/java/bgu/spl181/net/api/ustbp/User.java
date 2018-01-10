package bgu.spl181.net.api.ustbp;

import com.google.gson.annotations.SerializedName;

public abstract class User {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("type")
    protected String isAdmin;

    public User(){}
    public User(String username, String password, String admin){
        this.username=username;
        this.isAdmin=admin;
        this.password=password;
    }

    public boolean isAdmin(){ return isAdmin.equals("admin"); }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean correctPassword(String password){
        return this.password.equals(password);
    }
}
