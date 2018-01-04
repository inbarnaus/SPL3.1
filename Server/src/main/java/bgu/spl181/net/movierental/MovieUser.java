package bgu.spl181.net.movierental;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieUser {
    @SerializedName("username")
    private String username;
    @SerializedName("type")
    private final String type;
    @SerializedName("password")
    private String password;
    @SerializedName("country")
    private String country;
    @SerializedName("movies")
    private List<String> movies;
    @SerializedName("balance")
    private int balance;
    private boolean loggedin;

    public MovieUser(String username, String type, String password, String country, List<String> movies, int balance) {
        this.username = username;
        this.type = type;
        this.password = password;
        this.country = country;
        this.movies = movies;
        this.balance = balance;
        loggedin=false;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }
}
