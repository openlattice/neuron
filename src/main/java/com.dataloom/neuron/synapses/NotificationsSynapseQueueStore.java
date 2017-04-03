package com.dataloom.neuron.synapses;

import com.dataloom.neuron.NeuronSynapses;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.kryptnostic.conductor.rpc.odata.Table;
import com.kryptnostic.rhizome.cassandra.CassandraTableBuilder;
import com.kryptnostic.rhizome.queuestores.cassandra.AbstractBaseCassandraQueueStore;

import java.util.EventObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationsSynapseQueueStore extends AbstractBaseCassandraQueueStore<EventObject> {

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
    protected BoundStatement bind( Long key, EventObject value, BoundStatement bs ) {
        return null;
    }

    @Override
    protected Long mapKey( Row row ) {
        return null;
    }

    @Override
    protected EventObject mapValue( ResultSet resultSet ) {
        return null;
    }
}
