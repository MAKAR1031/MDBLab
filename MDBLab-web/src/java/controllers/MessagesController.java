package controllers;

import ejb.MessagesServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import models.CustomMessage;

@Named
@RequestScoped
public class MessagesController{

    @EJB
    private MessagesServiceLocal messagesService;

    public List<CustomMessage> getAllMessages() {
        return messagesService.getAllMessages();
    }
    
    public void removeMessage(int messageId) {
        messagesService.removeMessage(messageId);
    }
    
    public void refreshPage() {}
}