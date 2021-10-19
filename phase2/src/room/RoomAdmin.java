package room;

import event.useCases.EventBoard;
import event.useCases.ScheduleGenerator;
import gateways.IGateway;

import java.util.List;

/**
 * Room Admin is used exclusively by a staff in the current version.
 * It gives access to change room variables, addRoom, removeRooms, etc.
 */
public class RoomAdmin extends RoomManager {

    /**
     * Constructs a new room admin object by calling the RoomManager's constructor.
     *
     * @param g                 RoomGateway
     * @param scheduleGenerator ScheduleGenerator
     */
    public RoomAdmin(IGateway g, ScheduleGenerator scheduleGenerator) {
        super(g, scheduleGenerator);
    }

    /**
     * Set the name of the room, then saves the change through gateway.
     *
     * @param id   id representation of the room
     * @param name new name for the room
     * @param g    gateway to store the data
     * @return true if the name has been set, and false otherwise.
     */
    public boolean setName(int id, String name, IGateway g) {
        getRoomById(id).setName(name);
        g.write(rooms);
        return true;
    }

    /**
     * Set the capacity to a new capacity if all the events held in this room have a smaller or equal capacity to
     * the new capacity.
     * Then saves the change through gateway.
     *
     * @param id       id representation of the room
     * @param capacity new name for the room
     * @param g        gateway to store the data
     * @param eb       eventBoard to get all the events from
     * @return true if the name has been set, and false otherwise.
     */
    public boolean setCapacity(int id, int capacity, IGateway g, EventBoard eb) {
        int roomId = getRoomById(id).getId();
        List<Integer> allEventId = eb.getAllEventsId();
        for (int eventId : allEventId) {
            if (Integer.parseInt(eb.getInfo(eventId, "room")) == (roomId)) {
                if (Integer.parseInt(eb.getInfo(eventId, "capacity")) > capacity) {
                    return false; // this room has an event that has capacity beyond new room capacity.
                }
            }
        }
        getRoomById(id).setCapacity(capacity);
        g.write(rooms);
        return true;
    }

    /**
     * Set the location of the room, then saves the change through gateway.
     *
     * @param id       id representation of the room
     * @param location new location for the room
     * @param g        gateway to store the data
     * @return true if the location has been set, and false otherwise.
     */
    public boolean setLocation(int id, String location, IGateway g) {
        getRoomById(id).setLocation(location);
        g.write(rooms);
        return true;
    }

    /**
     * Add a given equipment to a room.
     * If added, save the change through gateway
     *
     * @param id    representation of the room
     * @param equip new equipment to be added
     * @param g     gateway to save the room information
     * @return true if the equipment has been added, and false otherwise.
     */
    public boolean addEquip(int id, String equip, IGateway g) {
        boolean added = getRoomById(id).addEquipment(equip);
        if (added) {
            g.write(rooms);
        }
        return added;
    }

    /**
     * Remove the given equipment for a given room.
     * If removed, save the change through gateway.
     *
     * @param id    representation of the room
     * @param equip equipment to be added
     * @param g     gateway to save the room information
     * @return true if the equipment has been removed, and false otherwise.
     */
    public boolean removeEquip(int id, String equip, IGateway g) {
        boolean deleted = getRoomById(id).removeEquipment(equip);
        if (deleted) {
            g.write(rooms);
        }
        return deleted;
    }

    /**
     * Add a new room entity object by the given parameters.
     * We do not check for any conflicts as they we will have id as the unique representation.
     * Then we update the rooms list.
     * Since we are modifying the rooms list, we have to notify the observers through the updateRooms method.
     *
     * @param name       name of the room
     * @param location   location of the room
     * @param equipments equipment in this room
     * @param capacity   capacity of this room
     * @param g          gateway to save the change.
     * @return true iff the room is added
     */
    public boolean addRoom(String name, String location, List<String> equipments, int capacity, IGateway g) {
        int id = generateRoomId();
        Room newRoom = new Room(id, name, location, equipments, capacity);
        rooms.add(newRoom);
        save(g);
        return true;
    }

    /**
     * Remove the room by the given id if this room does not currently host any events.
     * Then save the changes in rooms, and notify its observers.
     *
     * @param id representation of the room
     * @param g  gateway to save the changes
     * @param eb eventboard to check all the events.
     * @return true if the room has been removed, and false otherwise.
     */
    public boolean removeRoom(int id, IGateway g, EventBoard eb) {
        if (getOccupation(id, eb).size() > 0) {
            return false;
        } else {
            rooms.remove(getRoomById(id));
        }
        save(g);
        return true;
    }

    /**
     * Generate an id for the upcoming room.
     * ID starts with 1000, and additional rooms have the id of the last room's id incremented by 1.
     *
     * @return int id.
     */
    private int generateRoomId() {
        if (rooms.size() == 0) {
            return 1000;
        } else {
            return rooms.get(rooms.size() - 1).getId() + 1;
        }
    }

    /**
     * Save changes to data.
     *
     * @param g RoomGateway
     */
    private void save(IGateway g) {
        updateRooms();
        g.write(rooms);
    }
}
