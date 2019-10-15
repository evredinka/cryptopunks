package com.cryptopunks.storage.file;

import com.cryptopunks.model.Punk;
import org.junit.Test;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PunkRepositoryImplTest {

    @Test
    public void shouldReturnPunkWithData() {
        PunkPopulator punkPopulator = mock(PunkPopulator.class);
        Punk loadedPunk = Punk.builder().id(1234).gender("Female").accessories(singletonList("Hair")).build();
        when(punkPopulator.loadPunks()).thenReturn(singletonList(loadedPunk));

        PunkRangeConfig punkRangeConfig = mock(PunkRangeConfig.class);
        when(punkRangeConfig.isInRange(1234)).thenReturn(true);

        Optional<Punk> maybePunk = new PunkRepositoryImpl(punkPopulator, punkRangeConfig).getPunk(1234);

        assertTrue(maybePunk.isPresent());
        assertThat(maybePunk.get(), is(loadedPunk));
    }

    @Test
    public void shouldReturnPunkWithoutDataIfIdInRange() {
        PunkPopulator punkPopulator = mock(PunkPopulator.class);
        when(punkPopulator.loadPunks())
                .thenReturn(singletonList(Punk.builder().id(1234).build()));

        PunkRangeConfig punkRangeConfig = mock(PunkRangeConfig.class);
        when(punkRangeConfig.isInRange(1)).thenReturn(true);

        Optional<Punk> maybePunk = new PunkRepositoryImpl(punkPopulator, punkRangeConfig).getPunk(1);

        assertTrue(maybePunk.isPresent());
        assertThat(maybePunk.get(), is(Punk.builder().id(1).build()));
    }

    @Test
    public void shouldNotReturnPunkIfIdNotInRange() {
        PunkPopulator punkPopulator = mock(PunkPopulator.class);
        when(punkPopulator.loadPunks())
                .thenReturn(singletonList(Punk.builder().id(1234).build()));

        PunkRangeConfig punkRangeConfig = mock(PunkRangeConfig.class);
        when(punkRangeConfig.isInRange(11_000)).thenReturn(false);

        Optional<Punk> maybePunk = new PunkRepositoryImpl(punkPopulator, punkRangeConfig).getPunk(11_000);

        assertFalse(maybePunk.isPresent());
    }

}
