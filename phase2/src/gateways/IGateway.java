package gateways;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway can read and write data.
 */
public interface IGateway {

    /**
     * Read the file in the given path (assigned during implementation).
     * @return the ArrayList of all Events/Messages/Users (objects) depending on the gateway type.
     */
    List read();

    /**
     * Take in the ArrayList of all Events/Messages/Users (objects) and store the data in the given
     * path (assigned during implementation)
     *
     * Check if the input ArrayList has the type that matches the gateway's type.
     * Catch any IOException
     * @param l the ArrayList of all Events/Messages/Users (objects)
     * @return the boolean representation of the result after writing.
     */
    boolean write(List l);
}
