package ejb;

import dao.MessagesDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.CustomMessage;

@Stateless
public class MessagesService implements MessagesServiceLocal {
    @EJB
    private MessagesDAO messagesDAO;

    @Override
    public List<CustomMessage> getAllMessages() {
        return messagesDAO.readAllMessages();
    }

    @Override
    public void removeMessage(int messageId) {
        messagesDAO.removeMessage(messagesDAO.getMessageById(messageId));
    }
}