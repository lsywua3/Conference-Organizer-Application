package room;

import java.util.ArrayList;
import java.util.List;

/**
 * A room in the conference
 */
public class Room implements java.io.Serializable {
    private final int id;
    private String name;
    private String location;
    private List<String> equipment;
    private int capacity;

    /**
     * Constructs a new room object will the following parameters
     *
     * @param id         unique representation of the room. Starts from 1000
     * @param name       name of the room
     * @param location   location of the room
     * @param equipments the equipment that this room has
     * @param capacity   maximum capacity of this room
     */
    public Room(int id, String name, String location, List<String> equipments, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.equipment = new ArrayList<>();
        this.setEquipments(equipments);
        this.capacity = capacity;
    }

    /**
     * Getter for this room's id
     *
     * @return the room's id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for this room's name
     *
     * @return the room's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for this room's capacity
     *
     * @return the room's capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter for this room's equipment
     *
     * @return the room's equipment
     */
    public List<String> getEquipment() {
        return equipment;
    }

    /**
     * Getter for this room's location
     *
     * @return the room's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Add an equipment to the equipment list of the room, if it is not in there already.
     *
     * @return true if this equipment has been added, and false otherwise.
     */
    public boolean addEquipment(String equipment) {
        if (this.equipment.contains(equipment)) {
            return false;
        } else {
            this.equipment.add(equipment);
            return true;
        }
    }

    /**
     * Remove an equipment to the equipment list of the room, if it is in there.
     *
     * @return true if this equipment has been removed, and false otherwise.
     */
    public boolean removeEquipment(String equipment) {
        if (!this.equipment.contains(equipment)) {
            return false;
        } else {
            this.equipment.remove(equipment);
            return true;
        }
    }

    /**
     * Set this room's equipment to the given equipments, after checking for duplicates.
     */
    public void setEquipments(List<String> equipments) {
        List<String> tempEquipment = new ArrayList<>();
        for (String e : equipments) {
            if (!this.equipment.contains(e)) {
                tempEquipment.add(e);
            }
        }
        this.equipment = tempEquipment;
    }

    /**
     * Set the name of this room
     *
     * @return true if the name has been set, always return true since name does not check for duplicate.
     */
    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    /**
     * Set the capacity of this room
     *
     * @return true if the capacity has been set, always return true.
     */
    public boolean setCapacity(int capacity) {
        this.capacity = capacity;
        return true;
    }

    /**
     * Set the location of this room
     *
     * @return true if the location has been set, always return true since location does not check for duplicate.
     */
    public boolean setLocation(String location) {
        this.location = location;
        return true;
    }
}
