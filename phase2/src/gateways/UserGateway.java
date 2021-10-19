package gateways;

import account.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway for User.
 */
public class UserGateway implements IGateway {
    private String location;

    public UserGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\UserData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\UserData.ser";
        }
    }

    public List<User> read() {
        List<User> allUsers = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allUsers = (List) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return allUsers;
    }

    public boolean write(List users) {
        for (Object o : users) {
            if (!(o instanceof User)) {
                return false;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
