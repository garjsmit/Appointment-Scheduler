package Model;

public class Customer {

    private int customerID, divisionID;
    private String customerName, address, phone, postalCode;

    public Customer(String customerName, String address, String postalCode, String phone, int divisionID) {
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getDivisionID() {
        return divisionID;
    }


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

}
