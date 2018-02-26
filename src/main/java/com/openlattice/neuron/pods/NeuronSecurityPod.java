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

import com.kryptnostic.rhizome.configuration.RhizomeConfiguration;
import com.openlattice.auth0.Auth0SecurityPod;
import com.openlattice.authorization.SystemRole;
import com.openlattice.organizations.roles.SecurePrincipalsManager;
import java.net.URI;
import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableGlobalMethodSecurity( prePostEnabled = true )
@EnableWebSecurity( debug = false )
public class NeuronSecurityPod extends Auth0SecurityPod {

    @Inject
    protected RhizomeConfiguration rhizomeConfiguration;

    @Inject
    SecurePrincipalsManager principalsManager;

    @Override
    protected void authorizeRequests( HttpSecurity http ) throws Exception {

        String allowedOrigin = rhizomeConfiguration.getCORSAccessControlAllowOriginUrl();
        AllowFromStrategy allowFromStrategy = new StaticAllowFromStrategy( URI.create( allowedOrigin ) );

        // @formatter:off
        http
                .headers()
                    .frameOptions().disable()
                    .addHeaderWriter( new XFrameOptionsHeaderWriter( allowFromStrategy ) )
                    .and()
                .authorizeRequests()
                    .antMatchers( HttpMethod.OPTIONS ).permitAll()
                    .antMatchers( "/neuron/**" ).hasAnyAuthority(
                            SystemRole.ADMIN.getPrincipal().getId(),
                            SystemRole.ADMIN.getPrincipal().getId().toUpperCase(),
                            SystemRole.AUTHENTICATED_USER.getPrincipal().getId()
                    );
        // @formatter:on
    }
}
