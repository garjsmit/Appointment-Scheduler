package Model;

/**Country class*/
public class Country {

    private int countryID;
    private String country;

    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
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
     * @return Returns String countryName.
     * */
    @Override
    public String toString(){
        return (country);
    }
}
