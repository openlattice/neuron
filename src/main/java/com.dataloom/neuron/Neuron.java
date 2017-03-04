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

package com.dataloom.neuron;

import com.kryptnostic.rhizome.configuration.websockets.BaseRhizomeServer;
import com.kryptnostic.rhizome.hazelcast.serializers.RhizomeUtils.Pods;
import com.kryptnostic.rhizome.pods.CassandraPod;
import com.kryptnostic.rhizome.pods.hazelcast.RegistryBasedHazelcastInstanceConfigurationPod;
import digital.loom.rhizome.authentication.Auth0Pod;

public class Neuron extends BaseRhizomeServer {

    public static final Class<?>[] rhizomePods = new Class<?>[] {
            Auth0Pod.class,
            CassandraPod.class,
            RegistryBasedHazelcastInstanceConfigurationPod.class
    };

    public static final Class<?>[] neuronPods = new Class<?>[] {

    };

    public Neuron( Class<?>... pods ) {

        super( Pods.concatenate(
                pods,
                rhizomePods
        ) );
    }

    public static void main( String[] args ) throws Exception {

        new Neuron().start( args );
    }
}