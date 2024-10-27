package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    private static final int MAX_AMOUNT_OF_CHARACTERS = 255;

    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank()) {
            throw new IllegalArgumentException("Message cannot be blank");
        }
        if (message.getMessage_text().length() >= MAX_AMOUNT_OF_CHARACTERS) {
            throw new IllegalArgumentException("Message must be under " + MAX_AMOUNT_OF_CHARACTERS + " characters");
        }
        if (ifAccountUsernameExists()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return messageDAO.insertMessage(message);
    }


    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
