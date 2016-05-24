package mdbclient.controllers;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import models.CustomMessage;

public class WindowController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button sendButton;

    private ConnectionFactory connectionFactory;
    private Queue destinationQueue;

    private FadeTransition titleFadeAnimation;
    private FadeTransition messageFadeAnimation;
    private SequentialTransition sendRotateAnimation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setProperty(ConnectionConfiguration.imqAddressList,
                    "localhost:7676");
            destinationQueue = new Queue("SampleQueue");
        } catch (JMSException e) {
            System.out.println("Ошибка при создании фабрики подключений или очереди");
            System.exit(1);
        }
        createAnimation();
    }

    @FXML
    private void sendMessageHandle() {
        try {
            String title = titleField.getText();
            String text = messageTextArea.getText();

            if (!title.isEmpty() && !text.isEmpty()) {
                titleField.clear();
                messageTextArea.clear();
                titleFadeAnimation.play();
                messageFadeAnimation.play();
                sendMessage(title, text);
            } else {
                if (title.isEmpty()) {
                    titleField.requestFocus();
                } else {
                    messageTextArea.requestFocus();
                }
                sendRotateAnimation.play();
            }
        } catch (Exception e) {
            System.out.println("Не удалось отправить сообщение");
        }
    }

    private void createAnimation() {
        titleFadeAnimation = new FadeTransition(Duration.millis(400), titleField);
        titleFadeAnimation.setCycleCount(2);
        titleFadeAnimation.setAutoReverse(true);
        titleFadeAnimation.setFromValue(1);
        titleFadeAnimation.setToValue(0);

        messageFadeAnimation = new FadeTransition(Duration.millis(400), messageTextArea);
        messageFadeAnimation.setCycleCount(2);
        messageFadeAnimation.setAutoReverse(true);
        messageFadeAnimation.setFromValue(1);
        messageFadeAnimation.setToValue(0);

        RotateTransition rt1 = new RotateTransition(Duration.millis(70), sendButton);
        rt1.setCycleCount(2);
        rt1.setAutoReverse(true);
        rt1.setByAngle(-10);

        RotateTransition rt2 = new RotateTransition(Duration.millis(70), sendButton);
        rt2.setCycleCount(2);
        rt2.setAutoReverse(true);
        rt2.setByAngle(12);

        sendRotateAnimation = new SequentialTransition(rt1, rt2);
    }

    private void sendMessage(String title, String text) throws JMSException {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destinationQueue);
            CustomMessage customMessage = new CustomMessage();
            customMessage.setTitle(title);
            customMessage.setContent(text);
            ObjectMessage message = session.createObjectMessage(customMessage);
            producer.send(message);
        }
    }
}