class RegularUser extends User {
    public RegularUser(String username, String phoneNumber, String pin, String nid, String address, double balance) {
        super(username, phoneNumber, pin, nid, address, balance);
    }

    @Override
    public void showDetails() {
        System.out.println("Username: " + getUsername());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("NID: " + getNid());
        System.out.println("Address: " + getAddress());
        System.out.println("Balance: BDT " + getBalance());
    }
}
