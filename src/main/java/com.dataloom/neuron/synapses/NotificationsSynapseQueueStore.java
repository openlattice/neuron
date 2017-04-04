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

package com.dataloom.neuron.synapses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataloom.neuron.NeuronSynapses;
import com.dataloom.neuron.Signal;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.kryptnostic.conductor.rpc.odata.Table;
import com.kryptnostic.rhizome.cassandra.CassandraTableBuilder;
import com.kryptnostic.rhizome.queuestores.cassandra.AbstractBaseCassandraQueueStore;

public class NotificationsSynapseQueueStore extends AbstractBaseCassandraQueueStore<Signal> {

    private static final Logger logger = LoggerFactory.getLogger( NotificationsSynapseQueueStore.class );

    private static final CassandraTableBuilder cassandraTableBuilder = Table.DATA.getBuilder();

    public NotificationsSynapseQueueStore( Session session ) {

        super( NeuronSynapses.NOTIFICATIONS.name(), session, cassandraTableBuilder );
    }

    @Override
    protected BoundStatement bind( Long key, BoundStatement bs ) {
        return null;
    }

    @Override
    protected BoundStatement bind( Long key, Signal value, BoundStatement bs ) {
        return null;
    }

    @Override
    protected Long mapKey( Row row ) {
        return null;
    }

    @Override
    protected Signal mapValue( ResultSet resultSet ) {
        return null;
    }
}
