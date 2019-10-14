package com.cryptopunks.web;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import com.cryptopunks.web.dto.ErrorDTO;
import com.cryptopunks.web.dto.HighestBidDTO;
import com.cryptopunks.web.dto.OfferDTO;
import com.cryptopunks.web.dto.PunkDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoPunksControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CryptoPunksMarketGateway cryptoPunksMarketGateway;

    @Test
    public void shouldReturnPunkWithoutOffer() {
        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(false);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234)).thenReturn(rawOffer);

        PunkDTO punk = restTemplate.getForObject("/punks/1234", PunkDTO.class);

        assertThat(punk.getId(), is(1234));
        assertThat(punk.getGender(), is("Female"));
        assertThat(punk.getAccessories(), is(singletonList("Wild Hair")));
        assertThat(punk.getActiveOffer(), is(nullValue()));
    }

    @Test
    public void shouldReturnPunkWithOffer() {
        RawOffer rawOffer = mockActiveOffer("seller", "onlySellTo", 12_300);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234)).thenReturn(rawOffer);
        when(cryptoPunksMarketGateway.punkBids(1234)).thenReturn(mock(RawBid.class));

        OfferDTO offer = restTemplate.getForObject("/punks/1234", PunkDTO.class).getActiveOffer();

        assertThat(offer.getSeller(), is("seller"));
        assertThat(offer.getOnlySellTo(), is("onlySellTo"));
        assertThat(offer.getMinValueInWei(), is(BigInteger.valueOf(12_300)));

        assertThat(offer.getHighestBid(), is(nullValue()));
    }

    @Test
    public void shouldReturnPunkWithOfferWithHighestBid() {
        RawOffer rawOffer = mockActiveOffer("seller", "onlySellTo", 12_300);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234)).thenReturn(rawOffer);
        RawBid rawBid = mockActiveBid("bidder", 14_000);
        when(cryptoPunksMarketGateway.punkBids(1234)).thenReturn(rawBid);

        PunkDTO punk = restTemplate.getForObject("/punks/1234", PunkDTO.class);

        HighestBidDTO highestBid = punk.getActiveOffer().getHighestBid();
        assertThat(highestBid.getBidder(), is("bidder"));
        assertThat(highestBid.getValue(), is(BigInteger.valueOf(14_000)));
    }

    @Test
    public void shouldReturnErrorIfPunkNotFound() {
        ErrorDTO error = restTemplate.getForObject("/punks/10", ErrorDTO.class);

        assertThat(error.getMessage(), is("No punk found for id 10"));
    }

    @Test
    public void shouldReturnListOfPunksWithId() {
        List punkIds = restTemplate.getForObject("/punks?fields=id,activeOffer=true", List.class);

        assertThat(punkIds.size(), is(1));
        PunkDTO punk = getNextPunk(punkIds);
        assertThat(punk.getId(), is(1234));
    }

    private RawBid mockActiveBid(String bidder, int value) {
        RawBid rawBid = mock(RawBid.class);
        when(rawBid.hasBid()).thenReturn(true);
        when(rawBid.getBidder()).thenReturn(bidder);
        when(rawBid.getValue()).thenReturn(BigInteger.valueOf(value));
        return rawBid;
    }

    private RawOffer mockActiveOffer(String seller, String onlySellTo, int value) {
        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(true);
        when(rawOffer.getSeller()).thenReturn(seller);
        when(rawOffer.getOnlySellTo()).thenReturn(onlySellTo);
        when(rawOffer.getMinValueInWei()).thenReturn(BigInteger.valueOf(value));
        return rawOffer;
    }

    private PunkDTO getNextPunk(List punkIds) {
        Object next = punkIds.iterator().next();
        return objectMapper.convertValue(next, PunkDTO.class);
    }

}
