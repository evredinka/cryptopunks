package com.cryptopunks.storage;

import com.cryptopunks.model.Punk;

import java.util.Optional;

public interface CryptoPunkRepository {

    Optional<Punk> getPunk(int id);
}
