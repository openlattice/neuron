package com.dataloom.neuron.pods;

import com.dataloom.neuron.Signal;
import com.dataloom.neuron.synapses.NotificationsSynapseQueueStore;
import com.datastax.driver.core.Session;
import com.kryptnostic.rhizome.queuestores.SelfRegisteringQueueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.EventObject;

@Configuration
public class NeuronQueueStoresPod {

    @Inject
    private Session session;

    @Bean
    public SelfRegisteringQueueStore signalsQueueStore() {

        return new NotificationsSynapseQueueStore( session );
    }

}
