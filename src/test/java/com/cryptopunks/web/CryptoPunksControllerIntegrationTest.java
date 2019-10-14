package com.cryptopunks.web;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import com.cryptopunks.web.dto.ErrorDTO;
import com.cryptopunks.web.dto.PunkDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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
    public void shouldReturnPunkWithOffer() {
        boolean isForSale = true;
        BigInteger punkIndex = BigInteger.valueOf(1234);
        String seller = "seller";
        BigInteger minValue = BigInteger.valueOf(12_300);
        String onlySellTo = "onlySellTo";

        var data = new Tuple5<>(isForSale, punkIndex, seller, minValue, onlySellTo);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234))
                .thenReturn(RawOffer.builder().data(data).build());

        PunkDTO punk = restTemplate.getForObject("/punks/1234", PunkDTO.class);

        assertThat(punk.getId(), is(1234));
        assertThat(punk.getGender(), is("Female"));
        assertThat(punk.getAccessories(), is(singletonList("Wild Hair")));
        assertThat(punk.getActiveOffer().getSeller(), is("seller"));
        assertThat(punk.getActiveOffer().getOnlySellTo(), is("onlySellTo"));
        assertThat(punk.getActiveOffer().getMinValueInWei(), is(BigInteger.valueOf(12_300)));
    }

    @Test
    public void shouldReturnPunkWithoutOffer() {
        boolean isForSale = false;
        BigInteger punkIndex = BigInteger.valueOf(1234);
        String seller = "seller";
        BigInteger minValue = BigInteger.valueOf(12_300);
        String onlySellTo = "onlySellTo";

        var data = new Tuple5<>(isForSale, punkIndex, seller, minValue, onlySellTo);
        when(cryptoPunksMarketGateway.punksOfferedForSale(1234))
                .thenReturn(RawOffer.builder().data(data).build());

        PunkDTO punk = restTemplate.getForObject("/punks/1234", PunkDTO.class);

        assertThat(punk.getId(), is(1234));
        assertThat(punk.getGender(), is("Female"));
        assertThat(punk.getAccessories(), is(singletonList("Wild Hair")));
        assertThat(punk.getActiveOffer(), is(nullValue()));
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

    private PunkDTO getNextPunk(List punkIds) {
        Object next = punkIds.iterator().next();
        return objectMapper.convertValue(next, PunkDTO.class);
    }

}
