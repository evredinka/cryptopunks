package com.cryptopunks;

import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import com.cryptopunks.web.dto.ErrorDTO;
import com.cryptopunks.web.dto.PunkDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoPunksControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CryptoPunkRepository punkRepository;

    @Test
    public void shouldReturnPunk() {
        when(punkRepository.getPunk(1234)).thenReturn(Optional.of(new Punk(1234)));

        PunkDTO punk = restTemplate.getForObject("/punks/1234", PunkDTO.class);

        assertThat(punk.getId(), is(1234));
    }

    @Test
    public void shouldThrow404() {
        when(punkRepository.getPunk(1234)).thenReturn(Optional.empty());

        ErrorDTO error = restTemplate.getForObject("/punks/1234", ErrorDTO.class);

        assertThat(error.getMessage(), is("No punk found for id 1234"));
    }

    @Test
    public void shouldReturnListOfPunksWithId() {
        List punkIds = restTemplate.getForObject("/punks?fields=id,activeOffer=true", List.class);

        assertThat(punkIds.size(), is(1));
        PunkDTO punk = getNextPunk(punkIds);
        assertThat(punk.getId(), is(1234));
    }

    private PunkDTO getNextPunk(List punkIds) {
        Object next = punkIds.iterator().next();
        return objectMapper.convertValue(next, PunkDTO.class);
    }

}
