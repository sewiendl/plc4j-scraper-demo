package com.example.plc4jscraperdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class Plc4jScraperDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Plc4jScraperDemoApplication.class, args);

        CountDownLatch cdl = ctx.getBean(CountDownLatch.class);
        Runtime.getRuntime().addShutdownHook(new Thread(cdl::countDown));
        cdl.await();
    }

}
