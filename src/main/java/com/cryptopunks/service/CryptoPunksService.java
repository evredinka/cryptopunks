package com.cryptopunks.service;

import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import com.cryptopunks.web.dto.PunkDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoPunksService {

    private final CryptoPunkRepository punkRepository;

    public List<PunkDTO> getPunks(List<String> fields, Boolean activeOffer) {
        //todo @evgeniia implement me!
        return singletonList(PunkDTO.builder().id(1234).build());
    }

    public PunkDTO getPunk(int id) {
        Punk punk = punkRepository.getPunk(id)
                .orElseThrow(() -> new EntityNotFoundException("No punk found for id " + id));
        log.debug("Received punk {}", punk);

        return PunkDTO.builder().id(punk.getId()).build();
    }

}
