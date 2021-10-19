package gateways;

import message.entities.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway for Messages.
 */
public class MessageGateway implements IGateway {
    private String location;

    public MessageGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\MessageData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\MessageData.ser";
        }
    }

    public List read() {
        List<Message> allMessages = new ArrayList<Message>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allMessages = (List) ois.readObject();

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

    public boolean write(List messages) {
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
