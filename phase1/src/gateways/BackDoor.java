package gateways;

import entities.*;

import java.util.ArrayList;

public class BackDoor {

    private static void createData() {
        UserGateway ugw = new UserGateway();
        EventGateway egw = new EventGateway();
        MessageGateway mgw = new MessageGateway();

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();

        // Create users
        Attendee atd1 = new Attendee("atd1@admin.com", "123456", "Yichuan", 1001);
        Attendee atd2 = new Attendee("atd2@admin.com", "123456", "Xiangshan", 1002);
        Speaker spk1 = new Speaker("spk1@admin.com", "123456", "Zhongni", 3501);
        Speaker spk2 = new Speaker("spk2@admin.com", "123456", "Micius", 3502);
        Organizer org = new Organizer("org@admin.com", "123456", "Zixiu", 9990);
        users.add(spk1);
        users.add(spk2);
        users.add(org);
        users.add(atd1);
        users.add(atd2);

        // Create speakers
        ArrayList<Integer> speakers = new ArrayList<>();
        speakers.add(spk1.getId());
        events.add(new Event(2001, "Classic of Changes", speakers, 101, 12, 1, 2));
        events.add(new Event(2002, "Classic of History", speakers, 202, 10, 1, 2));
        spk1.addTalk(events.get(0).getId());
        spk1.addTalk(events.get(1).getId());

        // Create messages
        messages.add(new Message(2501, "Hello World!", atd1.getId(), atd2.getId()));
        messages.add(new Message(2502, "Good Afternoon, Good Evening and Good Night.", atd1.getId(), spk1.getId()));
        atd1.addSentMessage(messages.get(0).getId());
        atd2.addReceivedMessage(messages.get(0).getId());
        atd1.addSentMessage(messages.get(0).getId());
        spk1.addReceivedMessage(messages.get(1).getId());

        ugw.write(users);
        mgw.write(messages);
        egw.write(events);
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
