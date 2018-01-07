package bgu.spl181.net.api.ustbp;

public abstract class User {
    private String username;
    private String password;
    private boolean isAdmin;
    private int balance;

    public User(String username){
        this.balance=0;
        this.username=username;
    }

    public void setAdmin(boolean admin) { isAdmin = admin; }

    public String getUsername() {
        return username;
    }

    public void setBalance(int balance){ this.balance=balance; }

    public int getBalance() { return balance; }

    public void addBalance(int amount){ this.balance=balance+amount; }

    public boolean correctPassword(String password){
        return this.password==password;
    }
}
