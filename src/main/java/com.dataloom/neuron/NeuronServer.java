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

import com.dataloom.hazelcast.pods.SharedStreamSerializersPod;
import com.dataloom.neuron.pods.NeuronPod;
import com.dataloom.neuron.pods.NeuronSecurityPod;
import com.dataloom.neuron.pods.NeuronServicesPod;
import com.dataloom.neuron.pods.NeuronServletsPod;
import com.kryptnostic.conductor.codecs.pods.TypeCodecsPod;
import com.kryptnostic.rhizome.configuration.websockets.BaseRhizomeServer;
import com.kryptnostic.rhizome.hazelcast.serializers.RhizomeUtils.Pods;

import digital.loom.rhizome.authentication.Auth0Pod;

import static com.kryptnostic.rhizome.core.RhizomeApplicationServer.DEFAULT_PODS;

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