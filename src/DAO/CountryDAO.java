package DAO;

import Model.Country;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    private static ObservableList<Country> countryList = FXCollections.observableArrayList();

    public static ObservableList<Country> getAllCountries(){

        countryList.clear();
        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement("SELECT * FROM countries");

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country newCountry = new Country(countryID, country);

                countryList.add(newCountry);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return countryList;
    }

}
