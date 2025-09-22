package com.paczek.demo.mock;

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
        return """
                {
                  "store":{
                    "book":[
                      {
                        "author":"Nigel Rees",
                        "category":"reference",
                        "price":8.95,
                        "title":"Sayings of the Century"
                      },
                      {
                        "author":"Evelyn Waugh",
                        "category":"fiction",
                        "price":12.99,
                        "title":"Sword of Honour"
                      },
                      {
                        "author":"Herman Melville",
                        "category":"fiction",
                        "isbn":"0-5.5.61311-3",
                        "price":8.99,
                        "title":"Moby Dick"
                      },
                      {
                        "author":"J. R. R. Tolkien",
                        "category":"fiction",
                        "isbn":"0-395-19395-8",
                        "price":22.99,
                        "title":"The Lord of the Rings"
                      }
                    ]
                  }
                }
                """;
    }
}
