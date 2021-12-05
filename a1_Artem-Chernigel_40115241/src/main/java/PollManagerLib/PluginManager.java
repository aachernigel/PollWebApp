package PollManagerLib;

public class PluginManager {
    private UserManagement userManagement = null;
    private EmailManagement emailManagement = null;

    public PluginManager(UserManagement userManagement){
        this.userManagement = userManagement;
    }

    public PluginManager(EmailManagement emailManagement){
        this.emailManagement = emailManagement;
    }

    public UserManagement getUserManagement(){
        if(this.userManagement == null)
            throw new IllegalStateException("Plugin was set to Email Management");
        return this.userManagement;
    }

    public EmailManagement getEmailManagement(){
        if(this.emailManagement == null)
            throw new IllegalStateException("Plugin was set to User Management");
        return this.emailManagement;
    }


}
