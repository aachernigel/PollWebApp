package userManagement;

public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public User(String userID, String firstName, String lastName, String emailAddress, String password){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getUserID(){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
