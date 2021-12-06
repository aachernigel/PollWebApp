package userManagement;


public class User {
    private static final int TOKEN_LENGTH = 10;

    private String userID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String verificationToken;
    private String changePasswordToken;

    public User(String userID, String firstName, String lastName, String emailAddress, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.verificationToken = null;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateVerificationToken() {
        String verificationToken = "";
        for (int i = 0; i < TOKEN_LENGTH; i++)
            verificationToken += (int) (Math.random() * TOKEN_LENGTH);
        this.verificationToken = verificationToken;
    }

    public void generateChangePasswordToken(){
        String changePasswordToken = "";
        for (int i = 0; i < TOKEN_LENGTH; i++)
            changePasswordToken += (int) (Math.random() * TOKEN_LENGTH);
        this.changePasswordToken = changePasswordToken;
    }

    public String getChangePasswordToken(){
        return this.changePasswordToken;
    }

    public void setChangePasswordToken(String changePasswordToken){
        this.changePasswordToken = changePasswordToken;
    }

    public String getVerificationToken() {
        return this.verificationToken;
    }
}
