package com.example.plc4jscraperdemo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.scraper.ResultHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
@Data
public class ScraperResultHandler implements ResultHandler {

    private String latest = "";

    @Override
    public void handle(String job, String alias, Map<String, Object> results) {
        latest = LocalDateTime.now() + " job=" + job + "; alias=" + alias + "; results=" + results;
        log.debug(latest);
    }

}
