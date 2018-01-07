package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.ustbp.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieUser extends User{
    @SerializedName("country")
    private List<String> datablock;
    @SerializedName("movies")
    private List<String> movies;
    @SerializedName("balance")
    private int balance;
    private String isAdmin;

    public MovieUser(String username, String password, List<String> datablock, String isAdmin) {
        super(username,password,isAdmin);
        this.datablock=datablock;
        this.movies = new ArrayList<>();
        this.balance = 0;
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

    public void addBalance(int amount){ this.balance=balance+amount;}

    public boolean isRent(String movie){ return movies.contains(movie); }

    public boolean isAdmin() { return isAdmin.equals("admin"); }
}
