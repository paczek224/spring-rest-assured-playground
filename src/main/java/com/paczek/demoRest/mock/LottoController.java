package com.paczek.demoRest.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class LottoController {

    @GetMapping("/lotto")
    public String lotto() throws IOException, URISyntaxException {
        return Files.readString(Path.of(ClassLoader.getSystemResource("static/lotto.json").toURI()));
    }
}
