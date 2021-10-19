package gateways;

import request.entities.UserRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway for UserRequest.
 */
public class UserRequestGateway implements IGateway {
    private String location;

    public UserRequestGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\UserRequestData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\UserRequestData.ser";
        }
    }

    public List read() {
        List<UserRequest> allRequests = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allRequests = (List) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return allRequests;
    }

    public boolean write(List requests) {
        for (Object o : requests) {
            if (!(o instanceof UserRequest)) {
                return false;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(requests);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
