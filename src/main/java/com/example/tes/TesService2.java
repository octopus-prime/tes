package com.example.tes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class TesService2 {

    private final FooClient fooClient;
    private final BarClient barClient;
    private final TraceableExecutorService executorService;

    @Autowired
    TesService2(final FooClient fooClient, final BarClient barClient, final TraceableExecutorService executorService) {
        this.fooClient = fooClient;
        this.barClient = barClient;
        this.executorService = executorService;
    }

    String getTes(final UUID tesId) {
        final var getFoo = CompletableFuture.supplyAsync(() -> fooClient.getFoo(tesId), executorService);
        final var getBar = CompletableFuture.supplyAsync(() -> barClient.getBar(tesId), executorService);
        return CompletableFuture.allOf(getFoo, getBar).thenApply(unused -> getFoo.join() + getBar.join()).join();
    }
}
