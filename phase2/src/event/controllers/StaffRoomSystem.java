package event.controllers;

import account.useCases.AccountManager;
import event.presenters.StaffRoomSystemPresenter;
import gateways.IGateway;
import room.RoomAdmin;

import java.util.*;
import java.util.regex.PatternSyntaxException;

public class StaffRoomSystem extends EventSystem{

    private final RoomAdmin roomAdmin;
    private final StaffRoomSystemPresenter presenter;
    private final IGateway roomGateway;



    /**
     * Constructs a staff room system for staff to manage room and event.
     * @param currUser  The id of the current user
     * @param acm       The account manager that handles operations related accounts
     * @param eventGateway    The event gateway that handles data for event operations
     * @param roomGateway  The room gateway that handles data for room operations
     */
    public StaffRoomSystem(int currUser, AccountManager acm, IGateway eventGateway, IGateway roomGateway){
        super(currUser, acm, eventGateway, new StaffRoomSystemPresenter(), roomGateway);
        this.roomAdmin = new RoomAdmin(roomGateway, eb.getScheduleGenerator());
        this.presenter = new StaffRoomSystemPresenter();
        this.roomGateway = roomGateway;
        roomAdmin.addObserver(eb);
        roomAdmin.addObserver(eb.getScheduleGenerator());
        roomAdmin.updateRooms();
    }

    /**
     * Displays the detailed information for all rooms available
     */
    public void getAllRoomInfo (){
        presenter.presentGetAllRoomInfoIntroduction();
        List<Map<String, List<List<String>>>> roomsFullInfo = new ArrayList<>();
        for (int roomId : roomAdmin.getAllRoomId()) {
            roomsFullInfo.add(roomAdmin.getFullInfo(roomId, eb));
        }
        presenter.presentGetAllRoomInfoDisplay(roomsFullInfo);
    }

    /**
     * Takes user input and displays the detailed information of a specified room
     */
    public void getOneRoomInfo (){
        presenter.presentGetOneRoomInfoInstruction();
        int roomId;

        roomId = getValidRoomIdInput();
        if (roomId == 0) {
            return;
        }
        presenter.presentGetOneRoomInfoDisplay(roomAdmin.getFullInfo(roomId, eb));

    }

    /**
     * Takes user input and adds a room to the conference
     */
    public void addRoom() {
        presenter.presentAddRoomInstruction();
        String roomName;
        String location;
        boolean validEquipmentsList;
        List<String> equipmentList = new ArrayList<>();
        String equipments;
        int capacity;

        presenter.presentAddRoomNameInstruction();
        roomName = getValidStringInput();
        if (roomName.equals("0")) {
            return;
        }

        presenter.presentAddRoomLocationInstruction();
        location = getValidStringInput();
        if (location.equals("0")) {
            return;
        }

        do {
            presenter.presentAddRoomEquipmentInstruction();
            equipments = getValidStringInput();
            if (equipments.equals("0")) {
                return;
            }
            if (equipments.equals("No")) {
                break;
            }
            try {
                equipmentList = Arrays.asList(equipments.split(", "));
                validEquipmentsList = true;
            } catch (PatternSyntaxException e) {
                presenter.presentAddRoomEquipmentInvalidInput();
                s.next();
                validEquipmentsList = false;
            }
        } while (!validEquipmentsList);

        presenter.presentAddRoomCapacityInstruction();
        capacity = getValidIntInput();
        if (capacity == 0) {
            return;
        }

        if (addRoomConfirm() == 0 ) {
            return;
        }

        boolean success = roomAdmin.addRoom(roomName, location, equipmentList, capacity, roomGateway);
        presenter.presentAddRoomResult(success);
    }

    private int addRoomConfirm() {

        presenter.presentAddRoomConfirmInstruction();
        do {
            int confirm = getValidIntInput();
            if (!(confirm == 0 || confirm == 1)) {
                presenter.presentInvalidConfirmInput();

            } else {
                return confirm;
            }
        } while (true);
    }


    /**
     * Takes user input and removes a room from the conference
     */
    public void removeRoom() {
        presenter.presentRemoveRoomInstruction();
        int roomId = getValidRoomIdInput();
        if (roomId == 0) {
            return;
        }
        boolean success = roomAdmin.removeRoom(roomId, roomGateway, eb);
        presenter.presentRemoveRoomResult(success);
    }

    /**
     * Takes a user input and edit the information or equipments about a room in the conference
     */
    public void editRoom(){

        int choice;

        int roomId;
        presenter.presentEditRoomInstruction();
        do {
            choice = getValidIntInput();
            if (choice == 0) {
                return;
            }
         else if (choice==1 || choice==2 || choice==3 || choice==4) {

            break;
        }
            presenter.presentInvalidChoiceMessage();
        }while(true);

        presenter.presentEditRoomIdInstruction();
        roomId = getValidRoomIdInput();
        if (roomId == 0) {
            return;
        }

        navigateSetRoomCommand(roomId, choice);

    }

    private void setRoomEquipment(int roomId){
        int equipChoice;
        String equipName;
        boolean success;

        presenter.presentSetEquipmentOptionInstruction();
        do {
            equipChoice = getValidIntInput();
            if (equipChoice == 0) {
                return;
            } else if (!(equipChoice == 1 || equipChoice == 2)) {
                presenter.presentSetEquipmentOptionInvalidInput();
                continue;
            }
            break;
        } while (true);
        if (equipChoice == 1) {
            presenter.presentSetEquipmentAddEquipmentName();
            equipName = getValidStringInput();
            if (equipName.equals("0")) {
                return;
            }
            success = roomAdmin.addEquip(roomId, equipName, roomGateway);

            presenter.presentSetEquipmentAddEquipmentResult(success);


        } else {
            presenter.presentSetEquipmentRemoveEquipmentName();
            equipName = getValidStringInput();
            if (equipName.equals("0")) {
                return;
            }
            success = roomAdmin.removeEquip(roomId, equipName, roomGateway);

            presenter.presentSetEquipmentRemoveEquipmentResult(success);

        }
    }

    private void navigateSetRoomCommand (int roomId, int choice){
        boolean success;
        if (choice == 1) {
            String newName;

            presenter.presentSetNameInstruction();
            newName = getValidStringInput();
            if (newName.equals("0")) {
                return;
            }
            success = roomAdmin.setName(roomId, newName, roomGateway);
            presenter.presentSetNameResult(success);
        } else if (choice == 2) {
            String newLocation;

            presenter.presentSetLocationInstruction();
            newLocation = getValidStringInput();
            if (newLocation.equals("0")) {
                return;
            }
            success = roomAdmin.setLocation(roomId, newLocation, roomGateway);
            presenter.presentSetLocationResult(success);

        } else if (choice == 3) {
            int newCapacity;

            presenter.presentSetCapacityInstruction();
            newCapacity = getValidIntInput();

            if (newCapacity == 0) {
                return;
            }
            success = roomAdmin.setCapacity(roomId, newCapacity, roomGateway, eb);
            presenter.presentSetCapacityResult(success);

        } else {
             setRoomEquipment(roomId);
        }
    }





    private int inputRoomId() {
        try {
            int roomId = s.nextInt();
            s.nextLine();
            if (roomId == 0) {
                return 0;
            }
            if (!roomAdmin.getAllRoomId().contains(roomId)) {
                presenter.presentRoomNotFound();
                return -1;
            }
            return roomId;
        } catch (InputMismatchException e) {
            presenter.presentInputError();
            s.next();
        }
        return -1;
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
            if (name == -1){
                presenter.presentInputError();
            }
            return name;
        } catch (InputMismatchException e) {
            presenter.presentInputError();
            s.next();
        }
        return -1;
    }

    private String getValidStringInput() {
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

    private int getValidIntInput() {
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

    private int getValidRoomIdInput() {
        int roomId;
        do {
            roomId = inputRoomId();
            if (roomId == -1) {
                continue;
            }
            break;
        } while (true);
        return roomId;
    }

    /**
     * Takes a staff input and makes an staff event/room system operation
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
                getAllRoomInfo();
                break;
            case 5:
                getOneRoomInfo();
                break;
            case 6:
                addRoom();
                break;
            case 7:
                removeRoom();
                break;
            case 8:
                editRoom();
                break;
        }
    }

    /**
     * Runs the event/room system of a staff
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        int choice;
        do {
            presenter.presentMenu();
            choice = getValidIntInput();
            if (choice >= 0 && choice <= 8) {
                navigateCommand(choice);
            } else {
                presenter.presentCommandError();
            }
        } while (choice != 0);
        return 0;
    }
}
