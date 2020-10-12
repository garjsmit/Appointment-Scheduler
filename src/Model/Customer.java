package Model;

public class Customer {

    private int customerID, divisionID, countryID;
    private String customerName, address, phone, postalCode, division, country;


    public Customer(String customerName, String address, String postalCode, String phone, int divisionID, String division, int countryID, String country) {
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.division = division;
        this.countryID = countryID;
        this.country = country;
    }

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID, String division, int countryID, String country) {
        this.customerID = customerID;
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.division = division;
        this.countryID = countryID;
        this.country = country;
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

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
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


    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }


    public void setDivision(String division) {
        this.division = division;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
