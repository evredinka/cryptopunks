package com.cryptopunks.storage.contract.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.cryptopunks.storage.contract.gateway.CryptoPunksMarket.*;
import static java.util.stream.Collectors.toList;
import static org.web3j.protocol.core.DefaultBlockParameterName.EARLIEST;
import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;

@RequiredArgsConstructor
public class CryptoPunksMarketGateway {

    private final Web3j web3;
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

    public CompletableFuture<List<EthEvent>> requestPunkOfferedEvents() {
        return requestEvents(PUNKOFFERED_EVENT);
    }

    public CompletableFuture<List<EthEvent>> requestPunkBoughtEvents() {
        return requestEvents(PUNKBOUGHT_EVENT);
    }

    public CompletableFuture<List<EthEvent>> requestPunkNoLongerForSaleEvents() {
        return requestEvents(PUNKNOLONGERFORSALE_EVENT);
    }

    private CompletableFuture<List<EthEvent>> requestEvents(Event event) {
        return web3.ethGetLogs(newFilterForEvent(event)).sendAsync()
                .thenApply(ethLog -> processLogs(ethLog, event));
    }

    private List<EthEvent> processLogs(EthLog ethLog, Event event) {
        return ethLog.getLogs().stream()
                .map(EthLog.LogObject.class::cast)
                .map(log -> new EthEvent(getPunkIndex(log, event), getBlockNumber(log)))
                .collect(toList());
    }

    private int getPunkIndex(EthLog.LogObject log, Event event) {
        TypeReference<Type> typeRef = event.getIndexedParameters().get(0);
        Uint256 punkId = (Uint256) FunctionReturnDecoder.decodeIndexedValue(log.get().getTopics().get(1), typeRef);
        return punkId.getValue().intValue();
    }

    private long getBlockNumber(EthLog.LogObject log) {
        return log.getBlockNumber().longValue();
    }

    private EthFilter newFilterForEvent(Event event) {
        return new EthFilter(EARLIEST, LATEST, cryptoPunksMarket.getContractAddress())
                .addSingleTopic(EventEncoder.encode(event));
    }

    private <T> T executeCall(RemoteCall<T> call) {
        try {
            return call.send();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute remote call to the contract", e);
        }
    }

    @Data
    @AllArgsConstructor
    public static class EthEvent {
        private int punkIndex;
        private long blockNumber;
    }

}
