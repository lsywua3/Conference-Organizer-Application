package gateways;

import rate.Rate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gateway for Rate.
 */
public class RateGateway implements IGateway {
    private String location;

    public RateGateway() {
        String root = System.getProperty("user.dir");
        if (root.endsWith("phase2")) {
            location = root + "\\src\\data\\RateData.ser";
        } else {
            location = root + "\\phase2\\src\\data\\RateData.ser";
        }
    }

    public List read() {
        List<HashMap<Integer, Rate>> ratingBoard = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ratingBoard = (List) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            return new ArrayList<>();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return ratingBoard;
    }

    public boolean write(List ratingBoard) {
        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ratingBoard);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
}
