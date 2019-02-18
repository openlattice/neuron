/*
 * Copyright (C) 2018. OpenLattice, Inc.
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
 * You can contact the owner of the copyright at support@openlattice.com
 *
 *
 */

package com.openlattice.neuron;

import com.hazelcast.core.HazelcastInstance;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SignalTerminal {

    private static final Logger logger = LoggerFactory.getLogger( SignalTerminal.class );

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    // TODO: there must be a better way than @Scheduled to "start" the listening to the queue
    @Scheduled( fixedDelay = 1000 )
    public void process() {

//        // TODO: multi-threading?
//        // TODO: IQueue is not a partitioned data-structure... will this be a problem?
//
//        IQueue<Signal> queue = hazelcastInstance.getQueue( HazelcastQueue.SIGNAL.name() );
//
//        // TODO: or... while ( !queue.isEmpty() )
//        while ( true ) {
//            try {
//                Signal signal = queue.take();
//                String aclKeyPath = signal.getAclKey()
//                        .stream()
//                        .map( UUID::toString )
//                        .collect( Collectors.joining( "/" ) );
//                // TODO: this needs to evolve to handle all types of signals, which means handling various destinations
//                String destination = ACL_KEY_PATH + "/" + aclKeyPath;
//                simpMessagingTemplate.convertAndSend( destination, signal );
//            } catch ( InterruptedException e ) {
//                logger.error( e.getMessage(), e );
//            }
//        }
    }
}
