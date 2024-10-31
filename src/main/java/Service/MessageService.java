package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    private static final int MAX_AMOUNT_OF_CHARACTERS = 255;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    /**
     * Service method to create a message and insert it into a database. It throws an IllegalArgumentException if the message text is blank,
     * the length of the message is greater than 255 characters, or if the account does not exist in the account table.
     * @param message
     * @return Message if created successfully
     */
    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank()) {
            throw new IllegalArgumentException("Message cannot be blank");
        }
        if (message.getMessage_text().length() > MAX_AMOUNT_OF_CHARACTERS) {
            throw new IllegalArgumentException("Message must be under " + MAX_AMOUNT_OF_CHARACTERS + " characters");
        }
        if (!ifAccountIdExists(message)) {
            throw new IllegalArgumentException("Posted by account does not exist");
        }
        return messageDAO.insertMessage(message);
    }


    /**
     * Service method to obtain all messages in the message table.
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        if (message != null) {
            messageDAO.deleteMessageById(message_id);
            return message;
        }
        return null;
    }

    public Message updateMessageById(int message_id) {
        
    }

    /**
     * Service method to get all messages by user.
     * @param message
     * @return list of all messages by a user
     */
    public List<Message> getAllMessagesByUser(Message message) {
        return messageDAO.getAllMessagesByUser(message.getPosted_by());
    }

    /**
     * Helper method for createMessage() to determine if an account exists within the account table.
     * @param message
     * @return true if the posted_by id exists within account, false otherwise
     */
    private boolean ifAccountIdExists(Message message) {
        return accountDAO.getAccountById(message.getPosted_by()) != null;
    }

    private boolean ifMessageExists(Message message) {
        return messageDAO.getMessageById(message) != null;
    }
