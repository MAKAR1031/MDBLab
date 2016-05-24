package mdb;

import dao.MessagesDAO;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import models.CustomMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/sample_queue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReceiver implements MessageListener {

    @EJB
    private MessagesDAO messagesDAO;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                CustomMessage cm = (CustomMessage) ((ObjectMessage) message).getObject();
                messagesDAO.saveMessage(cm);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обработке сообщения");
        }
    }
}