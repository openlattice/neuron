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

package com.openlattice.neuron.configuration.interceptors;

import static com.openlattice.neuron.constants.PathConstants.ACL_KEY_PATH;

import com.openlattice.authorization.AclKey;
import com.openlattice.authorization.AuthorizationManager;
import com.openlattice.authorization.AuthorizingComponent;
import com.openlattice.authorization.Permission;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class AclKeyChannelInterceptor extends ChannelInterceptorAdapter implements AuthorizingComponent {

    private static final Logger logger = LoggerFactory.getLogger( AclKeyChannelInterceptor.class );

    private AuthorizationManager authorizationManager;

    public AclKeyChannelInterceptor( AuthorizationManager authorizationManager ) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public AuthorizationManager getAuthorizationManager() {
        return this.authorizationManager;
    }

    @Override
    public Message<?> preSend( Message<?> message, MessageChannel channel ) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap( message );

        if ( StompCommand.SUBSCRIBE.equals( accessor.getCommand() ) ) {

            /*
             * we only want to allow a client to SUBSCRIBE to AclKeys that they are allowed to access.
             * returning null means that SimpleBrokerMessageHandler will *not* invoke registerSubscription() on the
             * SubscriptionRegistry, and therefore, this SUBSCRIBE frame is effectively rejected
             */

            AclKey aclKey = parseDestinationForAclKey( accessor.getDestination() );
            if ( aclKey.isEmpty() ) {
                return null;
            }

            try {
                // TODO: figure out the set of permissions that one needs to be allowed to SUBSCRIBE to an AclKey
                if ( isAuthorized( Permission.READ ).test( aclKey ) ) {
                    // success! the client is allowed to subscribe to the given AclKey
                    return message;
                } else {
                    // failure... the client is not allowed to subscribe to the given AclKey
                    // TODO: figure out how to notify the client so that they can UNSUBSCRIBE
                    return null;
                }
            } catch ( Exception e ) {
                logger.error( e.getMessage(), e );
                return null;
            }
        }

        return message;
    }

    // TODO: make this more robust, write unit tests, move to a shared location
    private AclKey parseDestinationForAclKey( String destination ) {

        if ( destination == null
                || !destination.startsWith( ACL_KEY_PATH )
                || destination.equals( ACL_KEY_PATH ) ) {
            return new AclKey();
        }

        try {

            String aclKeyString = destination.substring( ACL_KEY_PATH.length() + 1 ); // +1 for the leading "/"
            String[] aclKeySplit = aclKeyString.split( "/" );

            if ( aclKeySplit.length <= 0 ) {
                return new AclKey();
            }

            AclKey aclKey = new AclKey( Arrays
                    .stream( aclKeySplit )
                    .map( UUID::fromString )
                    .collect( Collectors.toList() ) );

            return aclKey;
        } catch ( Exception e ) {
            return new AclKey();
        }
    }
}
