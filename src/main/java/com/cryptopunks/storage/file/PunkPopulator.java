package com.cryptopunks.storage.file;

import com.cryptopunks.model.Punk;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
class PunkPopulator {

    private final ObjectMapper mapper;
    private final String punkDataPath;

    PunkPopulator(ObjectMapper mapper, @Value("${cryptopunks.punkDataPath}") String punkDataPath) {
        this.mapper = mapper;
        this.punkDataPath = punkDataPath;
    }

    List<Punk> loadPunks() {
        try {
            return readFromFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load punks data", e);
        }
    }

    private List<Punk> readFromFile() throws IOException {
        Map<Integer, PunkBuilder> punks = mapper.readValue(this.getClass().getResourceAsStream(punkDataPath),
                new TypeReference<Map<Integer, PunkBuilder>>() {});
        return punks.entrySet().stream()
                .map((e) -> e.getValue().build(e.getKey())).collect(toList());
    }

    @Data
    public static class PunkBuilder {
        private String gender;
        private List<String> accessories;

        Punk build(int id) {
            return new Punk(id, gender, accessories);
        }
    }

}
