package gateways;

import room.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway for Room.
 */
public class RoomGateway implements IGateway {
    private String location;

    public RoomGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\RoomData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\RoomData.ser";
        }
    }

    public List read() {
        List<Room> allRooms = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allRooms = (List) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return allRooms;
    }

    public boolean write(List rooms) {
        for (Object o : rooms) {
            if (!(o instanceof Room)) {
                return false;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(rooms);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
