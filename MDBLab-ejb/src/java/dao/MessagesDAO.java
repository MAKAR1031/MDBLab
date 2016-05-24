package dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.CustomMessage;

@Stateless
@LocalBean
public class MessagesDAO {

    @PersistenceContext(unitName = "MDBLab-ejbPU")
    private EntityManager em;

    public CustomMessage getMessageById(int id) {
        return em.find(CustomMessage.class, id);
    }
    
    public List<CustomMessage> readAllMessages() {
        return em.createQuery("SELECT m FROM CustomMessage m").getResultList();
    }

    public void saveMessage(CustomMessage message) {
        em.persist(message);
    }

    public void removeMessage(CustomMessage message) {
        em.remove(em.merge(message));
    }
}