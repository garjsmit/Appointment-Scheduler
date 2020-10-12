package Model;

public class FirstLevelDivision {


    private int divisionID, countryID;
    private String division;

    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }


    public int getDivisionID() {
        return divisionID;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString(){
        return (division);
    }


}
