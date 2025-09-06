package com.paczek.demoRest.controller.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class StoreController {

    @GetMapping("/store")
    public String store() throws IOException, URISyntaxException {
        return Files.readString(Path.of(ClassLoader.getSystemResource("static/store.json").toURI()));
    }
}
