package ejb;

import java.util.List;
import javax.ejb.Local;
import models.CustomMessage;

@Local
public interface MessagesServiceLocal {
    void removeMessage(int messageId);
    List<CustomMessage> getAllMessages();
}
