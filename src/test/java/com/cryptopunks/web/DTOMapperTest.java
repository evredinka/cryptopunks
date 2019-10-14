package com.cryptopunks.web;

import com.cryptopunks.model.Offer;
import com.cryptopunks.model.Punk;
import com.cryptopunks.web.dto.PunkDTO;
import org.junit.Test;

import java.math.BigInteger;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DTOMapperTest {

    @Test
    public void shouldMapPunkWithoutOffer() {
        Punk punk = Punk.builder()
                .id(1234)
                .gender("Female")
                .accessories(singletonList("Wild Hair"))
                .build();

        PunkDTO dto = new DTOMapper().map(punk, null);

        assertThat(dto.getId(), is(1234));
        assertThat(dto.getGender(), is("Female"));
        assertThat(dto.getAccessories(), is(singletonList("Wild Hair")));
        assertThat(dto.getActiveOffer(), is(nullValue()));
    }

    @Test
    public void shouldMapPunkWithOffer() {
        Punk punk = Punk.builder()
                .id(1234)
                .gender("Female")
                .accessories(singletonList("Wild Hair"))
                .build();
        Offer offer = Offer.builder()
                .seller("seller")
                .onlySellTo("onlySellTo")
                .minValueInWei(BigInteger.valueOf(14))
                .build();

        PunkDTO dto = new DTOMapper().map(punk, offer);

        assertThat(dto.getId(), is(1234));
        assertThat(dto.getGender(), is("Female"));
        assertThat(dto.getAccessories(), is(singletonList("Wild Hair")));

        assertThat(offer.getSeller(), is("seller"));
        assertThat(offer.getOnlySellTo(), is("onlySellTo"));
        assertThat(offer.getMinValueInWei(), is(BigInteger.valueOf(14)));
    }

}
