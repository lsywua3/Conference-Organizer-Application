package presenters;

/**
 * Presenter for Main Menu.
 */
public class MainMenuPresenter {
    /**
     * Display a welcome message.
     */
    public void presentWelcome() {
        System.out.println("Hello, welcome to the Conference!");
    }

    /**
     * Display the list of operations.
     *
     * @param name name of the current user.
     */
    public void presentMenu(String name, String id) {
        System.out.println("------------\nWelcome, " + name + " #" + id);
        System.out.println("[1] View events");
        System.out.println("[2] View messages");
        System.out.println("[0] Log out");
    }
}
