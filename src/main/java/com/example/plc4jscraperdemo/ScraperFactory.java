package com.example.plc4jscraperdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.scraper.ResultHandler;
import org.apache.plc4x.java.scraper.Scraper;
import org.apache.plc4x.java.scraper.config.ScraperConfiguration;
import org.apache.plc4x.java.scraper.exception.ScraperException;
import org.apache.plc4x.java.scraper.triggeredscraper.TriggeredScraperImpl;
import org.apache.plc4x.java.scraper.triggeredscraper.triggerhandler.collector.TriggerCollectorImpl;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScraperFactory {

    private final ResultHandler resultHandler;

    private final PlcDriverManager plcDriverManager;

    public ScraperFactory(ResultHandler resultHandler, PlcDriverManager plcDriverManager) {
        this.resultHandler = resultHandler;
        this.plcDriverManager = plcDriverManager;
    }

    public Scraper get(ScraperConfiguration scraperConfiguration) throws ScraperException {
        return new TriggeredScraperImpl(scraperConfiguration, resultHandler, new TriggerCollectorImpl(plcDriverManager));
    }

}
