package com.cryptopunks.storage.contract;

import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import org.junit.Test;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EthereumDataMapperTest {

    @Test
    public void shouldMapOffer() {
        EthereumDataMapper dataMapper = new EthereumDataMapper();
        var data = new Tuple5<>(true, BigInteger.valueOf(1), "seller", BigInteger.valueOf(100_000), "onlySellTo");

        Offer offer = dataMapper.map(new RawOffer(data));

        assertThat(offer.getSeller(), is("seller"));
        assertThat(offer.getOnlySellTo(), is("onlySellTo"));
        assertThat(offer.getMinValueInWei(), is(BigInteger.valueOf(100_000)));
    }

}
