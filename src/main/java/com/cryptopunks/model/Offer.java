package com.cryptopunks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
@AllArgsConstructor
public class Offer {

    private final String seller;
    private final String onlySellTo;
    private final BigInteger minValueInWei;

}
