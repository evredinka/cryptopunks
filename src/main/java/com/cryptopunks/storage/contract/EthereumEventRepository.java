package com.cryptopunks.storage.contract;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import static com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway.EthEvent;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
class EthereumEventRepository {

    private final CryptoPunksMarketGateway cryptoPunksMarketGateway;

    List<Integer> getOfferIds() {
        CompletableFuture<List<EthEvent>> futureOffered = cryptoPunksMarketGateway.requestPunkOfferedEvents();
        CompletableFuture<List<EthEvent>> futureNotForSale = cryptoPunksMarketGateway.requestPunkNoLongerForSaleEvents();
        CompletableFuture<List<EthEvent>> futureBought = cryptoPunksMarketGateway.requestPunkBoughtEvents();

        CompletableFuture.allOf(futureOffered, futureNotForSale, futureBought).join();

        Map<Integer, Long> punkOfferedEvents = processLogs(futureOffered.join());
        Map<Integer, Long> punkNoLongerForSaleEvents = processLogs(futureNotForSale.join());
        Map<Integer, Long> punkBoughtEvents = processLogs(futureBought.join());

        return punkOfferedEvents.keySet().stream()
                .filter(maybePunkForSale -> {
                    Long offeredOn = punkOfferedEvents.get(maybePunkForSale);
                    Long noLongerForSaleOn = punkNoLongerForSaleEvents.get(maybePunkForSale);
                    Long boughtOn = punkBoughtEvents.get(maybePunkForSale);

                    return isLess(noLongerForSaleOn, offeredOn) && isLess(boughtOn, offeredOn);
                })
                .sorted()
                .collect(toUnmodifiableList());
    }

    private Map<Integer, Long> processLogs(List<EthEvent> events) {
        return events.stream().collect(groupByPunkIdToMaxBlockNumber());
    }

    private boolean isLess(@Nullable Long first, @NonNull Long second) {
        return isNull(first) || first < second;
    }

    private Collector<EthEvent, ?, Map<Integer, Long>> groupByPunkIdToMaxBlockNumber() {
        return groupingBy(EthEvent::getPunkIndex,
                reducing(-1L, EthEvent::getBlockNumber, BinaryOperator.maxBy(Long::compareTo)));
    }

}
