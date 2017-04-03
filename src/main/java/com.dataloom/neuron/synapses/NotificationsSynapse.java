package com.dataloom.neuron.synapses;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dataloom.neuron.NeuronSynapses;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import static com.dataloom.neuron.constants.SynapseConstants.NOTIFICATIONS_SYNAPSE_PATH;

@Component
@EnableScheduling
public class NotificationsSynapse {

    private static final Logger logger = LoggerFactory.getLogger( NotificationsSynapse.class );

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled( fixedDelay = 1000 )
    public void signal() {

        // TODO: use an actual class for events, something like IQueue<Signal>, not just IQueue<Object>
        // TODO: perhaps extend an abstract base Synapse
        IQueue<Object> notifications = hazelcastInstance.getQueue( NeuronSynapses.NOTIFICATIONS.name() );

        while ( true ) {
            try {
                Object notification = notifications.take();
                simpMessagingTemplate.convertAndSend( NOTIFICATIONS_SYNAPSE_PATH, notification.toString() );
            } catch ( InterruptedException e ) {
                logger.error( e.getMessage(), e );
            }
        }
    }
}
