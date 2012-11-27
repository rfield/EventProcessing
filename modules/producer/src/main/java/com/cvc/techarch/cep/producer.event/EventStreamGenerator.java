package com.cvc.techarch.cep.producer.event;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventStreamGenerator extends Thread {

    public void run() {

        stopped = false;

        if (isInfinite) {
            while(!stopped) {
                generateEvent();
            }
        }
        else {
            for(int i = 1; i <= numMessages; i++) {
                generateEvent();
            }
        }
    }

    private void generateEvent() {
        try {
            jmsTemplate.convertAndSend(destination, "[Generator-" + id + "]: " + producer.getNext().toString());
        }
        catch (Exception e) {
            success = false;
            this.exception = e;
        }
        delay(milliSecondsDelay);
    }

    private void delay(int delay) {
        if(delay > 0)
            try {
                Thread.sleep(delay);
            }
            catch (Exception e) {
                // nothing to do
            }
    }

    public EventStreamGenerator(boolean isInfinite, int numMessages, int milliSecondsDelay,
                                Destination destination, JmsTemplate jmsTemplate, EventProducer producer) {
        this.id = instanceCounter++;
        this.isInfinite = isInfinite;
        this.numMessages = numMessages;
        this.milliSecondsDelay = milliSecondsDelay;
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.producer = producer;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    private boolean stopped = false;
    private boolean success = true;
    private Exception exception = null;
    private boolean isInfinite;
    private int numMessages;
    private int milliSecondsDelay;
    private int id;
    private Destination destination;
    private JmsTemplate jmsTemplate;
    private EventProducer producer;
    private static int instanceCounter = 0;
}
