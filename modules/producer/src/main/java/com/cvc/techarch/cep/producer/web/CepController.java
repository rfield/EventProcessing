package com.cvc.techarch.cep.producer.web;

import com.cvc.techarch.cep.producer.event.EventProducer;
import com.cvc.techarch.cep.producer.event.EventProducerFactory;
import com.cvc.techarch.cep.producer.event.EventStreamGenerator;
import com.cvc.techarch.cep.producer.event.EventType;
import org.apache.activemq.util.ThreadTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 */
@Controller()
@RequestMapping("/*")
public class CepController {

    private JmsTemplate jmsTemplate;
    private Destination destination;
    private EventProducerFactory factory;

    /**
     * Creates a new CmsController with a given jmsTemplate and destination
     */
    @Autowired
    public CepController(JmsTemplate jmsTemplate, Destination destination, EventProducerFactory factory) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.POST)
    /**
     *  It is worth noting that Spring MVC allows very flexible method signatures here,
     *  and supports an array of automatically populated parameters. Here we use the
     *  reasonably portable and convenient Spring equivalent of HttpServletRequest, which is
     *  WebRequest.
     */
    public /* @ResponseBody */ String processSubmit(WebRequest request) {

        int numMessages = 1;
        boolean isInfinite = false;

        Logger log = LoggerFactory.getLogger(this.getClass());

        String resultViewName = "streamStarted";   /* based on config, this resolves to WEB-INF/views/streamStarted.jsp */

        try {

            // TODO This is some ugly, kludgey code with repeated conditionals, and too much logic

            log.info("Processing web request...");

            Map<String, String[]> parameters = request.getParameterMap();

            /* Map form parameters */
            int milliSecondsDelay = Integer.valueOf(parameters.get("milliSecondsDelay")[0]);
            int eventType = Integer.valueOf(parameters.get("eventType")[0]);
            int numThreads = Math.max(1, Integer.valueOf(parameters.get("numThreads")[0]));
            isInfinite = parameters.get("streamType")[0].equalsIgnoreCase("infinite");
            if (!isInfinite) {
                numMessages = Integer.valueOf(parameters.get("numMessages")[0]);
            }

            /* Dump producer.event information */
            log.info("Source      : " + this.getClass().getName());
            log.info("Destination : " + destination.toString());
            log.info("Web form    : " + parameters.toString());
            log.info("Num threads : " + numThreads);

            EventProducer producer = factory.getEventProducer(EventType.getEventType(eventType));

            ArrayList<EventStreamGenerator> generators = new ArrayList<EventStreamGenerator>(numThreads);

            //  TODO Probably it would be better to use the new Java 5 Callable interface here

            // Prepare the threads to run
            for (int i = 0; i < numThreads; i++) {
                generators.add(new EventStreamGenerator(isInfinite, numMessages / numThreads,
                        milliSecondsDelay, destination, jmsTemplate, producer));
            }

            //  Start them in sequence
            for (int i = 0; i < numThreads; i++) {
                generators.get(i).start();
            }

            if (!isInfinite) {
                // Wait for all to threads to complete if this is a finite stream
                for (int i = 0; i < numThreads; i++) {
                    try {
                        generators.get(i).join();
                    } catch (InterruptedException e) {
                        // Nothing to do
                    }
                }
                //  And make sure the threads succeeded
                for (int i = 0; i < numThreads; i++) {
                     if (!generators.get(i).isSuccess()) {
                         throw generators.get(i).getException();
                     }
                }
            }

            log.info("Event stream completed.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage(), RequestAttributes.SCOPE_REQUEST);
            resultViewName = "failure";
        }

        request.setAttribute("numMessages", numMessages, RequestAttributes.SCOPE_REQUEST);
        request.setAttribute("isInfinite", isInfinite, RequestAttributes.SCOPE_REQUEST);
        return resultViewName;
    }
}
