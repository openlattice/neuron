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

import com.openlattice.neuron.SignalTerminal;
import com.openlattice.neuron.configuration.WebSocketConfig;
import com.openlattice.neuron.configuration.WebSocketSecurityConfig;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan(
        basePackageClasses = {
                SignalTerminal.class
        },
        includeFilters = @ComponentScan.Filter(
                value = {
                        org.springframework.stereotype.Component.class,
                        org.springframework.stereotype.Controller.class
                },
                type = FilterType.ANNOTATION
        )
)
@Import( {
        WebSocketConfig.class,
        WebSocketSecurityConfig.class
} )
public class NeuronMvcPod extends WebMvcConfigurationSupport {

    // TODO(LATTICE-2346): We need to lock this down. Since all endpoints are stateless + authenticated this is more a
    // defense-in-depth measure.
    @SuppressFBWarnings(
            value = {"PERMISSIVE_CORS"},
            justification = "LATTICE-2346"
    )
    @Override
    protected void addCorsMappings( CorsRegistry registry ) {
        registry
                .addMapping( "/**" )
                .allowedMethods( "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH" )
                .allowedOrigins( "*" );
        super.addCorsMappings( registry );
    }
}
