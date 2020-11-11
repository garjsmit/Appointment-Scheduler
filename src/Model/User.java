package Model;

/**User class*/
public class User {

    private int userID;
    private String username, password;


    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * @return Returns userID.
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * @return Returns username.
     * */
    public String getUsername() {
        return username;
    }

    /**
     * @return Returns password.
     * */
    public String getPassword() {
        return password;
    }

    /**
     * @return Returns String "userID | username".
     * */
    @Override
    public String toString(){
        return (userID + " | " + username);
    }

}
