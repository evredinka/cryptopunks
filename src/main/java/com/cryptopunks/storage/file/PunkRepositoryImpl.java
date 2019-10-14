package com.cryptopunks.storage.file;

import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
class PunkRepositoryImpl implements CryptoPunkRepository {

    private final Map<String, Punk> punks;

    PunkRepositoryImpl(PunkPopulator punkPopulator) {
        this.punks = punkPopulator.loadPunks();
    }

    @Override
    public Optional<Punk> getPunk(int punkId) {
        Punk maybePunk = punks.get(String.valueOf(punkId));
        return Optional.ofNullable(maybePunk).map(punk -> punk.setId(punkId));
    }

}
