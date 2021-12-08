package userManagement;

import emailManagement.EmailType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PollUserManagerTest {

    private PollUserManager userManager = null;
    private User user = null;

    @Test
    void signUp() {
        this.userManager = new PollUserManager();
        user = new User("testID_SU", "testFirstName", "testLastName", "testEmailAddress_SU", "testPassword");
        assertEquals(
                true,
                userManager.signUp(user),
                "An Error occurred during a Sign Up"
        );
    }

    @Test
    void forgotPassword() {
        this.userManager = new PollUserManager();
        user = new User("testID_FP", "testFirstName", "testLastName", "testEmailAddress_FP", "testPassword");
        userManager.signUp(user);
        user.setChangePasswordToken("");
        assertEquals(
                true,
                userManager.forgotPassword(user),
                "An Error occurred during Forgot Password procedure"
        );
    }

    @Test
    void emailVerification() {
        this.userManager = new PollUserManager();
        user = new User("testID_EV", "testFirstName", "testLastName", "testEmailAddress_EV", "testPassword");
        userManager.signUp(user);
        user.setChangePasswordToken("");
        userManager.forgotPassword(user);
        assertEquals(
                true,
                userManager.emailVerification(user.getUserID(), user.getVerificationToken(),
                        EmailType.ACCOUNT_CREATION, null),
                "An Error occurred during Email Verification of the Sign Up procedure"
        );
        assertEquals(
                true,
                userManager.emailVerification(user.getUserID(), user.getChangePasswordToken(),
                        EmailType.FORGOT_PASSWORD, PollUserManager.createTemporaryPassword()),
                "An Error occurred during Email Verification of the Forgot Password procedure"
        );
    }

    @Test
    void changePassword() {
        this.userManager = new PollUserManager();
        user = new User("testID_CP", "testFirstName", "testLastName", "testEmailAddress_CP", "testPassword");
        userManager.signUp(user);
        user.setPassword("changedTestPassword");
        assertEquals(
                true,
                userManager.changePassword(user),
                "An Error occurred during Change Password procedure"
        );
    }
}