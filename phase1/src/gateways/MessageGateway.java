package gateways;

import entities.Message;

import java.io.*;
import java.util.ArrayList;

public class MessageGateway implements IGateway {
    String location = System.getProperty("user.dir") + "\\phase1\\src\\data\\MessageData.ser";

    public ArrayList<Message> read() {
        ArrayList<Message> allMessages = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allMessages = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return allMessages;
    }

    public boolean write(ArrayList messages) {
        for (Object o : messages) {
            if (!(o instanceof Message)) {
                return false;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(messages);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
