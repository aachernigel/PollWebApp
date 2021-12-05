package userManagement;

import PollManagerLib.PluginManager;

public class PollPluginFactory {
    // Available Plugins:
    //      1. UserManager
    //      2. EmailGateway

    public PluginManager getPlugin(Class classType){
        if(classType == null){
            System.err.println("The Plugin passed is null");
        } else if(classType.equals(PollUserManager.class)){
            return new PluginManager(new PollUserManager());
        } else if(classType.equals(EmailGateway.class)){
            return new PluginManager(new EmailGateway());
        }
        return null;
    }
}
