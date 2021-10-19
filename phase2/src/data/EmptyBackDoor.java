package data;


import account.entities.Organizer;
import account.entities.User;
import event.entities.Event;
import gateways.*;
import message.entities.Message;
import rate.Rate;
import request.entities.UserRequest;
import room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmptyBackDoor {

    private static void createData() {
        UserGateway ugw = new UserGateway();
        EventGateway egw = new EventGateway();
        MessageGateway mgw = new MessageGateway();
        RoomGateway rmgw = new RoomGateway();
        UserRequestGateway rqgw = new UserRequestGateway();
        RateGateway rtgw = new RateGateway();

        List<User> users = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<UserRequest> requests = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<HashMap<Integer, Rate>> rates = new ArrayList<>();

        // Create users
        Organizer org = new Organizer("org@admin.com", "123456", "Organizer", 1000);
        users.add(org);

        // write
        ugw.write(users);
        mgw.write(messages);
        egw.write(events);
        rqgw.write(requests);
        rmgw.write(rooms);
        rtgw.write(rates);
    }

    public static void main(String[] args) {
        createData();
    }
}
