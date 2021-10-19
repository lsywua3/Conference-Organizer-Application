package gateways;

import java.util.ArrayList;

public interface IGateway {

    /**
     * Read the file in the given path (assigned during implementation).
     *
     * @return the ArrayList of all Events/Messages/Users (objects) depending on the gateway type.
     */
    ArrayList read();

    /**
     * Take in the ArrayList of all Events/Messages/Users (objects) and store the data in the given
     * path (assigned during implementation)
     * <p>
     * Check if the input ArrayList has the type that matches the gateway's type.
     * Catch any IOException
     *
     * @param l the ArrayList of all Events/Messages/Users (objects)
     * @return the boolean representation of the result after writing.
     */
    boolean write(ArrayList l);
}
