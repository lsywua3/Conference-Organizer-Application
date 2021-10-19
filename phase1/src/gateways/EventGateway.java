package gateways;

import entities.Event;

import java.io.*;
import java.util.ArrayList;

public class EventGateway implements IGateway {
    String location = System.getProperty("user.dir") + "\\phase1\\src\\data\\EventData.ser";

    public ArrayList<Event> read() {
        ArrayList<Event> allEvents = new ArrayList<>();
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

    public boolean write(ArrayList events) {
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
