package com.cryptopunks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class Punk {

    private final int id;
    private final String gender;
    private final List<String> accessories;

}
