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

package com.openlattice.neuron.pods;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.hazelcast.core.HazelcastInstance;
import com.geekbeast.auth0.Auth0Pod;
import com.geekbeast.authentication.Auth0Configuration;
import com.openlattice.authorization.HazelcastAclKeyReservationService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.inject.Inject;

@Configuration
@Import( {
        Auth0Pod.class,
} )
public class NeuronServicesPod {

    @Inject
    private Auth0Configuration auth0Configuration;

    @Inject
    private EventBus eventBus;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private ListeningExecutorService executor;

    @Inject
    private HikariDataSource hikariDataSource;

    @Bean
    public HazelcastAclKeyReservationService aclKeyReservationService() {
        return new HazelcastAclKeyReservationService( hazelcastInstance );
    }

//
//    @Bean
//    public SecurePrincipalsManager principalService() {
//        return new HazelcastPrincipalService( hazelcastInstance,
//                aclKeyReservationService(),
//                authorizationManager() );
//    }
}
