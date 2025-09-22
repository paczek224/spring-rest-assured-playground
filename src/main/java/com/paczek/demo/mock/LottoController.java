package com.paczek.demo.mock;

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
        return """
                {
                  "lotto":{
                    "lottoId":5,
                    "winning-numbers":[2,45,34,23,7,5,3],
                    "winners":[{
                      "winnerId":23,
                      "numbers":[2,45,34,23,3,5]
                    },{
                      "winnerId":54,
                      "numbers":[52,3,12,11,18,22]
                    }]
                  }
                }
                """;
    }
}
