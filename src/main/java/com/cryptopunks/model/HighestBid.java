package com.cryptopunks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigInteger;

@Value
@Builder
@NonFinal
@AllArgsConstructor
public class HighestBid {

    private final String bidder;
    private final BigInteger value;

}
