package ttl.backend.domain;

public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String taxID;

    public Customer( int customerID, String name, String address, String taxID) {    //This is a constructor. A Constructor must have the exact same name as the class, no return type, and it must not have void
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.taxID = taxID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTaxID() {
        return taxID;
    }
}
