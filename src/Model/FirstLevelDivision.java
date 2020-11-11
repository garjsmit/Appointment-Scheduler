package Model;

/**FirstLevelDivision class*/
public class FirstLevelDivision {


    private int divisionID, countryID;
    private String division;

    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }

    /**
     * @return Returns divisionID.
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return Returns countryID.
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return Returns division.
     * */
    public String getDivision() {
        return division;
    }

    /**
     * @return Returns String division.
     * */
    @Override
    public String toString(){
        return (division);
    }


}
