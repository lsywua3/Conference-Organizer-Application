package event.presenters;

import java.util.List;
import java.util.Map;

public class OrganizerEventSystemPresenter extends EventSystemPresenter {
    @Override
    public void presentMenu() {
        System.out.println("----------------\nEvent System Menu: ");
        System.out.println("" +
                "[1] Get all events\n" +
                "[2] Get all schedule\n" +
                "[3] Get information for specific event\n" +
                "[4] Create a new event\n" +
                "[5] Cancel an event\n" +
                "[6] Edit an event\n" +
                "[7] Reschedule an event\n" +
                "[8] Create a new account\n" +
                "[0] Exit");
    }


    /**
     * Present a list a speaker ids.
     *
     * @param speakers List of Integer containing speaker ids.
     */
    public void presentSpeakers(List<Integer> speakers) {
        System.out.println("All Speakers:");
        StringBuilder result = new StringBuilder();
        for (int id : speakers) {
            result.append("[").append(id).append("], ");
        }

        System.out.println(result);
    }

    /**
     * Display the instruction for addEvent.
     * "System.out.println("Please enter information for the event: ");"
     */
    public void presentAddEventInstruction() {
        System.out.println("Please enter information for the event: ");
    }

    /**
     * Display the instruction for add title of the event.
     */
    public void presentReqTitle() {
        System.out.print("<title>: ");
    }

    /**
     * Display the instruction for add speakers of the event.
     */
    public void presentReqSpeakerIds() {

        System.out.println("For speaker id, please input in format of: 1003, 1004");
        System.out.println("For no speaker event, please leave it empty and press enter");
        System.out.print("<speaker ids>: ");

    }

    /**
     * Display the instruction for add room of the event.
     */
    public void presentReqRoom() {
        System.out.print("<room>: ");
    }

    /**
     * Display the instruction for add capacity of the event.
     */
    public void presentReqCapacity() {
        System.out.print("<capacity>: ");
    }

    /**
     * Display the instruction for add time of the event.
     */
    public void presentReqTime() {
        System.out.print("<time>: ");
    }

    /**
     * Display the instruction for add event duration of the event.
     */
    public void presentReqDuration() {
        System.out.print("<duration>: ");
    }

    /**
     * Display the instruction for add event date of the event.
     */
    public void presentReqDay() {
        System.out.print("<day>: ");
    }

    /**
     * Display the result of add event.
     */
    public void presentAddEventResult(boolean success) {
        if (success) {
            System.out.println("Event has been successfully added!");
        } else {
            System.out.println("Event adding failed!");
        }
    }

    /**
     * Display the instruction of remove an event.
     */
    public void presentRemoveEventInstruction() {
        System.out.println("Please enter <event id> of the event that you want to remove; Enter [0] to exit.");
    }

    /**
     * Display the result of remove an event.
     * @param success whether the event has been successfully removed.
     * @param eventId the id of event want to remove
     */
    public void presentRemoveEventResult(boolean success, int eventId) {
        if (success) {
            System.out.println("Successfully cancelled Event [" + eventId + "]!");
        } else {
            System.out.println("Event cancelling failed!");
        }
    }

    /**
     * Display the instruction of edit an event.
     */
    public void presentEditEventInstruction() {
        System.out.println("Please enter <event id> of event that you want to edit; Enter [0] to exit. ");
    }

    /**
     * Display the result of edit and event.
     * @param success whether the event had been successfully edited.
     */
    public void presentEditEventResult(boolean success) {
        if (success) {
            System.out.println("Event has been successfully edited!");
        } else {
            System.out.println("Event edit failed!");
        }
    }

    /**
     * Display the instruction of reschedule an event.
     */
    public void presentRescheduleInstruction() {
        System.out.println("Please enter <event id> of event that you want to reschedule; Enter [0] to exit. ");
    }

    /**
     * Display the result of reschedule an event
     * @param success whether this event had been successfully rescheduled.
     */
    public void presentRescheduleResult(boolean success) {
        if (success) {
            System.out.println("Event has been successfully rescheduled!");
        } else {
            System.out.println("Event rescheduling failed!");
        }
    }

    /**
     * Display the instruction of create a new account.
     */
    public void presentCreateAccountInstruction() {
        System.out.println("Please enter <account type>: ");
    }

    /**
     * Display the result of create a new account.
     * @param success whether this account has been successfully created.
     */
    public void presentCreateAccountResult(boolean success) {
        if (success) {
            System.out.println("Account has been successfully Created");
        } else {
            System.out.println("Invalid user type, please try again. (Possible Account Types: Attendee, Organizer, " +
                    "Speaker, Staff");
        }
    }



    /**
     * Display all equipments
     *
     * @param equipments List of Strings
     */
    public void presentAllEquipments(List<String> equipments) {

        if (equipments.size() == 0) {
            System.out.println("There is no available equipment any room.");
        }
        for (int i = 0; i < equipments.size(); i++) {
            System.out.print("[" + (i + 1) + "] " + equipments.get(i) + "\t");
        }
        System.out.println();
    }

    /**
     * Display instruction.
     */
    public void presentEquipmentRequirementsInstruction() {
        System.out.println("These are all the equipments that we may offer, please enter the <index> of equipments" +
                " you want in the format of: 2, 5, 10 ");
        System.out.println("enter [0] to exit; " +
                "enter [empty] if you do not have any equipment requirements.");
    }

    /**
     * Display room options
     *
     * @param satisfiedRooms List of Integers
     */
    public void presentRoomOptions(List<Integer> satisfiedRooms) {

        if (satisfiedRooms.size() == 0){
            System.out.println("There is no room that match your requirements");
            System.out.println("Please enter [0] to reenter your equipment requirements");
            return;
        }
        System.out.println("Here are the rooms that contain your required equipment:");
        for(int i=0; i<satisfiedRooms.size(); i++){
            System.out.println("["+(i+1)+"]  "+ "room"+satisfiedRooms.get(i));
        }
        System.out.println("Please enter <index> of the room to view details; " +
                "enter [0] to change your equipment requirements");
    }

    /**
     * "Please enter the equipment requirements:"
     */
    public void presentReenterEquipmentRequirementOption() {
        System.out.println("Please enter the equipment requirements:");
    }

    /**
     * "Invalid choice, please try again!"
     */
    public void presentInvalidChoice() {
        System.out.println("Invalid choice, please try again!");
    }


    /**
     * Display a details of rooms
     * @param fullInfo map of info
     */
    public void presentRoomDetailedInformation(Map<String, List<List<String>>> fullInfo) {
        //Todo: Finish this method!

        String name = fullInfo.get("specs").get(0).get(0);
        System.out.println("ID: "+name);
        System.out.println("Name: "+fullInfo.get("specs").get(0).get(1));
        System.out.println("Location: "+fullInfo.get("specs").get(0).get(2));
        System.out.println("Equipments: "+fullInfo.get("specs").get(0).get(3));
        System.out.println("Capacity: "+fullInfo.get("specs").get(0).get(4));
        System.out.println("Time occupied: ");

        if (fullInfo.get("occupation").size()==0){
            System.out.println("\t\tThis room is not occupied by any event yet");
        } else {
            for (List<String> event : fullInfo.get("occupation")) {
                System.out.println("\tDay: " + event.get(2) + "\tStart time: " + event.get(0) + "\tDuration: "
                        + event.get(1));
            }
        }
    }

    /**
     * "Confirm? Enter [1] to use this room; enter [0] to check another room."
     */
    public void presentRoomConfirmation() {
        System.out.println("Confirm? Enter [1] to use this room; enter [0] to check another room.");
    }

    /**
     * "This is an invalid input, please try again!"
     */
    public void presentInvalidConfirmation() {
        System.out.println("This is an invalid input, please try again!");
    }

    /**
     * "Please enter index of the room to view details, enter [0] to change you equipment requirements;"
     */
    public void presentReenterRoomChoiceMessage() {
        System.out.println("Please enter <index> of the room to view details, enter [0] to change you equipment " +
                "requirements;");
    }



    /**
     * ""Enter [1] to change room; enter [0] to keep using this room;"
     */
    public void presentChangeRoomInstruction() {
        System.out.println("Enter [1] to change room; enter [0] to keep using this room;");
    }


}
