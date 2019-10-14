package com.cryptopunks.web;

import com.cryptopunks.model.HighestBid;
import com.cryptopunks.model.Offer;
import com.cryptopunks.model.Punk;
import com.cryptopunks.web.dto.HighestBidDTO;
import com.cryptopunks.web.dto.OfferDTO;
import com.cryptopunks.web.dto.PunkDTO;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DTOMapperTest {

    @Test
    public void shouldMapPunk() {
        Punk punk = newPunk(1234, "Female", singletonList("Wild Hair"));
        Offer offer = null;

        PunkDTO dto = new DTOMapper().map(punk, offer);

        assertThat(dto.getId(), is(1234));
        assertThat(dto.getGender(), is("Female"));
        assertThat(dto.getAccessories(), is(singletonList("Wild Hair")));
        assertThat(dto.getActiveOffer(), is(nullValue()));
    }

    @Test
    public void shouldMapPunkWithOffer() {
        Punk punk = newPunk(1234, "Female", singletonList("Wild Hair"));
        Offer offer = newOffer("seller", "onlySellTo", 15_000);

        OfferDTO dto = new DTOMapper().map(punk, offer).getActiveOffer();

        assertThat(dto.getSeller(), is("seller"));
        assertThat(dto.getOnlySellTo(), is("onlySellTo"));
        assertThat(dto.getMinValueInWei(), is(BigInteger.valueOf(15_000)));
        assertThat(dto.getHighestBid(), is(nullValue()));
    }

    @Test
    public void shouldMapHighestBid() {
        Punk punk = newPunk(1234, "Female", singletonList("Wild Hair"));
        HighestBid highestBid = HighestBid.builder()
                .bidder("bidder")
                .value(BigInteger.valueOf(15_000))
                .build();
        Offer offer = newOffer("seller", "onlySellTo", 15_000, highestBid);

        HighestBidDTO dto = new DTOMapper().map(punk, offer).getActiveOffer().getHighestBid();

        assertThat(dto.getBidder(), is("bidder"));
        assertThat(dto.getValue(), is(BigInteger.valueOf(15_000)));
    }

    private Punk newPunk(int id, String gender, List<String> accessories) {
        return Punk.builder()
                .id(id)
                .gender(gender)
                .accessories(accessories)
                .build();
    }

    private Offer newOffer(String seller, String onlySellTo, int value) {
        return newOffer(seller, onlySellTo, value, null);
    }

    private Offer newOffer(String seller, String onlySellTo, int value, HighestBid bid) {
        return Offer.builder()
                .seller(seller)
                .onlySellTo(onlySellTo)
                .minValueInWei(BigInteger.valueOf(value))
                .highestBid(bid)
                .build();
    }

}
