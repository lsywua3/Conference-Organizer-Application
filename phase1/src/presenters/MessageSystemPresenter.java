package presenters;

import useCase.accountManager.AccountManager;
import useCase.messageManager.UserMessageManager;

import java.util.ArrayList;

/**
 * Presenter for Message System.
 */
public class MessageSystemPresenter {

    /**
     * Display the information of a message including message id, sender id, sender name, receiver id,
     * receiver name and conetent of the messages.
     *
     * @param messageInfo an ArrayList of String containing the information of a message, in format of
     *                    [sender id, receiver id, content, message id, sender name, receiver name]
     */
    public void presentMessage(ArrayList<String> messageInfo) {
        if (messageInfo.size() == 0) {
            System.out.println("Your designated message does not exist.");
        } else {
            String senderId = messageInfo.get(0);
            String receiverId = messageInfo.get(1);
            String content = messageInfo.get(2);
            String messageId = messageInfo.get(3);
            String senderName = messageInfo.get(4);
            String receiverName = messageInfo.get(5);
            String result = "[" + messageId + "] ----------------------\n"
                    + "       From [" + senderId + "] " + senderName + "\n"
                    + "       To   [" + receiverId + "] " + receiverName + "\n"
                    + "       " + content;
            //displayContent;
            System.out.println(result);
            System.out.println();
        }
    }


    /**
     * Display the information of a list of messages, each with the message information provided in the
     * presentMessage method.
     *
     * @param messagesInfo an ArrayList<ArrayList<String>> of a list of messages, each element of the arraylist is an
     *                     arraylist of event information in format of [sender id, receiver id, content, message id,
     *                     sender name, receiver name]
     */
    public void presentMessages(ArrayList<ArrayList<String>> messagesInfo) {
        if (messagesInfo.size() == 0) {
            System.out.println("There are no messages of this type");
        }
        for (ArrayList<String> messageInfo : messagesInfo) {
            presentMessage(messageInfo);

        }
    }

    /**
     * Display the preview of a list of sent messages and the preview of a list of received messages,
     * each with the message information including message id, receiver/sender name, content preview with only
     * the first 50 characters of the content of the message
     *
     * @param receivedMessagesInfo an ArrayList<ArrayList<String>> of a list of received messages, each element of
     *                             the arraylist is an arraylist of message information in format of
     *                             [sender id, receiver id, content, message id, sender name, receiver name]
     * @param sentMessagesInfo     an ArrayList<ArrayList<String>> of a list of sent messages, each element of
     *                             the arraylist is an arraylist of message information in format of
     *                             [sender id, receiver id, content, message id, sender name, receiver name]
     */
    public void presentMessagesPreview(ArrayList<ArrayList<String>> receivedMessagesInfo,
                                       ArrayList<ArrayList<String>> sentMessagesInfo) {
        String content;
        String displayContent;
        String displayReceivedPreview;
        System.out.println("Here is the preview of the messages that you have received and sent.");
        //System.out.println("Received Messages:");
        presentMessagePreview(receivedMessagesInfo, "received");
        //System.out.println("Sent Messages:");
        presentMessagePreview(sentMessagesInfo, "sent");


    }

    /**
     * Display the preview of a list of sent messages or the preview of a list of received messages,
     * each with the message information including message id, receiver/sender name, content preview with only
     * the first 50 characters of the content of the message
     *
     * @param messageInfo an ArrayList<ArrayList<String>> of a list of messages, each element of the arraylist is an
     *                    arraylist of message information in format of [sender id, receiver id, content, message id,
     *                    sender name, receiver name]
     * @param option      a String that specifies whether this list of messages to be previewed are all
     *                    sent messages or received messages; Should only be either "sent" or "received" (Precondition)
     */
    public void presentMessagePreview(ArrayList<ArrayList<String>> messageInfo, String option) {

        String content;
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
        for (ArrayList<String> receivedMessageInfo : messageInfo) {
            switch (title) {
                case "receiver":
                    name = receivedMessageInfo.get(5);
                    break;
                case "sender":
                    name = receivedMessageInfo.get(4);
                    break;
            }
            content = receivedMessageInfo.get(2);
            if (content.length() <= 50) {
                displayContent = content;
            } else {
                displayContent = content.substring(0, 50);
            }
            displayPreview = "Message Id: [" + receivedMessageInfo.get(3) + "]" + title + "Name: " +
                    name + "   Content Preivew: " + displayContent;
            System.out.println(displayPreview);
        }

    }


    /**
     * Generates the information list of a message in format of [sender id, receiver id, content, message id,
     * sender name, receiver name] for presentation purpose
     *
     * @param mm             the user message manager of a specific user that gets the information about a specific
     *                       message of that user's
     * @param accountManager the account manager that provides more detailed information about sender and receiver
     * @param messageId      the id of the message whose information list needs to be generated
     * @return an arraylist of Strings that provides the information of a message in format of [sender id, receiver id,
     * content, message id, sender name, receiver name]
     */

    public ArrayList<String> generatePresenterInfo(UserMessageManager mm, AccountManager accountManager,
                                                   int messageId) {

        ArrayList<String> infoList = mm.getInfoList(messageId);
        // Check alias problems...
        infoList.add(Integer.toString(messageId));
        infoList.add(accountManager.getInfo(Integer.parseInt(mm.getInfo(messageId,
                "senderId")), "name"));
        infoList.add(accountManager.getInfo(Integer.parseInt(mm.getInfo(messageId,
                "receiverId")), "name"));
        return infoList;

    }


}
