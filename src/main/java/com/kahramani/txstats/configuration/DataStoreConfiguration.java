package com.kahramani.txstats.configuration;

import com.kahramani.txstats.store.TransactionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataStoreConfiguration {

    @Bean
    public TransactionStore transactionStore() {
        return TransactionStore.getInstance();
    }
}