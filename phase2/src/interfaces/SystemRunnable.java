package interfaces;

/**
 * System can be run by console inputs.
 */
public interface SystemRunnable {

    /**
     * Takes a command and calls the corresponding operation.
     *
     * @param command an integer represents the operation.
     */
    void navigateCommand(int command);

    /**
     * Runs the system, take user input to decide operations.
     *
     * @return 0 if the program execute successfully.
     */
    int run();
}
