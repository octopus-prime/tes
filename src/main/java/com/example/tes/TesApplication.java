package com.example.tes;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootApplication
@EnableFeignClients
public class TesApplication {

    private static final int PORT = 9999;
    private final WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(PORT));

    public static void main(final String[] args) {
        SpringApplication.run(TesApplication.class, args);
    }

    @Bean
    TraceableExecutorService traceableExecutorService(final BeanFactory beanFactory) {
        return new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool(), "voodoo priest");
    }

    @PostConstruct
    void startWiremock() {
        wireMockServer.start();

        configureFor(PORT);
        givenThat(get("/foos/bf73ce21-f91b-4619-8891-1b4b471db3fd").willReturn(okJson("foo")));
        givenThat(get("/bars/bf73ce21-f91b-4619-8891-1b4b471db3fd").willReturn(okJson("bar")));
    }

    @PreDestroy
    void stopWiremock() {
        wireMockServer.stop();
    }
}
