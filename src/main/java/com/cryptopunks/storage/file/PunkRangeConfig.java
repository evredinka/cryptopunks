package com.cryptopunks.storage.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cryptopunks.punkid")
public class PunkRangeConfig {

    private int minPunkId;
    private int maxPunkId;

    public boolean isInRange(int punkId) {
        return punkId >= minPunkId && punkId <= maxPunkId;
    }

}
