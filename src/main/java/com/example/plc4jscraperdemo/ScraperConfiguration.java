package com.example.plc4jscraperdemo;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.scraper.Scraper;
import org.apache.plc4x.java.scraper.config.triggeredscraper.ScraperConfigurationTriggeredImpl;
import org.apache.plc4x.java.scraper.exception.ScraperException;
import org.apache.plc4x.java.scraper.triggeredscraper.TriggeredScraperImpl;
import org.apache.plc4x.java.scraper.triggeredscraper.triggerhandler.collector.TriggerCollectorImpl;
import org.apache.plc4x.java.utils.connectionpool.PooledPlcDriverManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ScraperConfiguration {

    @Bean
    public org.apache.plc4x.java.scraper.config.ScraperConfiguration scrapeConfig() throws IOException {
        return org.apache.plc4x.java.scraper.config.ScraperConfiguration.fromFile("example.yml", ScraperConfigurationTriggeredImpl.class);
    }

    @Bean
    public Scraper scraper(ScraperResultHandler resultHandler) throws ScraperException, IOException {
        return new TriggeredScraperImpl(scrapeConfig(), resultHandler, new TriggerCollectorImpl(plcDriverManager(1_000L)));
    }

    // see https://issues.apache.org/jira/browse/PLC4X-158
    private PlcDriverManager plcDriverManager(long connectionTimeoutMs) {
        return new PooledPlcDriverManager(pooledPlcConnectionFactory -> {
            GenericKeyedObjectPoolConfig<PlcConnection> poolConfig = new GenericKeyedObjectPoolConfig<>();
            poolConfig.setMaxTotalPerKey(1);
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setMaxWaitMillis(connectionTimeoutMs);
            return new GenericKeyedObjectPool<>(pooledPlcConnectionFactory, poolConfig);
        });
    }

}
