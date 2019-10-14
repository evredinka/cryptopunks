package com.cryptopunks.storage.contract.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;

@Value
@Builder
@AllArgsConstructor
public class RawOffer {

    private final Tuple5<Boolean, BigInteger, String, BigInteger, String> data;

    public boolean isForSale() {
        return data.component1();
    }

    public String getSeller() {
        return data.component3();
    }

    public String getOnlySellTo() {
        return data.component5();
    }

    public BigInteger getMinValueInWei() {
        return data.component4();
    }

}
