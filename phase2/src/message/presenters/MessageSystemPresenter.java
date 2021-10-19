package message.presenters;

import account.useCases.AccountManager;
import message.useCases.UserMessageManager;

import java.util.List;

public abstract class MessageSystemPresenter {

    /**
     * Display the information of a message including message id, sender id, sender name, receiver id,
     * receiver name and conetent of the messages.
     *
     * @param messageInfo an List of String containing the information of a message, in format of
     *                    [sender id, receiver id, content, message id, sender name, receiver name]
     */
    public void presentMessage(List<String> messageInfo) {
        if (messageInfo.size() == 0) {
            System.out.println("Your designated message does not exist.");
        } else {
            String senderId = messageInfo.get(0);
            String receiverId = messageInfo.get(1);
            String content = messageInfo.get(2);
            String status = messageInfo.get(3);
            String messageId = messageInfo.get(4);
            String senderName = messageInfo.get(5);
            String receiverName = messageInfo.get(6);
            String result = "<" + messageId + "> ---------------\n"
                    + "       From [" + senderId + "] " + senderName + "\n"
                    + "       To [" + receiverId + "] " + receiverName + "\n"
                    + "       " + content;
            //displayContent;
            if (!status.equals("send")) {
                result = "[" + status + "]\n" + result;
            }
            System.out.println(result);
            System.out.println();
        }
    }


    /**
     * Display the information of a list of messages, each with the message information provided in the
     * presentMessage method.
     *
     * @param messagesInfo an List<List<String>> of a list of messages, each element of the list is an
     *                     list of event information in format of [sender id, receiver id, content, message id,
     *                     sender name, receiver name]
     */
    public void presentMessages(List<List<String>> messagesInfo) {
        if (messagesInfo.size() == 0) {
            System.out.println("There are no messages of this type");
        }
        for (List<String> messageInfo : messagesInfo) {
            presentMessage(messageInfo);
            //System.out.println();
        }
    }

    /**
     * Display the preview of a list of sent messages or the preview of a list of received messages,
     * each with the message information including message id, receiver/sender name, content preview with only
     * the first 50 characters of the content of the message
     *
     * @param messageInfo an List<List<String>> of a list of messages, each element of the list is an
     *                    list of message information in format of [sender id, receiver id, content, message id,
     *                    sender name, receiver name]
     * @param option      a String that specifies whether this list of messages to be previewed are all
     *                    sent messages or received messages; Should only be either "sent" or "received" (Precondition)
     */
    public void presentMessagePreview(List<List<String>> messageInfo, String option) {

        String content;
        String status;
        String displayContent;
        String displayPreview;
        String title = "";
        String intro = "";
        String name = "";
        switch (option) {
            case "sent":
                title = "receiver";
                intro = "Sent Messages:";
                break;
            case "received":
                title = "sender";
                intro = "Received Messages:";
                break;
        }
        System.out.println(intro);
        if (messageInfo.size() == 0) {
            System.out.println("Three is no message of this type.");
        }
        for (List<String> receivedMessageInfo : messageInfo) {
            switch (title) {
                case "receiver":
                    name = receivedMessageInfo.get(6);
                    break;
                case "sender":
                    name = receivedMessageInfo.get(5);
                    break;
            }
            status = receivedMessageInfo.get(3);
            content = receivedMessageInfo.get(2);
            if (content.length() <= 50) {
                displayContent = content;
            } else {
                displayContent = content.substring(0, 50);
            }
            displayPreview = "<" + status + ">" + " [" + receivedMessageInfo.get(4) + "]" + title + "   From: " +
                    name + "   Content Preivew: " + displayContent;
            System.out.println(displayPreview);
        }

    }


    /**
     * Display the preview of a list of sent messages and the preview of a list of received messages,
     * each with the message information including message id, receiver/sender name, content preview with only
     * the first 50 characters of the content of the message
     *
     * @param receivedMessagesInfo an List<List<String>> of a list of received messages, each element of
     *                             the list is an list of message information in format of
     *                             [sender id, receiver id, content, message id, sender name, receiver name]
     * @param sentMessagesInfo     an List<List<String>> of a list of sent messages, each element of
     *                             the list is an list of message information in format of
     *                             [sender id, receiver id, content, message id, sender name, receiver name]
     */
    public void presentMessagesPreview(List<List<String>> receivedMessagesInfo,
                                       List<List<String>> sentMessagesInfo) {
        //String content;
        //String displayContent;
        //String displayReceivedPreview;
        System.out.println("Here is the preview of the messages that you have received and sent.");
        //System.out.println("Received Messages:");
        presentMessagePreview(receivedMessagesInfo, "received");
        //System.out.println("Sent Messages:");
        presentMessagePreview(sentMessagesInfo, "sent");


    }


    /**
     * Generates the information list of a message in format of [sender id, receiver id, content, message id,
     * sender name, receiver name] for presentation purpose
     *
     * @param mm             the user message manager of a specific user that gets the information about a specific message
     *                       of that user's
     * @param accountManager the account manager that provides more detailed information about sender and receiver
     * @param messageId      the id of the message whose information list needs to be generated
     * @return an list of Strings that provides the information of a message in format of [sender id, receiver id,
     * content, message id, sender name, receiver name]
     */

    public List<String> generatePresenterInfo(UserMessageManager mm, AccountManager accountManager, int messageId) {

        List<String> infoList = mm.getInfoList(messageId);
        // Check alias problems...
        infoList.add(Integer.toString(messageId));
        infoList.add(accountManager.getInfo(Integer.parseInt(mm.getInfo(messageId,
                "senderId")), "name"));
        infoList.add(accountManager.getInfo(Integer.parseInt(mm.getInfo(messageId,
                "receiverId")), "name"));
        return infoList;

    }


    /**
     * Present the instruction for asking for message id input when a user tries to view a message
     */
    public void presentViewMessageMessageInstruction() {
        System.out.println("Please enter the <message id> of the message that you want to view. Enter [0] to " +
                "exit");
    }

    /**
     * Present the instruction for asking for which message list the user wants to view when a user tries to view
     * messages.
     */
    public void presentViewMessagesMessageInstruction() {
        System.out.println("Which message option would you like to view?");
        System.out.println("[1] all unread messages \n[2] all read messages \n[3] all achieved messages\n" +
                "[4] all received messages \n[5] all sent messages \n[0] exit");

    }


    /**
     * Present the instruction for asking the email of the user that the current user wants to send an email to
     */
    public void presentSendMessageEmailInstruction() {
        System.out.println("Please enter the <email> of the user that you want this message to be sent to. " +
                "Enter [0] to go back");
    }


    /**
     * Present the instruction for asking the content of the email that the current user wants to send to
     */
    public void presentSendMessageContentInstruction() {
        System.out.println("Please enter the <content> of the message that you want to send. " +
                "Enter [0] to go back");
    }

    /**
     * Helper method that presents the success notification message when a message is sent successfully, and presents
     * the failure message when a notification message failed to be sent
     *
     * @param success        the boolean value representing whether the message is sent successfully
     * @param successMessage the success notification message to be displayed when a message is sent successfully
     * @param failureMessage the failure notification message to be displayed when a message is sent unsuccessfully
     */
    protected void presentSendingResult(boolean success, String successMessage, String failureMessage) {
        if (!success) {
            System.out.println(failureMessage);
        } else {
            System.out.println(successMessage);
        }
    }


    /**
     * Present the success notification message or failure message for sending a message after a user has tried to send
     * a message
     *
     * @param success a boolean value representing whether the message is successfully sent
     */
    public void presentSendMessageResult(boolean success) {
        String successMessage = "Your message has been successfully sent!";
        String failutreMessage = "failed sending the message, please double check and try again.";
        presentSendingResult(success, successMessage, failutreMessage);
    }

    /**
     * Present the introduction message saying that all the messages that the user has received will be presented
     */
    public void presentReplyMessageAllMessagesIntroduction() {
        System.out.println("These are all the messages that you have received");
    }


    /**
     * Present the instruction asking for the message id input of the message that this current user wants to reply to
     * when a user tries to reply to a message.
     */
    public void presentReplyMessageMessageInstruction() {
        System.out.println("Please enter the <message id> of the message that you want to reply. Enter" +
                " [0] to exit");
    }


    /**
     * Present the instruction for asking for the content input of the reply message when a user tries to reply to a
     * message.
     */
    public void presentReplyMessageContentInstruction() {
        System.out.println("Please enter the <content> of the message that you want to reply. " +
                "Enter [0] to go back");
    }

    /**
     * Present the success or failure notification message for sending the reply message after a user has replied to
     * a message
     *
     * @param success the boolean value representing whether the reply message has been successfully sent
     */
    public void presentReplyMessageResult(boolean success) {
        String successMessage = "Your reply message has been successfully sent!";
        String failureMessage = "failed replying the message, please double check and try again";
        presentSendingResult(success, successMessage, failureMessage);
    }

    /**
     * Present the instruction for asking which message user want to mark
     */
    public void presentMarkMessageMessageInstruction() {
        System.out.println("Please enter the <message id> of the message that you want to mark. Enter" +
                " [0] to exit");
    }

    /**
     * Present the options of status user can mark the message as
     */
    public void presentMarkMessageOptionsInstruction() {
        System.out.println("Which status do you want to mark?");
        System.out.println("[1] unread [2] read [3] achieved [0] exit");
    }

    public void presentMarkMessageResult(boolean success) {
        if (success) {
            System.out.println("Your message has been marked successfully!");
        } else {
            System.out.println("Fail to mark your message, please double check and try again");
        }
    }

    /**
     * Present the message not found notification message when a message that a user is trying to access can not be found
     */
    public void presentMessageNotFound() {
        System.out.println("This message is not found, please try again!");
    }

    /**
     * Present the user not found notification message when the user that the current user tries to find can not be found.
     */
    public void presentUserNotFound() {
        System.out.println("This user is not found, please try again!");
    }

    /**
     * Present the invalid input notification message when an input is invalid
     */
    public void presentInputError() {
        System.out.println("This is an invalid input, please try again.");
    }


    /**
     * Presents the invalid command notification message when an input command is invalid
     */
    public void presentCommandError() {
        System.out.println("Illegal command!");
    }

    /**
     * Present the menu of the commands that a user can make when using a messaging system
     */
    public abstract void presentMenu();


}
