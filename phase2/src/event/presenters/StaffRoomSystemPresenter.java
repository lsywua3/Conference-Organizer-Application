package event.presenters;

import java.util.List;
import java.util.Map;

public class StaffRoomSystemPresenter extends EventSystemPresenter {
    /**
     * Present the menu for staff room/event system
     */
    @Override
    public void presentMenu() {
        System.out.println("----------------\nStaff Room/Event System Menu: ");
        System.out.println("" +
                "[1] Get all events\n" +
                "[2] Get all schedule\n" +
                "[3] Get information for specific event\n" +
                "[4] Get information for all rooms\n" +
                "[5] Get information for one room\n" +
                "[6] Add one room\n" +
                "[7] Remove one room\n" +
                "[8] Edit one room\n" +
                "[0] Exit");
    }

    /**
     * Present the introduction for getting all the detailed room introduction
     */
    public void presentGetAllRoomInfoIntroduction() {
        System.out.println("Here are the information of all the rooms.");
    }

    /**
     * Displays the detailed information (room information + occupied time) for all the rooms in this conference
     *
     * @param roomsFullInfo The full info for all the rooms in this conference
     */
    public void presentGetAllRoomInfoDisplay(List<Map<String, List<List<String>>>> roomsFullInfo) {

        if (roomsFullInfo.size() == 0) {
            System.out.println("There is no room in this conference.");
            return;
        }
        for (Map<String, List<List<String>>> fullInfo : roomsFullInfo) {
            System.out.println();
            presentGetOneRoomInfoDisplay(fullInfo);
        }
    }

    /**
     * Present the instruction for getting one room detailed information
     */
    public void presentGetOneRoomInfoInstruction() {
        System.out.println("Please enter the <room Id> of the room that you want to access information to, enter [0] to " +
                "exit");
    }

    /**
     * Displays the detailed information (room information + occupied time) of one room specified by the user
     *
     * @param roomFullInfo The full room information about a room specified by an user
     */
    public void presentGetOneRoomInfoDisplay(Map<String, List<List<String>>> roomFullInfo) {
        if (roomFullInfo == null) {
            presentRoomNotFound();
            return;
        }
        System.out.println("\tRoom [" + roomFullInfo.get("specs").get(0).get(0) + "]:");
        System.out.println("\t\tName: " + roomFullInfo.get("specs").get(0).get(1));
        System.out.println("\t\tLocation: " + roomFullInfo.get("specs").get(0).get(2));
        System.out.println("\t\tEquipments: " + roomFullInfo.get("specs").get(0).get(3));
        System.out.println("\t\tCapacity: " + roomFullInfo.get("specs").get(0).get(4));
        System.out.println("\t\tTime occupied: ");
        if (roomFullInfo.get("occupation").size() == 0) {
            System.out.println("Not occupied");
        } else {
            for (List<String> event : roomFullInfo.get("occupation")) {
                System.out.println("\t\tDay: " + event.get(2) + "\tStart time: " + event.get(0) + "\tDuration: " + event.get(1));
            }
        }
    }

    /**
     * Present the instruction for adding a room
     */
    public void presentAddRoomInstruction() {
        System.out.println("Adding room\nPlease enter room information.\n-----------");
    }

    /**
     * Present the instruction for add a room name
     */
    public void presentAddRoomNameInstruction() {
        System.out.println("- Please enter the <name> of the room, enter [0] to exit");
    }

    /**
     * Present the instruction for add a room location
     */
    public void presentAddRoomLocationInstruction() {
        System.out.println("- Please enter the <location> of the room, enter [0] to exit");
    }

    /**
     * Present the instruction for add equipments for a room
     */
    public void presentAddRoomEquipmentInstruction() {
        System.out.println("- Please enter the <equipments> of the room in format of \"microphone, blackboard, tables\"" +
                "\nenter [No] if there is no equipment for this room; enter [0] to exit; ");
    }

    /**
     * Present the message for an invalid input for adding a room equipment
     */
    public void presentAddRoomEquipmentInvalidInput() {
        System.out.println("Invalid input for equipments, please try again");
    }

    /**
     * Present the instruction for adding a room's capacity
     */
    public void presentAddRoomCapacityInstruction() {
        System.out.println("Please enter the <capacity> of this room, enter [0] to exit.");
    }

    /**
     * Present the instruction for confirming adding a room
     */
    public void presentAddRoomConfirmInstruction() {
        System.out.println("Add this room? \n [1] Confirm [0] Cancel");
    }

    /**
     * Present the message for a invalid confirm input
     */
    public void presentInvalidConfirmInput() {
        System.out.println("Invalid input, please try again");
    }

    /**
     * Present the message for the result of adding a room
     *
     * @param success A boolean saying whether the room has been added
     */
    public void presentAddRoomResult(boolean success) {
        if (success) {
            System.out.println("A room has been successfully added!");
        } else {
            System.out.println("Failed to add this room, please try again!");
        }
    }

    /**
     * Present the instruction for removing a room
     */
    public void presentRemoveRoomInstruction() {
        System.out.println("Please enter the <room id> of the room that you want to remove; enter [0] to exit");
    }

    /**
     * Present the message for the result of removing a room
     *
     * @param success A boolean saying whether the user has been removed
     */
    public void presentRemoveRoomResult(boolean success) {
        if (success) {
            System.out.println("Room has been successfully removed!");
        } else {
            System.out.println("Failed to remove room, please try again. At least one event is being held here.");
        }
    }

    /**
     * Presents the instruction for editing a room
     */
    public void presentEditRoomInstruction() {
        System.out.println("Which information do you want to edit?");
        System.out.println("[1] Set Name");
        System.out.println("[2] Set Location");
        System.out.println("[3] Set Capacity");
        System.out.println("[4] Set Equipment");
        System.out.println("[0] Exit");
    }

    /**
     * Presents the instruction for editing a room
     */
    public void presentEditRoomIdInstruction() {
        System.out.println("Please enter the <room id> of the room that you want to make change to, enter [0] to exit");
    }

    /**
     * Presents the instruction for setting the name of a room
     */
    public void presentSetNameInstruction() {
        System.out.println("Please enter the <new name>, enter [0] to exit");
    }

    /**
     * Presents the result for setting the name of a room
     *
     * @param success A boolean saying whether the name has been edited
     */
    public void presentSetNameResult(boolean success) {
        if (success) {
            System.out.println("Name has been successfully edited!");
        } else {
            System.out.println("Failed to edit new name, please try again!");
        }
    }

    /**
     * Presents the instruction for setting the location of a room
     */
    public void presentSetLocationInstruction() {
        System.out.println("Please enter the <new location>, enter [0] to exit");
    }

    /**
     * Presents the result for whether the room location has been changed
     *
     * @param success A boolean saying whether the location has been edited
     */
    public void presentSetLocationResult(boolean success) {
        if (success) {
            System.out.println("Location has been successfully edited!");
        } else {
            System.out.println("Failed to edit location, please try again!");
        }
    }

    /**
     * Presents the instruction for setting the capacity of a room
     */
    public void presentSetCapacityInstruction() {
        System.out.println("Please enter the <new capacity>");
    }

    /**
     * Presents the result for setting the capacity of a room
     *
     * @param success A boolean representing whether the capacity of the room has been edited
     */
    public void presentSetCapacityResult(boolean success) {
        if (success) {
            System.out.println("Capacity is successfully edited!");
        } else {
            System.out.println("Failed to set capacity, please try again. At least one event in this room requires" +
                    " a greater capacity than the one you have entered.");
        }
    }

    /**
     * Presents the instruction for the options for setting the equipments of a room
     */
    public void presentSetEquipmentOptionInstruction() {
        System.out.println("Edit equipments:");
        System.out.println("[1] Add Equipment");
        System.out.println("[2] Remove Equipment");
        System.out.println("[0] Exit");
    }

    /**
     * Presents the message for invalid input for the set equipment options
     */
    public void presentSetEquipmentOptionInvalidInput() {
        System.out.println("Invalid option, please try again!");
    }

    /**
     * Presents the instruction for asking for the name of the new equipment to be added to a room
     */
    public void presentSetEquipmentAddEquipmentName() {
        System.out.println("Please enter <name> of one equipment that you want to add to this room:");
    }

    /**
     * Presents the result for adding the equipment to a room
     *
     * @param success A boolean saying whether the equipment has been added to the room
     */
    public void presentSetEquipmentAddEquipmentResult(boolean success) {
        if (success) {
            System.out.println("You have successfully added this equipment!");
        } else {
            System.out.println("Failed to add equipment, please try again. The equipment already exists.");
        }
    }

    /**
     * Presents the instruction for asking for the name of the equipment that the user wants to remove from the room
     */
    public void presentSetEquipmentRemoveEquipmentName() {
        System.out.println("Please enter <name> of the equipment that you want to remove (case matters)");
    }

    /**
     * Presents the result for removing the equipment
     *
     * @param success A boolean saying whether the equipment has been successfully removed
     */
    public void presentSetEquipmentRemoveEquipmentResult(boolean success) {
        if (success) {
            System.out.println("You have successfully removed this equipment");
        } else {
            System.out.println("Failed to remove equipment, please try again. The room does not contain the " +
                    "equipment you have entered. Case does matter.");
        }
    }

    /**
     * Presents the message for room not found
     */
    public void presentRoomNotFound() {
        System.out.println("Room not be found, please try again!");
    }

    /**
     * Present the message for a invalid choice input
     */
    public void presentInvalidChoiceMessage() {
        System.out.println("This choice is invalid, please try again later");
    }

}
