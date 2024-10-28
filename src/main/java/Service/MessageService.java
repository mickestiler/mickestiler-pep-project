package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    AccountDAO accountDAO;
    MessageDAO messageDAO;
    private static final int MAX_AMOUNT_OF_CHARACTERS = 255;

    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank()) {
            throw new IllegalArgumentException("Message cannot be blank");
        }
        if (message.getMessage_text().length() >= MAX_AMOUNT_OF_CHARACTERS) {
            throw new IllegalArgumentException("Message must be under " + MAX_AMOUNT_OF_CHARACTERS + " characters");
        }
        if (accountDAO.ifAccountUsernameExists()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return messageDAO.insertMessage(message);
    }


    /**
     * Service method to obtain all messages in the database.
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    private
}
