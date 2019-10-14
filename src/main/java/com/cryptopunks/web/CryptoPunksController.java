package com.cryptopunks.web;

import com.cryptopunks.web.dto.PunkDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.singletonList;

@RestController
public class CryptoPunksController {

    @GetMapping(
            value = "/punks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PunkDTO> getPunks(@RequestParam(required = false) List<String> fields,
                                  @RequestParam(required = false) Boolean activeOffer) {
        return singletonList(PunkDTO.builder().id(1234).build());
    }

    @GetMapping(
            value = "/punks/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PunkDTO getPunk(@PathVariable int id) {
        return PunkDTO.builder().id(id).build();
    }

}
