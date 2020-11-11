package Model;

import java.time.LocalDateTime;

/**Customer class*/
public class Customer {

    private int customerID, divisionID, countryID;
    private String customerName, address, phone, postalCode, division, country;
    private LocalDateTime createDate;

    /**Customer constructor for saving a new customer, when a customerID isn't already assigned. customerID not isn't part of the constructor because it will be assign by the databse.*/
    public Customer(String customerName, String address, String postalCode, String phone, int divisionID, String division, int countryID, String country, LocalDateTime createDate) {
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.division = division;
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
    }

    /**Customer constructor for saving an existing customer. Databse will UPDATE customer record based on customerID*/
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID, String division, int countryID, String country, LocalDateTime createDate) {
        this.customerID = customerID;
        this.postalCode = postalCode;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.division = division;
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
    }

    /**
     * @return Returns customerID.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return Returns customerName.
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return Returns phone.
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * @return Returns address.
     * */
    public String getAddress() {
        return address;
    }

    /**
     * @return Returns postalCode.
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return Returns divisionID.
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return Returns division.
     * */
    public String getDivision() {
        return division;
    }

    /**
     * @return Returns countryID.
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return Returns country.
     * */
    public String getCountry() {
        return country;
    }

    /**
     * @return Returns createDate.
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param customerID Set customerID.
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @param customerName Set customerName.
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @param phone Set phone.
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @param address Set address.
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param postalCode Set postalCode.
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @param divisionID Set divisionID.
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @param countryID Set countryID.
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @param division Set division.
     * */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @param country Set country.
     * */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @param createDate Set createDate.
     * */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Returns "customerID | customerID" for ComboBox display purposes.
     * */
    @Override
    public String toString(){
        return (customerID + " | " + customerName);
    }
}
