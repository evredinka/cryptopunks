package com.cryptopunks.storage.contract;

import com.cryptopunks.model.HighestBid;
import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import org.junit.Test;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
        assertThat(offer.getHighestBid(), is(nullValue()));
    }

    @Test
    public void shouldMapOfferWithHighestBid() {
        RawOffer rawOffer = new RawOffer(new Tuple5<>(true, BigInteger.valueOf(1), "seller", BigInteger.valueOf(100_000), "onlySellTo"));
        RawBid rawBid = new RawBid(new Tuple4<>(true, BigInteger.valueOf(1), "bidder", BigInteger.valueOf(150_000)));

        HighestBid highestBid = new EthereumDataMapper().map(rawOffer, rawBid).getHighestBid();

        assertThat(highestBid.getBidder(), is("bidder"));
        assertThat(highestBid.getValue(), is(BigInteger.valueOf(150_000)));
    }

}
