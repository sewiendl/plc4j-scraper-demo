package com.example.plc4jscraperdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.scraper.Scraper;
import org.apache.plc4x.java.scraper.config.ScraperConfiguration;
import org.apache.plc4x.java.scraper.exception.ScraperException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScraperService {

    private final ScraperFactory scraperFactory;

    private Scraper scraper;

    private final Object lock = new Object();

    public ScraperService(ScraperFactory scraperFactory) {
        this.scraperFactory = scraperFactory;
    }

    public void start() {
        synchronized (lock) {
            log.debug("start()");
            if (scraper != null && scraper.getNumberOfActiveTasks() == 0) {
                scraper.start();
            }
        }
    }

    public void stop() {
        synchronized (lock) {
            log.debug("stop()");
            if (scraper != null) {
                scraper.stop();
            }
        }
    }

    public void update(ScraperConfiguration scraperConfiguration) throws ScraperException {
        synchronized (lock) {
            log.debug("update()");
            boolean isRunning = false;
            if (scraper != null) {
                isRunning = scraper.getNumberOfActiveTasks() > 0;
            }
            stop();
            scraper = scraperFactory.get(scraperConfiguration);
            if (isRunning) {
                start();
            }
        }
    }

}
