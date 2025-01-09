package org.app.battleshiproyale.config;

import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunrConfig {
    @Bean
    public StorageProvider storageProvider() {
        return new InMemoryStorageProvider();
    }
}