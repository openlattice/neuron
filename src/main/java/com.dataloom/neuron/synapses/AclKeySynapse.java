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

import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dataloom.neuron.NeuronSynapse;
import com.dataloom.neuron.signals.AclKeySignal;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import static com.dataloom.neuron.constants.SynapseConstants.ACL_KEY_SYNAPSE_PATH;

@Component
@EnableScheduling
public class AclKeySynapse implements Synapse {

    private static final Logger logger = LoggerFactory.getLogger( AclKeySynapse.class );

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Override
    @Scheduled( fixedDelay = 1000 )
    public void transmit() {

        // TODO: IQueue is not a partitioned data-structure... will this be a problem?
        // TODO: there must be a better way than @Scheduled to "start" the listening to the queue
        // TODO: multi-threading?

        IQueue<AclKeySignal> aclKeyQueue = hazelcastInstance.getQueue( NeuronSynapse.ACL_KEY.getName() );

        // TODO: or... while ( !queue.isEmpty() )
        while ( true ) {
            try {
                AclKeySignal aclKeySignal = aclKeyQueue.take();

                String aclKeyPath = aclKeySignal.getAclKey()
                        .stream()
                        .map( UUID::toString )
                        .collect( Collectors.joining( "/" ) );

                String destination = ACL_KEY_SYNAPSE_PATH + "/" + aclKeyPath;

                // Set<SimpSubscription> matchingSubscriptions = simpUserRegistry.findSubscriptions(
                //         subscription -> subscription.getDestination().equals( destination )
                // );

                simpMessagingTemplate.convertAndSend( destination, aclKeySignal );
            } catch ( InterruptedException e ) {
                logger.error( e.getMessage(), e );
            }
        }
    }
}
