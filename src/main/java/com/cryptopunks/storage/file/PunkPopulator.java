package com.cryptopunks.storage.file;

import com.cryptopunks.model.Punk;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
class PunkPopulator {

    private final ObjectMapper mapper;

    PunkPopulator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    Map<String, Punk> loadPunks() {
        try {
            Map<String, Punk> punks = Collections.unmodifiableMap(readFromFile());
            log.info("Loaded {} punk(s)", punks.size());
            return punks;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load punks data", e);
        }
    }

    private Map<String, Punk> readFromFile() throws IOException {
        return mapper.readValue(this.getClass().getResourceAsStream("/punkDetails.json"), new TypeReference<Map<String, Punk>>() {});
    }

}
