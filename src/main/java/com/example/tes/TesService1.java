package com.example.tes;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
class TesService1 {

    private final FooClient fooClient;
    private final BarClient barClient;
    private final TraceableExecutorService executorService;

    @Autowired
    TesService1(final FooClient fooClient, final BarClient barClient, final BeanFactory beanFactory) {
        this.fooClient = fooClient;
        this.barClient = barClient;
        executorService = new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool(), "voodoo priest");
    }

    String getTes(final UUID tesId) {
        final var getFoo = CompletableFuture.supplyAsync(() -> fooClient.getFoo(tesId), executorService);
        final var getBar = CompletableFuture.supplyAsync(() -> barClient.getBar(tesId), executorService);
        return CompletableFuture.allOf(getFoo, getBar).thenApply(unused -> getFoo.join() + getBar.join()).join();
    }
}
