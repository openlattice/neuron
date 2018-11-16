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

import static com.kryptnostic.rhizome.core.RhizomeApplicationServer.DEFAULT_PODS;

import com.kryptnostic.rhizome.configuration.websockets.BaseRhizomeServer;
import com.kryptnostic.rhizome.hazelcast.serializers.RhizomeUtils.Pods;
import com.openlattice.auth0.Auth0Pod;
import com.openlattice.conductor.codecs.pods.TypeCodecsPod;
import com.openlattice.hazelcast.pods.SharedStreamSerializersPod;
import com.openlattice.neuron.pods.NeuronPod;
import com.openlattice.neuron.pods.NeuronSecurityPod;
import com.openlattice.neuron.pods.NeuronServicesPod;
import com.openlattice.neuron.pods.NeuronServletsPod;

public class NeuronServer extends BaseRhizomeServer {

    public static final Class<?>[] extraPods = new Class<?>[] {
            Auth0Pod.class,
            SharedStreamSerializersPod.class,
            TypeCodecsPod.class
    };

    public static final Class<?>[] neuronPods = new Class<?>[] {
            NeuronPod.class,
            NeuronSecurityPod.class,
            NeuronServicesPod.class,
            NeuronServletsPod.class
    };

    public NeuronServer( Class<?>... pods ) {

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