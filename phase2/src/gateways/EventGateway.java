package gateways;

import event.entities.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway for Events.
 */
public class EventGateway implements IGateway {
    private String location;

    public EventGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\EventData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\EventData.ser";
        }
    }

    public List<Event> read() {
        List<Event> allEvents = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allEvents = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return allEvents;
    }

    public boolean write(List events) {
        for (Object o : events) {
            if (!(o instanceof Event)) {
                return false;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(events);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
