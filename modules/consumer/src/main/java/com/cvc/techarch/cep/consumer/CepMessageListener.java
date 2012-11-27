package com.cvc.techarch.cep.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 7/12/11
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CepMessageListener implements MessageListener {

//    @Autowired
//    ContentItemListSupport contentItemListSupport;


    public void onMessage(Message message) {

        Logger log = LoggerFactory.getLogger(this.getClass());

        if (message instanceof TextMessage) {
            try {
                String messageText = ((TextMessage)message).getText();
				log.info("Message text is: " + messageText);
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
