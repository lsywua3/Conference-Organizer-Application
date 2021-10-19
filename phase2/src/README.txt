# User Guideline
===

To run the program:

    - Before running the program for the first time, please run data.BackDoor to set up preload data for the
      conference; run EmptyBackDoor to get clean data (only contains an Organizer account).

    - Run the main.Main to start the program.

We have seven default accounts in the program:
    - Organizer:
        - email: org@admin.com
        - password: 123456
    - Attendee1:
        - email: atd1@admin.com
        - password: 123456
    - Attendee1:
        - email: atd2@admin.com
        - password: 123456
    - Speaker1:
        - email: spk1@admin.com
        - password: 123456
    - Speaker2:
        - email: spk2@admin.com
        - password: 123456
    - Staff1:
        - email: stf1@admin.com
        - password: 123456
    - Staff2:
        - email: stf2@admin.com
        - password: 123456

Also three default events, two default messages, and 3 default rooms currently stored in the program.

In any menu, please enter the number shown in the square brackets [] to perform the following operation.

Room and time are labelled as R100 and 12:00, but your input should be 1000 for room ID and 12 for time (in hours).

The conference lasts for 3 days from 9:00 - 17:00.

Room management is in event menu of a Staff.

When performing any operation, please follow the instruction shown in console.

For organizer creating accounts, go to event menu.

# Mandatory Extensions
===
1. New user type: Staff
    - Staffs take the responsibility of room management, including adding, removing, set info, and change "equipments" of a room.

2. New event types:
    - MultiSpeakerEvent: has at least 2 speakers.
    - OneSpeakerEvent: can have only 1 speaker.
    - NoSpeakerEvent: 0 speaker!
    - When creating an event, it will automatically decide the event type according to the number of speakers you input.

# Option Extensions
===
1. Enhance the user's messaging experience by allowing them to "mark as unread", delete, or archive messages after reading them.
    - @ message.useCases.MessageManager
    - Received messages now has status: read, unread and achieved.
    - User can mark their messages to different status and view message of a certain status.

2. Have the program produce a neatly formatted program or schedule for the conference that users have the option of "downloading".
    - @ event.gateway.ScheduleHTMLDownloader
    - The schedule of events can now be download into a html file.
    - A download option will appear after viewing schedule in event menu.
    - Downloaded file will be stored in the root directory.

3. Add additional constraints to the scheduling for various types of events (e.g. technology requirements for presentations, tables vs rows of chairs). When organizers are creating events, they can see a suggested list of rooms that fit the requirements of their event.
    - @ room
    - Rooms now have equipments list.
    - Staffs can customize equipments of each room.
    - When creating events, organizer can enter the equipment they want.
    - And the program will recommend room for the organizer when adding event.

4. Allow the system to support additional user requests (e.g. dietary restrictions, accessibility requirements) where organizers can tag a request as "pending" or "addressed". All organizers see the same list of requests, so if one tags a request as addressed, all other organizers can log in and see that.
    - @ request
    - User other than organizer can now send additional requests.
    - The requests have two status: pending and addressed.
    - All organizers can tag the status of a request.

5. (The extension we designed)
    - @ rate
    - There is now a RateMySpeaker in the program.
    - User can add rating or comment for a speaker and also view the existing comments and average rating.
    - Of course, speakers can rate themselves...

# Design Patterns
===
- Dependency Injection
    - IGateway
        For all gateways to be used in use case, we have interface called IGateway that have read and write
        method that allow use case to use those function without breaking the clean architecture
    - AccountManager
        Many use cases requires AccountManager to query Users. By giving an instance of AccountManager from the
        controller, it reduces dependencies and ensure consistency of data.

- Observer
    We have use an observer for the rooms variable. This is due to that when updating rooms information, there
    are multiple place that need to be updated at once, and we do not want to store all the Use Cases
    within each other. By using the observer design pattern, RoomManager is being observed by EventBoard, EventAdmin,
    and ScheduleGenerator to keep track and update any changes to the list of rooms in the conference.

- Factory
    - SystemFactory
        In the menu system, we need to create different sub-systems according to the current user type.
        We have a factory to build systems based on current user type.
    - UserFactory
        Return a user object according to the given user type.

- Builder
    - MenuSystem.buildSystems();
        Since we have a lot of sub-systems to build in the menu system, we have a builder method in it that
        use the factory to build all sub-systems we want.

- Facade
    - ScheduleGenerator
        The ScheduleGenerator is a use of Facade where we separate it from the event use case.
        This is due to that presenter is the one how will require changes to the schedule generator.
        It takes the responsibility of generating a 2D list representing a schedule.
    - ConflictChecker
        Takes the responsibility of checking schedule conflicts out from the EventAdmin which add events.


# Design Decisions
===
## Phase 2 Updates
    - We removed 2 interfaces: Attendable and Messageable, which involves storing eventList and messageList. We think
      this will make the program easier to extend and maintain since it reduces bilateral interactions and the User
      entities will have less connection with the features. When another feature is added, the User entity do not need
      to be modified.
    - We put all println statements out of controllers into presenters for clean architecture.
    - Exported to .jar file in out/artifacts/phase2_jar/
## Phase 1 Decisions
    - Manager/Board vs. Admin: We want to encapsulate crucial functionalities such as add/remove so that irrelevant users
      will have no access to these functionalities. We made subclasses: EventBoard<-EventAdmin, RoomManager<-RoomAdmin
      that will be generated according to the user type. The superclasses are used for viewing and querying, the
      subclasses are used for modification of data. Normal use will instantiate a superclass, people who are responsible
      for data modification (organizer, staff) will instantiate the subclass.
    - Controllers' hierarchy are designed according to the functionalities the user can perform. It is very extensible
      to add new types of user since you only need to add subclasses.
    - Id is used in almost every entities in order to make unchangeable uniqueness (users might want to change their
      emails in the future, but they are not allowed to change id) and for the consistency of the program. They are also
      useful to be stored.
