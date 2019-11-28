package com.example.plc4jscraperdemo;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.scraper.Scraper;
import org.apache.plc4x.java.scraper.config.triggeredscraper.ScraperConfigurationTriggeredImpl;
import org.apache.plc4x.java.scraper.exception.ScraperException;
import org.apache.plc4x.java.scraper.triggeredscraper.TriggeredScraperImpl;
import org.apache.plc4x.java.scraper.triggeredscraper.triggerhandler.collector.TriggerCollectorImpl;
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
        return new TriggeredScraperImpl(scrapeConfig(), resultHandler, new TriggerCollectorImpl(new PlcDriverManager()));
    }

}
