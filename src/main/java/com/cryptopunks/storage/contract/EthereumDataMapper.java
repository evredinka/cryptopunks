package com.cryptopunks.storage.contract;

import com.cryptopunks.model.HighestBid;
import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
class EthereumDataMapper {

    Offer map(RawOffer rawOffer) {
        return map(rawOffer, null);
    }

    Offer map(RawOffer rawOffer, @Nullable RawBid rawBid) {
        return Offer.builder()
                .seller(rawOffer.getSeller())
                .onlySellTo(rawOffer.getOnlySellTo())
                .minValueInWei(rawOffer.getMinValueInWei())
                .highestBid(map(rawBid))
                .build();
    }

    @Nullable
    private HighestBid map(RawBid rawBid) {
        if (isNull(rawBid)) {
            return null;
        }
        return HighestBid.builder()
                .bidder(rawBid.getBidder())
                .value(rawBid.getValue())
                .build();
    }

}
