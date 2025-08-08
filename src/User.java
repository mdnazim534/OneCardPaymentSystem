abstract class User {
    private final String username, phoneNumber, pin, nid, address;
    protected double balance;

    public User(String username, String phoneNumber, String pin, String nid, String address, double balance) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.nid = nid;
        this.address = address;
        this.balance = balance;
    }

    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPin() { return pin; }
    public String getNid() { return nid; }
    public String getAddress() { return address; }
    public double getBalance() { return balance; }
    public void setBalance(double amount) { this.balance = amount; }

    abstract void showDetails();
}
