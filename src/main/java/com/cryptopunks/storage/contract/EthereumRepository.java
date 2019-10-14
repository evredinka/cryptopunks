package com.cryptopunks.storage.contract;

import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.OfferRepository;
import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class EthereumRepository implements OfferRepository {

    private final CryptoPunksMarketGateway cryptoPunksMarketGateway;
    private final EthereumDataMapper dataMapper;

    @Override
    public Optional<Offer> getActiveOfferByPunkId(int punkId) {
        RawOffer rawOffer = cryptoPunksMarketGateway.punksOfferedForSale(punkId);
        if (!rawOffer.isForSale()) {
            return Optional.empty();
        }
        RawBid rawBid = cryptoPunksMarketGateway.punkBids(punkId);
        if (!rawBid.hasBid()) {
            return Optional.of(dataMapper.map(rawOffer));
        }
        return Optional.of(dataMapper.map(rawOffer, rawBid));
    }

}
