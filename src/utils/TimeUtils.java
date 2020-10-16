package utils;

import javafx.scene.control.ComboBox;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

   private ZoneId zid = ZoneId.systemDefault();


   public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
   public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a z");

   public static LocalTime businessHoursStart = LocalTime.of(8, 00);
   public static LocalTime businessHoursEnd = LocalTime.of(22, 00);

   public static ComboBox<LocalTime> populateComboBox(ComboBox<LocalTime> comboBox){
      while(businessHoursStart.isBefore(businessHoursEnd.plusSeconds(1))){
         comboBox.getItems().add(businessHoursStart);
         businessHoursStart = businessHoursStart.plusMinutes(30);
      }
      return comboBox;
   }




   //Error checker function - is this outside business hours?


}
