package com.example.plc4jscraperdemo;

import org.apache.plc4x.java.scraper.Scraper;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource
public class ScraperService {

    private final Scraper scraper;

    private final Object lock = new Object();

    public ScraperService(Scraper scraper) {
        this.scraper = scraper;
    }

    @ManagedOperation
    public void start() {
        synchronized (lock) {
            if (scraper.getNumberOfActiveTasks() == 0) {
                scraper.start();
            }
        }
    }

    @ManagedOperation
    public void stop() {
        synchronized (lock) {
            scraper.stop();
        }
    }

    @ManagedAttribute
    public int getNumberOfActiveTasks() {
        return scraper.getNumberOfActiveTasks();
    }

}
