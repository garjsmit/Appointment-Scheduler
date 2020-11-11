Irwin Mainway Appointment Appointment Scheduler v2.01 November 11, 2020

PURPOSE: For the basic management of customers and their contact information allowing user to add, update, and delete customers,
and the scheduling of appointments, again with add, update and delete functionality. Customers and appointment changes are written to database.

AUTHOR: Garrison Smith
        gsmi108@gmail.com

IDE:    IntelliJ Ultimate 2020.2.3
        Java SE 11.0.8
        JavaFX-SDK-11.0.2


LOGIN SCREEN:
At the login screen, enter your username and password. An error will be generated if fields are left blank, if a password doesn't match a username, and if a username is not found.
After a correct login, the Customer screen will be displayed, and the user will be alerted if they have an appointment within 15 minutes, or if they do not have an upcoming appointment.
All logins are logged, whether successful or not, with username, date and time.

Use the Customers, Appointments, and Reports buttons to navigate to different screens.
The Exit button on each screen exits the application. User will be asked to confirm to exit.

CUSTOMER SCREEN:
The Customer screen first appears with the table populated with customer information, and the text fields disabled.

    To add a customer:  Click Add Customer and the Customer entry fields will become enabled.
                        Customer IDs are auto-generated and cannot be edited.
                        Users are only able to users to a Division that matches the country that is selected.
                        Enter information and click Save.

    To update a customer:   Select a customer in the table, click Update Customer, and the form will be populated with the customer information.
                            Edit information and click Save.

    To delete a customer:   Select customer and click Delete Customer. User will be asked to confirm delete.
                            If a customer has scheduled appointments, the appointment will be deleted first. User will be alerted how many appointments will be deleted.


APPOINTMENT SCREEN:
The Appointments screen first appears with the table populated with appointment information, and the text fields disabled.

    To add an appointment:  Click Add Appointment and the Appointment entry fields will become enabled.
                            Appointment IDs are auto generated. They cannot be edited.
                            To choose a time: select Hours and Minutes from the drop-down menus and select AM or PM.
                            Appointments can only be made on the hour, or at the 15, 30, or 45 minute marks.
                            Appointments cannot be made before 8 am EST, or after 10 pm EST.
                            Appointments cannot overlap other appointments that a CUSTOMER has. They MAY be back-to-back.
                            Enter information and click Save.

    To update a customer:   Select a customer in the table, click Update Customer, and the form will be populated with the customer information.
                            Edit information and click Save.
                            All the above rules for appointments apply to updates.

    To delete a customer:   Select customer and click Delete Customer. User will be asked to confirm delete.

    Using the radio buttons, users can view only appointments within the current month, or within the current week.
    Selecting the "Show All" radio button will refresh table with all scheduled appointments.

REPORTS SCREEN:
Users can view three reports on the Reports screen.

1) The upper table will show a selected contact's appointment list. Using the drop-down menu underneath the table, select contact and the table will refresh.

2) On the lower right corner, users can view appointment counts by type, for the last 12 months. Select the month from the drop-down menu that the counts will appear.

3) The lower table will show any customers that have been added within the last 30 days.