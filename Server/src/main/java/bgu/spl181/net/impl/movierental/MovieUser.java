package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.ustbp.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieUser extends User{
    @SerializedName("type")
    private final String type;
    @SerializedName("country")
    private String country;
    @SerializedName("movies")
    private List<String> movies;
    @SerializedName("balance")
    private int balance;
    private boolean isAdmin;

    public MovieUser(String username, String type, String password, String country, List<String> movies, int balance, boolean isAdmin) {
        super(username,password,isAdmin);
        this.type = type;
        this.country = country;
        this.movies = movies;
        this.balance = balance;
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

    public String getCountry() { return country; }

    public void addBalance(int amount){ this.balance=balance+amount;}

    public boolean isRent(String movie){ return movies.contains(movie); }

    public boolean isAdmin() { return isAdmin; }
}
