package com.cryptopunks;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoPunksController {

    @GetMapping(
            value = "/greeting",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String greeting() {
        return "Hello World!";
    }

}
