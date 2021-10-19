package interfaces;

import java.util.*;

/**
 * The class has a list of info that can be accessed.
 */
public interface InfoAccessible {
    /**
     * Return information about an event.
     * @param id An event's id.
     * @return information about one event
     */
    List<String> getInfoList(int id);

    /**
     * Return one specific information about one specific event.
     * @param id An event's id.
     * @param option One information about this event.
     * @return a specific single information about this event.
     */
    String getInfo(int id, String option);
}
