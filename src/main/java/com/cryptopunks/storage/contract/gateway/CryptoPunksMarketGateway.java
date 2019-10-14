package com.cryptopunks.storage.contract.gateway;

import lombok.RequiredArgsConstructor;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;

@RequiredArgsConstructor
public class CryptoPunksMarketGateway {

    private final CryptoPunksMarket cryptoPunksMarket;

    public RawOffer punksOfferedForSale(int punkId) {
        Tuple5<Boolean, BigInteger, String, BigInteger, String> res =
                executeCall(cryptoPunksMarket.punksOfferedForSale(BigInteger.valueOf(punkId)));
        return new RawOffer(res);
    }

    public RawBid punkBids(int punkId) {
        Tuple4<Boolean, BigInteger, String, BigInteger> res =
                executeCall(cryptoPunksMarket.punkBids(BigInteger.valueOf(punkId)));
        return new RawBid(res);
    }

    private <T> T executeCall(RemoteCall<T> call) {
        try {
            return call.send();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute remote call to the contract", e);
        }
    }

}
