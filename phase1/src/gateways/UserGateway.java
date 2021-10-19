package gateways;

import entities.User;

import java.io.*;
import java.util.ArrayList;

public class UserGateway implements IGateway {
    String location = System.getProperty("user.dir") + "\\phase1\\src\\data\\UserData.ser";

    public ArrayList<User> read() {
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allUsers = (ArrayList) ois.readObject();

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

    public boolean write(ArrayList users) {
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
