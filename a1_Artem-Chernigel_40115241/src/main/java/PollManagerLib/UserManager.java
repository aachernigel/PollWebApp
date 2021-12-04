package PollManagerLib;

public class UserManager {
    private final UserManagement userManagement;

    public UserManager(UserManagement userManagement){
        this.userManagement = userManagement;
    }

    public UserManagement getUserManagement(){
        return this.userManagement;
    }
}
