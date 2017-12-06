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

package com.dataloom.neuron.pods;

import com.auth0.spring.security.api.Auth0CORSFilter;
import com.dataloom.authentication.LoomAuth0AuthenticationProvider;
import com.dataloom.authorization.SystemRole;
import com.dataloom.organizations.roles.SecurePrincipalsManager;
import com.kryptnostic.rhizome.configuration.RhizomeConfiguration;
import digital.loom.rhizome.authentication.Auth0SecurityPod;
import digital.loom.rhizome.authentication.ConfigurableAuth0AuthenticationProvider;
import digital.loom.rhizome.authentication.ConfigurableAuth0CORSFilter;
import java.net.URI;
import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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
    protected ConfigurableAuth0AuthenticationProvider getAuthenticationProvider() {
        return new LoomAuth0AuthenticationProvider( getAuthenticationApiClient(), principalsManager );
    }

    @Override
    protected Auth0CORSFilter getAuth0CORSFilter() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin( rhizomeConfiguration.getCORSAccessControlAllowOriginUrl() );
        headers.setAccessControlAllowCredentials( true );

        return new ConfigurableAuth0CORSFilter( headers );
    }

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
