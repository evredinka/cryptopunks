package com.cryptopunks.storage.contract;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import org.junit.Test;

import java.util.List;

import static com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway.EthEvent;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EthereumEventRepositoryTest {

    @Test
    public void shouldReturnPunkOfferedId() {
        CryptoPunksMarketGateway marketGateway = mock(CryptoPunksMarketGateway.class);
        int punkOffered = 1;
        int punkNoLongerForSale = 2;
        int punkBought = 3;
        when(marketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 10_000))));
        when(marketGateway.requestPunkNoLongerForSaleEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkNoLongerForSale, 10_000))));
        when(marketGateway.requestPunkBoughtEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkBought, 10_000))));

        List<Integer> offerIds = new EthereumEventRepository(marketGateway).getOfferIds();

        assertThat(offerIds, contains(punkOffered));
    }

    @Test
    public void shouldNotReturnOfferedPunkIfItIsNoLongerForSale() {
        CryptoPunksMarketGateway marketGateway = mock(CryptoPunksMarketGateway.class);
        int punkOffered = 1;
        when(marketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 10_000))));
        when(marketGateway.requestPunkNoLongerForSaleEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 20_000))));
        when(marketGateway.requestPunkBoughtEvents()).thenReturn(completedFuture(emptyList()));

        List<Integer> offerIds = new EthereumEventRepository(marketGateway).getOfferIds();

        assertThat(offerIds, is(emptyList()));
    }

    @Test
    public void shouldNotReturnOfferedPunkIfItIsBought() {
        CryptoPunksMarketGateway marketGateway = mock(CryptoPunksMarketGateway.class);
        int punkOffered = 1;
        when(marketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 10_000))));
        when(marketGateway.requestPunkNoLongerForSaleEvents()).thenReturn(completedFuture(emptyList()));
        when(marketGateway.requestPunkBoughtEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 20_000))));

        List<Integer> offerIds = new EthereumEventRepository(marketGateway).getOfferIds();

        assertThat(offerIds, is(emptyList()));
    }

    @Test
    public void shouldReturnOfferedPunkIfHeWasOfferedAfterHeWasBought() {
        CryptoPunksMarketGateway marketGateway = mock(CryptoPunksMarketGateway.class);
        int punkOffered = 1;
        int punkBought = 3;
        when(marketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 20_000))));
        when(marketGateway.requestPunkNoLongerForSaleEvents()).thenReturn(completedFuture(emptyList()));
        when(marketGateway.requestPunkBoughtEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkBought, 10_000))));

        List<Integer> offerIds = new EthereumEventRepository(marketGateway).getOfferIds();

        assertThat(offerIds, contains(punkOffered));
    }

    @Test
    public void shouldReturnOfferedPunkIfHeWasOfferedAfterHeWasNoLongerForSale() {
        CryptoPunksMarketGateway marketGateway = mock(CryptoPunksMarketGateway.class);
        int punkOffered = 1;
        when(marketGateway.requestPunkOfferedEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 20_000))));
        when(marketGateway.requestPunkNoLongerForSaleEvents())
                .thenReturn(completedFuture(singletonList(new EthEvent(punkOffered, 10_000))));
        when(marketGateway.requestPunkBoughtEvents())
                .thenReturn(completedFuture(emptyList()));

        List<Integer> offerIds = new EthereumEventRepository(marketGateway).getOfferIds();

        assertThat(offerIds, contains(punkOffered));
    }

}
