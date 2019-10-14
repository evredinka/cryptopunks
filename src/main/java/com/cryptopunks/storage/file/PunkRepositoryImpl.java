package com.cryptopunks.storage.file;

import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
class PunkRepositoryImpl implements CryptoPunkRepository {

    private final Map<Integer, Punk> punks;

    PunkRepositoryImpl(PunkPopulator punkPopulator) {
        this.punks = punkPopulator.loadPunks().stream().collect(toMap(Punk::getId, identity()));
    }

    @Override
    public Optional<Punk> getPunk(int punkId) {
        return Optional.ofNullable(punks.get(punkId));
    }

}
