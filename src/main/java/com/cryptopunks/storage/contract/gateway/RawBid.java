package com.cryptopunks.storage.contract.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;

@Value
@Builder
@NonFinal
@AllArgsConstructor
public class RawBid {

    private final Tuple4<Boolean, BigInteger, String, BigInteger> data;

    public boolean hasBid() {
        return data.component1();
    }

    public String getBidder() {
        return data.component3();
    }

    public BigInteger getValue() {
        return data.component4();
    }
}
