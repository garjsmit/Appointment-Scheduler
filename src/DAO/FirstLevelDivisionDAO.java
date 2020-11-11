package DAO;

import Model.Country;
import Model.FirstLevelDivision;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.DrbgParameters;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** First Level Division DAO */
public class FirstLevelDivisionDAO {

    private static ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();

    /**
     * @return firstLevelDivisionList
     * This is a list of all first level divisions*/
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {

        firstLevelDivisionList.clear();
        try {
            String selectStatement = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision newFLD = new FirstLevelDivision(divisionID, division, countryID);

                firstLevelDivisionList.add(newFLD);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return firstLevelDivisionList;
    }

    /**
     * @param selectedCountryID user selects country
     * @return firstLevelDivisionList
     * This is a list of all first level divisions associated with selected country*/
    public static ObservableList<FirstLevelDivision> getSelectedFirstLevelDivisions(int selectedCountryID) {

        firstLevelDivisionList.clear();

        try {
            String selectStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);

            ps.setInt(1, selectedCountryID);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision newFLD = new FirstLevelDivision(divisionID, division, countryID);

                firstLevelDivisionList.add(newFLD);

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return firstLevelDivisionList;
    }
}
