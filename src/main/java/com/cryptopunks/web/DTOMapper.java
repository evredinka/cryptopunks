package com.cryptopunks.web;

import com.cryptopunks.model.HighestBid;
import com.cryptopunks.model.Offer;
import com.cryptopunks.model.Punk;
import com.cryptopunks.web.dto.HighestBidDTO;
import com.cryptopunks.web.dto.OfferDTO;
import com.cryptopunks.web.dto.PunkDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class DTOMapper {

    public List<PunkDTO> toDTO(List<Integer> punkIds) {
        return punkIds.stream()
                .map(id -> PunkDTO.builder().id(id).build())
                .collect(toList());
    }

    public PunkDTO toDTO(Punk punk, @Nullable Offer offer) {
        return PunkDTO.builder()
                .id(punk.getId())
                .gender(punk.getGender())
                .accessories(punk.getAccessories())
                .activeOffer(buildOfferDTO(offer))
                .build();
    }

    @Nullable
    private OfferDTO buildOfferDTO(@Nullable Offer offer) {
        if (isNull(offer)) {
            return null;
        }
        return OfferDTO.builder()
                .seller(offer.getSeller())
                .onlySellTo(offer.getOnlySellTo())
                .minValueInWei(offer.getMinValueInWei())
                .highestBid(buildHighestBidDTO(offer.getHighestBid()))
                .build();
    }

    @Nullable
    private HighestBidDTO buildHighestBidDTO(@Nullable HighestBid highestBid) {
        if (isNull(highestBid)) {
            return null;
        }
        return HighestBidDTO.builder()
                .bidder(highestBid.getBidder())
                .value(highestBid.getValue())
                .build();
    }

}
