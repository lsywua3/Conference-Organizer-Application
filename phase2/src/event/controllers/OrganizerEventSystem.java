package event.controllers;

import account.controllers.AccountSystem;
import account.useCases.AccountManager;
import event.presenters.OrganizerEventSystemPresenter;
import event.useCases.EventAdmin;
import gateways.IGateway;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.PatternSyntaxException;


/**
 * Event System for an organizer. User can perform operations related to events.
 */
public class OrganizerEventSystem extends EventSystem {
    private final EventAdmin eventAdmin;
    private final IGateway userGateway;
    private final OrganizerEventSystemPresenter presenter = new OrganizerEventSystemPresenter();

    /**
     * Constructs event system for an organizer.
     *
     * @param currUser     int id of the current user.
     * @param acm          AccountManager.
     * @param eventGateway EventGateway for read and write.
     * @param userGateway  UserGateway for read and write.
     */
    public OrganizerEventSystem(int currUser, AccountManager acm, IGateway eventGateway, IGateway userGateway,
                                IGateway roomGateway) {
        super(currUser, acm, eventGateway, new OrganizerEventSystemPresenter(), roomGateway);
        this.userGateway = userGateway;
        eventAdmin = new EventAdmin(eventGateway);
        rm.addObserver(eventAdmin);
        rm.addObserver(eventAdmin.getScheduleGenerator());
        rm.updateRooms();
    }

    /**
     * Takes user inputs to add an event to the conference.
     *
     * @return True if the event is added; False if the event is not added.
     */
    public boolean addEvent() {

        displayAllSchedule();
        presenter.presentAddEventInstruction();
        presenter.presentAllEquipments(rm.getEquip());
        presenter.presentEquipmentRequirementsInstruction();
        int roomId;
        boolean success;

        roomId = inputRoomFromEquipmentSelection();
        if (roomId == -1) {
            return false;
        }
        presenter.presentSpeakers(acm.getSpeakersIds());
        presenter.presentAddEventInstruction();
        List<String> eventInfoInput = inputEventInfo();
        if (eventInfoInput == null) {
            return false;
        }
        String title = eventInfoInput.get(0);
        String speakers = eventInfoInput.get(1);
        int capacity = Integer.parseInt(eventInfoInput.get(2));

        List<Integer> eventScheduleInput = inputEventSchedule();
        if (eventScheduleInput == null) {
            return false;
        }
        int day = eventScheduleInput.get(0);
        int time = eventScheduleInput.get(1);
        int duration = eventScheduleInput.get(2);

        // Start adding the event
        success = eventAdmin.addEvent(eventGateway, userGateway, acm, rm, title, convertToArrayList(speakers),
                roomId, time, duration, day, capacity);
        presenter.presentAddEventResult(success);

        if (success) {
            eb.reload(eventGateway);
        }
        return success;
    }

    /**
     * Takes the user input of an id of event and remove this event from the conference.
     *
     * @return True if the event is removed; False if the event is not removed.
     */
    public boolean removeEvent() {
        boolean legalInput = false;
        getAllEvents();
        do {
            try {
                presenter.presentRemoveEventInstruction();
                int eventId = getValidEventIdInput();
                if (eventId == 0) {
                    return false;
                }

                legalInput = true;
                boolean success = eventAdmin.removeEvent(eventGateway, userGateway, acm, eventId);
                presenter.presentRemoveEventResult(success, eventId);
                if (success) {
                    eb.reload(eventGateway);
                }
                return success;
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
        return false;
    }

    /**
     * Takes user input to edit the information of an event.
     *
     * @return True if the edit is successful; False if not.
     */
    public boolean editEvent() {

        int roomId;
        presenter.presentEditEventInstruction();
        // Ask for event Id:
        int eventId = getValidEventIdInput();
        if (eventId == 0) {
            return false;
        }

        // Present Current Event info:
        presenter.presentCurrently();
        presenter.presentEvent(eb.getInfoList(eventId, acm));
        presenter.presentLine();

        List<String> eventInfoInput = inputEventInfo();
        if (eventInfoInput == null) {
            return false;
        }
        String title = eventInfoInput.get(0);
        String speakers = eventInfoInput.get(1);
        int capacity = Integer.parseInt(eventInfoInput.get(2));

        List<Integer> eventScheduleInput = inputEventSchedule();
        if (eventScheduleInput == null) {
            return false;
        }
        int day = eventScheduleInput.get(0);
        int time = eventScheduleInput.get(1);
        int duration = eventScheduleInput.get(2);
        // Ask if the user wants to change the room, if so, provide room recommendation & ask user to select a room
        // and get the room Id
        presenter.presentChangeRoomInstruction();
        int confirm = inputValidInt();
        while (confirm != 0 && confirm != 1) {
            presenter.presentInvalidConfirmation();
            confirm = inputValidInt();
        }
        if (confirm == 1) {
            presenter.presentAllEquipments(rm.getEquip());
            presenter.presentEquipmentRequirementsInstruction();
            roomId = inputRoomFromEquipmentSelection();
            if (roomId == -1) {
                return false;
            }
        } else {
            roomId = Integer.parseInt(eb.getInfo(eventId, "room"));
        }

        boolean success = eventAdmin.setEvent(eventGateway, userGateway, acm, rm, eventId, title,
                convertToArrayList(speakers), roomId, time,
                duration, day, capacity);
        presenter.presentEditEventResult(success);

        if (success) {
            eb.reload(eventGateway);
        }
        return success;
    }

    /**
     * Takes user input to reschedule the time and room of an event.
     *
     * @return True if the reschedule is successful; False if not.
     */
    public boolean rescheduleEvent() {

        displayAllSchedule();
        int roomId;
        int eventId;
        boolean success;
        int confirm;
        presenter.presentRescheduleInstruction();
        // Ask for event id
        eventId = getValidEventIdInput();
        if (eventId == 0) {
            return false;
        }
        // present current event information
        presenter.presentCurrently();
        presenter.presentEvent(eb.getInfoList(eventId, acm));
        presenter.presentLine();

        List<Integer> eventScheduleInput = inputEventSchedule();
        if (eventScheduleInput == null) {
            return false;
        }
        int day = eventScheduleInput.get(0);
        int time = eventScheduleInput.get(1);
        int duration = eventScheduleInput.get(2);

        presenter.presentChangeRoomInstruction();
        confirm = inputValidInt();
        while (confirm != 0 && confirm != 1) {
            presenter.presentInvalidConfirmation();
            confirm = inputValidInt();
        }
        if (confirm == 1) {
            presenter.presentAllEquipments(rm.getEquip());
            presenter.presentEquipmentRequirementsInstruction();
            roomId = inputRoomFromEquipmentSelection();
            if (roomId == -1) {
                return false;
            }
        } else {
            roomId = Integer.parseInt(eb.getInfo(eventId, "room"));
        }

        success = eventAdmin.rescheduleEvent(eventGateway, eventId, roomId,
                time, duration, day);
        presenter.presentRescheduleResult(success);
        if (success) {
            eb.reload(eventGateway);
        }
        return success;
    }


    /**
     * Take user input for scheduling.
     *
     * @return A List of String in format of [title, speakers, capacity]
     */
    private List<String> inputEventInfo() {

        presenter.presentReqTitle();
        String title = inputValidString();
        if (title.equals("0")) {
            return null;
        }
        // Ask for the string of speakers ids
        presenter.presentReqSpeakerIds();
        String speakers;
        do {
            speakers = inputValidString();
            if (speakers.equals("0")) {
                return null;
            }
            if (!speakers.isEmpty()) {
                try {
                    String[] ls = speakers.split(", ");
                    for (String spk : ls) {
                        Integer.parseInt(spk);
                    }
                } catch (PatternSyntaxException | NumberFormatException e){
                    presenter.presentInputError();
                    continue;
                }
            }
            break;
        }while(true);
        // Ask for capacity
        presenter.presentReqCapacity();
        int capacity = inputValidInt();
        if (capacity == 0) {
            return null;
        }
        List<String> result = new ArrayList<>();
        result.add(title);
        result.add(speakers);
        result.add(Integer.toString(capacity));
        return result;
    }

    /**
     * Convert input speakers to a List
     *
     * @param speakers speaker ids in format of "1003, 2005".
     * @return List containing given speaker ids.
     */
    private List<Integer> convertToArrayList(String speakers) {
        List<Integer> result = new ArrayList<>();
        // TODO: test this method
        if (speakers.isEmpty()) {
            return result;
        }
        else {
            String[] ls = speakers.split(", ");
            for (String spk : ls) {
                result.add(Integer.parseInt(spk));
            }
        }
        return result;
    }

    /**
     * Take user input for scheduling.
     *
     * @return A List of Integer in format of [room, time, duration]
     */
    private List<Integer> inputEventSchedule() {
        presenter.presentReqDay();
        int day = inputValidInt();
        if (day == 0) {
            return null;
        }
        // Ask for start time
        presenter.presentReqTime();
        int time = inputValidInt();
        if (time == 0) {
            return null;
        }
        // Ask for duration
        presenter.presentReqDuration();
        int duration = inputValidInt();
        if (duration == 0) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        result.add(day);
        result.add(time);
        result.add(duration);
        return result;

    }

    /**
     * Take user inputs to create account.
     * The user can create a "speaker", "organizer", "attendee", or "staff" account.
     */
    public void createAccount() {
        AccountSystem accountSystem = new AccountSystem(userGateway, acm);
        String type;
        boolean registered = false;
        do {
            presenter.presentCreateAccountInstruction();
            type = s.nextLine().toLowerCase();
            if (type.equals("speaker") || type.equals("organizer")
                    || type.equals("attendee") || type.equals("staff")) {
                accountSystem.register(type);
                registered = true;
            }
            presenter.presentCreateAccountResult(registered);
        } while (!registered);
    }

    /**
     * Takes an command and make organizer event system operations.
     *
     * @param command an integer represents the operation.
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                getAllEvents();
                break;
            case 2:
                getAllSchedule();
                break;
            case 3:
                getEvent();
                break;
            case 4:
                addEvent();
                break;
            case 5:
                removeEvent();
                break;
            case 6:
                editEvent();
                break;
            case 7:
                rescheduleEvent();
                break;
            case 8:
                createAccount();
                break;
        }
    }

    /**
     * Runs the event system of an organizer.
     *
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                presenter.presentMenu();
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 8) {
                    navigateCommand(choice);
                } else {
                    presenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (choice != 0);
        return 0;
    }

    /**
     * Changes the event capacity.
     *
     * @return true if the capacity is reset successfully.
     */
    public boolean changeEventCapacity() {
        // Ask for event id
        int eventId = inputEventId();
        if (eventId == 0 || eventId == -1) {
            return false;
        }
        s.nextLine();
        // Ask for capacity
        presenter.presentReqCapacity();
        int capacity = inputValidInt();
        if (capacity == 0) {
            //break;
            return false;
        }
        s.nextLine();
        int roomId = Integer.parseInt(eb.getInfo(eventId, "room"));
        if (Integer.parseInt(rm.getInfo(roomId, "capacity")) <= capacity) {
            eventAdmin.resetCapacity(rm, eventId, capacity);
            return true;
        }
        return false;
    }

    private String inputStringInfo() {
        try {
            String name = s.nextLine();
            if (name.equals("0")) {
                return "0";
            } else if (name.equals("-1")){
                presenter.presentInputError();
            }
            return name;
        } catch (InputMismatchException e) {
            presenter.presentInputError();
            s.next();
        }
        return "-1";
    }

    private int inputIntInfo() {
        try {
            int name = s.nextInt();
            s.nextLine();
            if(name == -1){
                presenter.presentInputError();
            }
            return name;
        } catch (InputMismatchException e) {
            presenter.presentInputError();
            s.next();
        }
        return -1;
    }

    private String inputValidString() {
        String name;
        do {
            name = inputStringInfo();
            if (name.equals("-1")) {
                continue;
            }
            break;
        } while (true);
        return name;
    }

    private int inputValidInt() {
        int name;
        do {
            name = inputIntInfo();
            if (name == -1) {
                continue;
            }
            break;
        } while (true);
        return name;
    }


    private int getValidEventIdInput() {
        int eventId;
        do {
            eventId = inputEventId();
            if (eventId == -1) {
                continue;
            }
            break;
        } while (true);
        return eventId;
    }

    private List<String> inputValidEquipmentRequirements() {
        String equipmentRequirement;
        List<Integer> equipmentRequirementList;
        List<String> finalEquipmentRequirementList;
        do {
            try {
                equipmentRequirementList = new ArrayList<>();
                finalEquipmentRequirementList = new ArrayList<>();
                equipmentRequirement = inputValidString();
                if (equipmentRequirement.equals("0")) {
                    finalEquipmentRequirementList.add("0");
                    return finalEquipmentRequirementList;
                } else if (equipmentRequirement.equals("empty")) {
                    finalEquipmentRequirementList.add("-1");
                    return finalEquipmentRequirementList;
                }
                for (String equipment : equipmentRequirement.split(", ")) {
                    equipmentRequirementList.add(Integer.parseInt(equipment));
                }
                for (int equipIndex : equipmentRequirementList) {
                    finalEquipmentRequirementList.add(rm.getEquip().get(equipIndex - 1));
                }
                break;
            } catch (PatternSyntaxException | NumberFormatException | IndexOutOfBoundsException e) {
                presenter.presentInputError();
            }
        } while (true);

        return finalEquipmentRequirementList;
    }


    private int inputRoomFromEquipmentSelection() {
        List<String> equipmentRequirementList;
        List<Integer> satisfiedRooms;
        boolean startBooking;
        int roomConfirm;
        int roomId = 0;
        do {
            equipmentRequirementList = inputValidEquipmentRequirements();
            if (equipmentRequirementList.contains("0")) {
                return -1;
            } else if (equipmentRequirementList.size() == 1 && equipmentRequirementList.contains("-1")) {
                equipmentRequirementList = new ArrayList<>();
            }
            satisfiedRooms = rm.getRoomByEquip(equipmentRequirementList);
            presenter.presentRoomOptions(satisfiedRooms);
            startBooking = true;
            do {
                int choice = inputValidInt();
                if (choice == 0) {
                    presenter.presentReenterEquipmentRequirementOption();
                    startBooking = false;
                    break;
                }
                if (choice > satisfiedRooms.size() || choice < 0) {
                    presenter.presentInvalidChoice();
                    continue;
                }
                presenter.presentRoomDetailedInformation(rm.getFullInfo(satisfiedRooms.get(choice - 1), eb));
                presenter.presentRoomConfirmation();
                roomConfirm = inputValidInt();
                while (roomConfirm != 0 && roomConfirm != 1) {
                    presenter.presentInvalidConfirmation();
                    roomConfirm = inputValidInt();
                }
                if (roomConfirm == 1) {
                    roomId = satisfiedRooms.get(choice - 1);
                    break;
                } else {
                    presenter.presentReenterRoomChoiceMessage();
                }
            } while (true);
        } while (!startBooking);
        return roomId;

    }

}
