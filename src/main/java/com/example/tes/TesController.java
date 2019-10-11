package com.example.tes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class TesController {

    private final TesService1 tesService1;
    private final TesService2 tesService2;

    @Autowired
    TesController(final TesService1 tesService1, final TesService2 tesService2) {
        this.tesService1 = tesService1;
        this.tesService2 = tesService2;
    }

    @GetMapping(path = "v1/tess/{tesId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getTes1(@PathVariable("tesId") final UUID tesId) {
        return tesService1.getTes(tesId);
    }

    @GetMapping(path = "v2/tess/{tesId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getTes2(@PathVariable("tesId") final UUID tesId) {
        return tesService2.getTes(tesId);
    }
}
