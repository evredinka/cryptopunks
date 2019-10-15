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
    private final PunkRangeConfig punkRangeConfig;

    PunkRepositoryImpl(PunkPopulator punkPopulator,
                       PunkRangeConfig punkRangeConfig) {
        this.punks = punkPopulator.loadPunks().stream().collect(toMap(Punk::getId, identity()));
        this.punkRangeConfig = punkRangeConfig;
    }

    @Override
    public Optional<Punk> getPunk(int punkId) {
        if (punkRangeConfig.isInRange(punkId)) {
            return Optional.of(punks.getOrDefault(punkId, Punk.builder().id(punkId).build()));
        }
        return Optional.empty();
    }

}
