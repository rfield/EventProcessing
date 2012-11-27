package com.cvc.techarch.cep.producer.event;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EventProducer {

    public Event getNext();
}
