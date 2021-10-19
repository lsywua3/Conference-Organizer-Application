package data;

import account.entities.*;
import event.entities.Event;
import event.entities.MultiSpeakerEvent;
import event.entities.NoSpeakerEvent;
import event.entities.OneSpeakerEvent;
import gateways.*;
import message.entities.Message;
import request.entities.UserRequest;
import room.Room;

import java.util.ArrayList;
import java.util.List;

public class BackDoor {

    private static void createData() {
        UserGateway ugw = new UserGateway();
        EventGateway egw = new EventGateway();
        MessageGateway mgw = new MessageGateway();
        RoomGateway rmgw = new RoomGateway();
        UserRequestGateway rqgw = new UserRequestGateway();

        List<User> users = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<UserRequest> requests = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();

        // Create users
        Organizer org = new Organizer("org@admin.com", "123456", "Organizer", 1000);
        Attendee atd1 = new Attendee("atd1@admin.com", "123456", "Attendee 1", 1001);
        Attendee atd2 = new Attendee("atd2@admin.com", "123456", "Attendee 2", 1002);
        Speaker spk1 = new Speaker("spk1@admin.com", "123456", "Speaker 1", 1003);
        Speaker spk2 = new Speaker("spk2@admin.com", "123456", "Speaker 2", 1004);
        Staff stf1 = new Staff("stf1@admin.com", "123456", "Staff 1", 1005);
        Staff stf2 = new Staff("stf2@admin.com", "123456", "Staff 2", 1006);
        users.add(org);
        users.add(atd1);
        users.add(atd2);
        users.add(spk1);
        users.add(spk2);
        users.add(stf1);
        users.add(stf2);

        // Create speakers
        List<Integer> speakers0 = new ArrayList<>();
        List<Integer> speakers1 = new ArrayList<>();
        speakers1.add(spk1.getId());
        List<Integer> speakers2 = new ArrayList<>();
        speakers2.add(spk1.getId());
        speakers2.add(spk2.getId());

        // Create rooms
        Room ch = new Room(1000, "Convocation Hall", "CH", new ArrayList<>(), 5);
        ch.addEquipment("Projector");
        ch.addEquipment("Mic & Speaker");
        ch.addEquipment("Stage");
        rooms.add(ch);
        Room ba = new Room(1001, "Bahen", "BA", new ArrayList<>(), 10);
        ba.addEquipment("Desktops");
        ba.addEquipment("Projectors");
        ba.addEquipment("TV");
        ba.addEquipment("Mic & Speaker");
        ba.addEquipment("Round desks");
        ba.addEquipment("Outlets");
        rooms.add(ba);
        Room mark = new Room(1002, "Myhal", "MY", new ArrayList<>(), 5);
        mark.addEquipment("Podium");
        mark.addEquipment("Computers");
        mark.addEquipment("Monitor");
        rooms.add(mark);


        // Create events
        events.add(new NoSpeakerEvent(2001, "Party", speakers0, 1000, 12, 1, 1, 2));
        events.add(new OneSpeakerEvent(2002, "TedTalk", speakers1, 1001, 10, 5, 2, 2));
        events.add(new MultiSpeakerEvent(2003, "Talk Show", speakers2, 1002, 9, 8, 3, 5));

        // Create messages
        messages.add(new Message(2501, "Hello World!", atd1.getId(), atd2.getId()));
        messages.add(new Message(2502, "Good Afternoon, Good Evening and Good Night.", atd1.getId(), spk1.getId()));


        // Create rating


        // write
        ugw.write(users);
        mgw.write(messages);
        egw.write(events);
        rqgw.write(requests);
        rmgw.write(rooms);
    }

    public static void main(String[] args) {

        // Check users
//        UserGateway ugw = new UserGateway();
//        ArrayList<User> users = ugw.read();
//        System.out.println(users);

        // Check messages
//        MessageGateway mgw = new MessageGateway();
//        ArrayList<Message> messages = mgw.read();
//        System.out.println(messages);

        // Check messages
//        EventGateway egw = new EventGateway();
//        ArrayList<Event> events = egw.read();
//        System.out.println(events);
        createData();
    }
}
