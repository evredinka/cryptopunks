package com.cryptopunks.storage;

import com.cryptopunks.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Optional<Offer> getActiveOfferByPunkId(int punkId);

    List<Integer> getOfferedPunkIds();
}
