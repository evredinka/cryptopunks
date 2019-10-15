package com.cryptopunks.storage.contract;

import com.cryptopunks.model.Offer;
import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import com.cryptopunks.storage.contract.gateway.RawBid;
import com.cryptopunks.storage.contract.gateway.RawOffer;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class EthereumRepositoryTest {

    @Test
    public void shouldMapOfferWithHighestBid() {
        CryptoPunksMarketGateway gateway = mock(CryptoPunksMarketGateway.class);
        EthereumDataMapper dataMapper = mock(EthereumDataMapper.class);
        when(dataMapper.map(any(RawOffer.class), any(RawBid.class))).thenReturn(mock(Offer.class));

        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(true);
        when(gateway.punksOfferedForSale(1234)).thenReturn(rawOffer);

        RawBid rawBid = mock(RawBid.class);
        when(rawBid.hasBid()).thenReturn(true);
        when(gateway.punkBids(1234)).thenReturn(rawBid);

        new EthereumRepository(gateway, mock(EthereumEventRepository.class), dataMapper).getActiveOfferByPunkId(1234);

        verify(dataMapper).map(rawOffer, rawBid);
    }

    @Test
    public void shouldNotMapOfferIfNotForSale() {
        CryptoPunksMarketGateway gateway = mock(CryptoPunksMarketGateway.class);
        EthereumDataMapper dataMapper = mock(EthereumDataMapper.class);

        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(false);
        when(gateway.punksOfferedForSale(1234)).thenReturn(rawOffer);

        new EthereumRepository(gateway, mock(EthereumEventRepository.class), dataMapper).getActiveOfferByPunkId(1234);

        verifyZeroInteractions(dataMapper);
    }

    @Test
    public void shouldNotMapBidIfNoBid() {
        CryptoPunksMarketGateway gateway = mock(CryptoPunksMarketGateway.class);
        EthereumDataMapper dataMapper = mock(EthereumDataMapper.class);
        when(dataMapper.map(any(RawOffer.class))).thenReturn(mock(Offer.class));

        RawOffer rawOffer = mock(RawOffer.class);
        when(rawOffer.isForSale()).thenReturn(true);
        when(gateway.punksOfferedForSale(1234)).thenReturn(rawOffer);

        RawBid rawBid = mock(RawBid.class);
        when(rawBid.hasBid()).thenReturn(false);
        when(gateway.punkBids(1234)).thenReturn(rawBid);

        new EthereumRepository(gateway, mock(EthereumEventRepository.class), dataMapper).getActiveOfferByPunkId(1234);

        verify(dataMapper).map(rawOffer);
    }

}
