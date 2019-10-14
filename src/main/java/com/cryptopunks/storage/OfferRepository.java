package com.cryptopunks.storage;

import com.cryptopunks.model.Offer;

import java.util.Optional;

public interface OfferRepository {

    Optional<Offer> getActiveOfferByPunkId(int punkId);

}
