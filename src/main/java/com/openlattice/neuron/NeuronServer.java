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

import static com.geekbeast.rhizome.core.RhizomeApplicationServer.DEFAULT_PODS;

import com.geekbeast.auth0.Auth0Pod;
import com.geekbeast.rhizome.configuration.websockets.BaseRhizomeServer;
import com.geekbeast.rhizome.hazelcast.serializers.RhizomeUtils.Pods;
import com.openlattice.hazelcast.pods.SharedStreamSerializersPod;
import com.openlattice.neuron.pods.NeuronSecurityPod;
import com.openlattice.neuron.pods.NeuronServicesPod;
import com.openlattice.neuron.pods.NeuronServletsPod;

public class NeuronServer extends BaseRhizomeServer {

    private static final Class<?>[] extraPods = new Class<?>[] {
            Auth0Pod.class,
            SharedStreamSerializersPod.class,
    };

    private static final Class<?>[] neuronPods = new Class<?>[] {
            NeuronSecurityPod.class,
            NeuronServicesPod.class,
            NeuronServletsPod.class
    };

    private NeuronServer( Class<?>... pods ) {

        super( Pods.concatenate(
                pods,
                DEFAULT_PODS,
                extraPods,
                neuronPods
        ) );
    }

    public static void main( String[] args ) throws Exception {

        new NeuronServer().start( args );
    }
}