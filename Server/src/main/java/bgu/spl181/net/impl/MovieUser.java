package bgu.spl181.net.impl;

import bgu.spl181.net.api.ustbp.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieUser extends User{
    @SerializedName("country")
    private String country;
    @SerializedName("movies")
    private List<Movie> movies;
    @SerializedName("balance")
    private int balance;

    public MovieUser(String username, String password, String country, String isAdmin, int balance) {
        super(username,password,isAdmin);
        this.country=country;
        this.movies = new ArrayList<>();
        this.balance = balance;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int amount){ this.balance=balance+amount;}

    public boolean isRent(String movie){
        for (Movie currMovie: movies
             ) {
            if (currMovie.getName().equals(movie)){
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin() { return isAdmin.equals("admin"); }

    public String getCountry() {
        return country;
    }

    public void decBalance(int price){ this.balance=balance-price; }

    public void removeMovie(String movie){
        for (Movie currmovie: movies
             ) {
            if(currmovie.getName().equals(movie)){
                movies.remove(currmovie);
                break;
            }
        }
    }
}
