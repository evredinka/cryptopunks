package com.cryptopunks.service;

import com.cryptopunks.model.Offer;
import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import com.cryptopunks.storage.OfferRepository;
import com.cryptopunks.web.DTOMapper;
import org.junit.Test;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CryptoPunksServiceTest {

    @Test
    public void shouldMapOfferIds() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);

        when(offerRepository.getOfferedPunkIds()).thenReturn(singletonList(1234));

        new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunks(singletonList("id"), true);

        verify(dtoMapper).toDTO(singletonList(1234));
    }

    @Test
    public void shouldThrowIfActiveOfferFalse() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);

        Throwable ex = catchThrowable(() -> new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunks(singletonList("id"), false));

        assertThat(ex.getMessage(), is("Only activeOffer=true is supported"));
    }

    @Test
    public void shouldThrowIfNotIdField() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);

        Throwable ex = catchThrowable(() -> new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunks(singletonList("gender"), true));

        assertThat(ex.getMessage(), is("Only fields=id is supported"));
    }

    @Test
    public void shouldMapPunkWithOffer() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);
        Punk punk = Punk.builder().id(12334).build();
        when(punkRepository.getPunk(1234)).thenReturn(Optional.of(punk));
        Offer offer = Offer.builder().build();
        when(offerRepository.getActiveOfferByPunkId(1234)).thenReturn(Optional.of(offer));

        new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunk(1234);

        verify(dtoMapper).toDTO(punk, offer);
    }

    @Test
    public void shouldMapPunkWithoutOffer() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);
        Punk punk = Punk.builder().id(12334).build();
        when(punkRepository.getPunk(1234)).thenReturn(Optional.of(punk));
        when(offerRepository.getActiveOfferByPunkId(1234)).thenReturn(Optional.empty());

        new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunk(1234);

        verify(dtoMapper).toDTO(punk, null);
    }

    @Test
    public void shouldThrowIfNoPunkFound() {
        CryptoPunkRepository punkRepository = mock(CryptoPunkRepository.class);
        OfferRepository offerRepository = mock(OfferRepository.class);
        DTOMapper dtoMapper = mock(DTOMapper.class);
        when(punkRepository.getPunk(1234)).thenReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> new CryptoPunksService(punkRepository, offerRepository, dtoMapper).getPunk(1234));

        assertThat(ex.getMessage(), is("No punk found for id 1234"));
    }

}
