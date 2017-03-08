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

import com.google.common.collect.Lists;
import com.kryptnostic.rhizome.configuration.servlets.DispatcherServletConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NeuronServletsPod {

    public static final String   NEURON_SERVLET_NAME     = "neuron";
    public static final String[] NEURON_SERVLET_MAPPINGS = new String[] { "/neuron/*" };

    public static final Integer LOAD_ON_STARTUP = 1;

    @Bean
    public DispatcherServletConfiguration neuronServlet() {

        return new DispatcherServletConfiguration(
                NEURON_SERVLET_NAME,
                NEURON_SERVLET_MAPPINGS,
                LOAD_ON_STARTUP,
                Lists.newArrayList( NeuronMvcPod.class )
        );
    }

}
