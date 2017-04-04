/*
 * Copyright (C) 2017. Kryptnostic, Inc (dba Loom)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You can contact the owner of the copyright at support@thedataloom.com
 */

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
import com.dataloom.neuron.Signal;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import static com.dataloom.neuron.constants.SynapseConstants.NOTIFICATIONS_SYNAPSE_PATH;

@Component
@EnableScheduling
public class NotificationsSynapse implements Synapse {

    private static final Logger logger = LoggerFactory.getLogger( NotificationsSynapse.class );

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled( fixedDelay = 1000, fixedRate = 1000 )
    public void transmit() {

        IQueue<Signal> notifications = hazelcastInstance.getQueue( NeuronSynapses.NOTIFICATIONS.name() );

        // TODO: or... while ( !notifications.isEmpty() )
        // TODO: multi-threading?
        while ( true ) {
            try {
                Signal notification = notifications.take();
                simpMessagingTemplate.convertAndSend( NOTIFICATIONS_SYNAPSE_PATH, notification );
            } catch ( InterruptedException e ) {
                logger.error( e.getMessage(), e );
            }
        }
    }
}
