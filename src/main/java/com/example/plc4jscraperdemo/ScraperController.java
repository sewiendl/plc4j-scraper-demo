package com.example.plc4jscraperdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(path = "/scraper-service/active-task-count", produces = "application/json")
    public ResponseEntity getActiveTaskCount() {
        return ResponseEntity.ok(service.getNumberOfActiveTasks());
    }

    @GetMapping(path = "/scraper-service/latest", produces = "application/json")
    public ResponseEntity getLatest() {
        return ResponseEntity.ok(resultHandler.getLatest());
    }

}
