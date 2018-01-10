package bgu.spl181.net.impl.movierental;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("bannedCountries")
    private List<String> bannedCountries;
    @SerializedName("availableAmount")
    private int availableAmount;
    @SerializedName("totalAmount")
    private int totalAmount;

    public Movie(int id, String name, String price, List<String> bannedCountries, int availableAmount, int totalAmount) {
        this.id = String.valueOf(id);
        this.name = name;
        this.price = price;
        this.bannedCountries = bannedCountries;
        this.availableAmount = availableAmount;
        this.totalAmount = totalAmount;
    }

    public int getPrice() {
        return Integer.parseInt(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getBannedCountries() {
        return bannedCountries;
    }

    public void setBannedCountries(List<String> bannedCountries) {
        this.bannedCountries = bannedCountries;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void decAvailableAmount() {
        availableAmount--;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isBanned(String country){ return bannedCountries.contains(country); }

    @Override
    public String toString() {
        String sBannedCountries = "";
        for (int i = 0; i <bannedCountries.size() ; i++) {
            sBannedCountries+="\""+bannedCountries.get(i)+"\"";
            if(i+1!=bannedCountries.size()){
                sBannedCountries+=" ";
            }
        }
        return "\"" + name + "\"" +
                " " + availableAmount+
                " " + price +
                " " + sBannedCountries;
    }
}
