package account.presenters;

public class AccountSystemPresenter {

    public void presentMenu() {
        System.out.println("You entered Account System:");
        System.out.println("" +
                "[1] Log in to your account\n" +
                "[2] Register a new attendee account\n" +
                "[0] Exit");
    }

    /**
     * Display the message that asks the user's email to login.
     */
    public void presentLoginEmailInstruction() {
        System.out.println("Please enter your email: ");
    }

    /**
     * Display the message that asks the user's password of the corresponding email to login.
     */
    public void presentLoginPasswordInstruction() {
        System.out.println("Please enter your password: ");
    }

    /**
     * Present the message that tells the user that the email or password used to login is incorrect.
     */
    public void presentInvalidLoginError(){
        System.out.println("Email or password is incorrect, please try again.");
    }

    /**
     * Present the message that tells the user that they have successfully logged in.
     */
    public void presentSuccessfulLogin(){
        System.out.println("Logged in successfully!");
    }

    /**
     * Display the message that asks for the user's name.
     */
    public void presentSignupName(){
        System.out.println("Please enter your name: ");
    }

    /**
     * Display the message that asks the user for the email that they want to use to sign up for an account.
     */
    public void presentSignupEmail(){
        System.out.println("Please enter a valid email: ");
    }

    /**
     * Display the message that asks the user for a password they want to use to log in to their account.
     */
    public void presentSignupPassword(){
        System.out.println("Please enter a valid password: ");
    }

    /**
     * Present the message that tells the user that they have entered an illegal command.
     */
    public void presentCommandError(){
        System.out.println("Illegal command!");
    }

    /**
     * Present the message that tells the user that they have entered an illegal input.
     */
    public void presentInputError(){
        System.out.println("Illegal input!");
    }

    /**
     * Present the message that tells the user that they have successfully registered an account.
     * @param type type of the account
     * @param id id of the account
     */
    public void presentRegistered(String type, Integer id){
        System.out.println("Successfully created " + type + " account #" + id);
    }

    /**
     * Present the message that tells the user that the email is already used, try to sign up
     * using a different email.
     */
    public void presentInvalidEmail(){
        System.out.println("Email is already used, please try again.");
    }
}