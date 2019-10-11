package com.example.tes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "bar-service")
interface BarClient {

    @GetMapping(path = "bars/{barId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getBar(@PathVariable("barId") UUID barId);
}
