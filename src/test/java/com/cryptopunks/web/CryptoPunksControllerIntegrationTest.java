package com.cryptopunks.web;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import com.cryptopunks.web.dto.ErrorDTO;
import com.cryptopunks.web.dto.HighestBidDTO;
import com.cryptopunks.web.dto.OfferDTO;
import com.cryptopunks.web.dto.PunkDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoPunksControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CryptoPunksMarketGateway cryptoPunksMarketGateway;

    @Test
    public void shouldReturnPunkWithoutOffer() {
        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(false);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234)).thenReturn(rawOffer);

        ResponseEntity<PunkDTO> response = httpGet("/punks/1234", PunkDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        PunkDTO punk = response.getBody();
        assertThat(punk, is(notNullValue()));
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

        ResponseEntity<PunkDTO> response = httpGet("/punks/1234", PunkDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        OfferDTO offer = response.getBody().getActiveOffer();
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

        ResponseEntity<PunkDTO> response = httpGet("/punks/1234", PunkDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        HighestBidDTO highestBid = response.getBody().getActiveOffer().getHighestBid();
        assertThat(highestBid.getBidder(), is("bidder"));
        assertThat(highestBid.getValue(), is(BigInteger.valueOf(14_000)));
    }

    @Test
    public void shouldReturnPunkIfIdInRangeButNoPunkData() {
        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(false);
        when(cryptoPunksMarketGateway.punksOfferedForSale(10)).thenReturn(rawOffer);

        ResponseEntity<PunkDTO> response = httpGet("/punks/10", PunkDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        PunkDTO punk = response.getBody();
        assertThat(punk.getId(), is(10));
        assertThat(punk.getGender(), is(nullValue()));
        assertThat(punk.getAccessories(), is(nullValue()));
        assertThat(punk.getActiveOffer(), is(nullValue()));
    }

    @Test
    public void shouldReturnNotFoundIfPunkNotFound() {
        ResponseEntity<ErrorDTO> response = httpGet("/punks/11111", ErrorDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getMessage(), is("No punk found for id 11111"));
    }

    @Test
    public void shouldReturnListOfOfferedPunksWithId() {
        when(cryptoPunksMarketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new CryptoPunksMarketGateway.EthEvent(1234, 87563412))));
        when(cryptoPunksMarketGateway.requestPunkNoLongerForSaleEvents()).thenReturn(completedFuture(emptyList()));
        when(cryptoPunksMarketGateway.requestPunkBoughtEvents()).thenReturn(completedFuture(emptyList()));

        ResponseEntity<List<PunkDTO>> response = httpGet("/punks?fields=id&activeOffer=true", new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), contains(PunkDTO.builder().id(1234).build()));
    }

    @Test
    public void shouldReturnErrorIfActiveOfferFalse() {
        ResponseEntity<ErrorDTO> response = httpGet("/punks?fields=id&activeOffer=false", ErrorDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getMessage(), is("Only activeOffer=true is supported"));
    }

    @Test
    public void shouldReturnErrorIfGenderFieldRequested() {
        ResponseEntity<ErrorDTO> response = httpGet("/punks?fields=gender&activeOffer=true", ErrorDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getMessage(), is("Only fields=id is supported"));
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

    private <T> ResponseEntity<T> httpGet(String url, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType);
    }

    private <T> ResponseEntity<T> httpGet(String s, Class<T> responseType) {
        return restTemplate.exchange(s, HttpMethod.GET, null, responseType);
    }

}
