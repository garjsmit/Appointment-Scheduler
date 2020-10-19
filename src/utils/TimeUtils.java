package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

   private ZoneId zid = ZoneId.systemDefault();


   public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
   public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a z");
   private static ObservableList<Integer> hours = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
   private static ObservableList<Integer> minutes = FXCollections.observableArrayList(00,15,30,45);
   private static LocalTime startOfBusiness = LocalTime.of(8, 00);
   private static LocalTime endOfBusiness = LocalTime.of(22, 00);

   public static ObservableList<Integer> getHours() {
      return hours;
   }

   public static ObservableList<Integer> getMinutes() {
      return minutes;
   }

   public static LocalTime getStartOfBusiness() {
      return startOfBusiness;
   }

   public static LocalTime getEndOfBusiness() {
      return endOfBusiness;
   }

   public static ZonedDateTime getZonedStartOfBusiness(LocalDate date){
      return ZonedDateTime.of(date, startOfBusiness, ZoneId.of("America/New_York"));
   }

   public static ZonedDateTime getZonedEndOfBusiness(LocalDate date){
      return ZonedDateTime.of(date, endOfBusiness, ZoneId.of("America/New_York"));
   }

   public static ZonedDateTime figureZonedDateTime(LocalDate date, int hour, int min, boolean pmTrue){

      LocalTime time;
      if(pmTrue)
         time = LocalTime.of(hour + 12, min);
      else
         time = LocalTime.of(hour, min);

      LocalDateTime dateTime = LocalDateTime.of(date, time);

      ZonedDateTime zDateTime = ZonedDateTime.of(dateTime, ZoneId.of("America/New_York"));

      return zDateTime;
   }



}
