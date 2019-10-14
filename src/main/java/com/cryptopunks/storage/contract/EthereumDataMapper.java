package com.cryptopunks.storage.contract;

import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import org.springframework.stereotype.Component;

@Component
class EthereumDataMapper {

    Offer map(RawOffer rawOffer) {
        return Offer.builder()
                .seller(rawOffer.getSeller())
                .onlySellTo(rawOffer.getOnlySellTo())
                .minValueInWei(rawOffer.getMinValueInWei())
                .build();
    }

}
