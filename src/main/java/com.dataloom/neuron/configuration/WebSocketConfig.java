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

package com.dataloom.neuron.configuration;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import static com.dataloom.neuron.constants.WebSocketConstants.DEFAULT_APPLICATION_DESTINATION_PATH;
import static com.dataloom.neuron.constants.WebSocketConstants.DEFAULT_BROKER_PATH;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker( MessageBrokerRegistry registry ) {

        registry.enableSimpleBroker( DEFAULT_BROKER_PATH );
        registry.setApplicationDestinationPrefixes( DEFAULT_APPLICATION_DESTINATION_PATH );
    }

    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {

        registry
                .addEndpoint( DEFAULT_APPLICATION_DESTINATION_PATH )
                .setAllowedOrigins( "*" )
                .setHandshakeHandler( handshakeHandler() )
                .withSockJS();
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {

        WebSocketPolicy policy = new WebSocketPolicy( WebSocketBehavior.SERVER );
        policy.setInputBufferSize( 8192 );
        policy.setIdleTimeout( 600000 );

        return new DefaultHandshakeHandler( new JettyRequestUpgradeStrategy( new WebSocketServerFactory( policy ) ) );
    }

}
