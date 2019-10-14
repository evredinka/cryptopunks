package com.cryptopunks.web;

import com.cryptopunks.service.CryptoPunksService;
import com.cryptopunks.web.dto.PunkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CryptoPunksController {

    private final CryptoPunksService service;

    @GetMapping(
            value = "/punks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PunkDTO> getPunks(@RequestParam(required = false) List<String> fields,
                                  @RequestParam(required = false) Boolean activeOffer) {
        return service.getPunks(fields, activeOffer);
    }

    @GetMapping(
            value = "/punks/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PunkDTO getPunk(@PathVariable int id) {
        return service.getPunk(id);
    }

}
