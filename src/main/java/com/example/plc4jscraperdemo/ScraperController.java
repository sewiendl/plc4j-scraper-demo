package com.example.plc4jscraperdemo;

import org.apache.plc4x.java.scraper.config.triggeredscraper.ScraperConfigurationTriggeredImpl;
import org.apache.plc4x.java.scraper.exception.ScraperException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScraperController {

    private final ScraperService service;
    private final ScraperResultHandler resultHandler;

    public ScraperController(ScraperService service, ScraperResultHandler resultHandler) {
        this.service = service;
        this.resultHandler = resultHandler;
    }

    @PostMapping("/scraper-service/start")
    public ResponseEntity start() {
        service.start();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/scraper-service/stop")
    public ResponseEntity stop() {
        service.stop();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/scraper-service/update")
    public ResponseEntity update(@RequestBody ScraperConfigurationTriggeredImpl scraperConfiguration) throws ScraperException {
        service.update(scraperConfiguration);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/scraper-service/latest", produces = "application/json")
    public ResponseEntity getLatest() {
        return ResponseEntity.ok(resultHandler.getLatest());
    }

}
