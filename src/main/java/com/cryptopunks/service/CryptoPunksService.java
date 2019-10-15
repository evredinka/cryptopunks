package com.cryptopunks.service;

import com.cryptopunks.model.Offer;
import com.cryptopunks.model.Punk;
import com.cryptopunks.storage.CryptoPunkRepository;
import com.cryptopunks.storage.OfferRepository;
import com.cryptopunks.web.DTOMapper;
import com.cryptopunks.web.dto.PunkDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoPunksService {

    private final CryptoPunkRepository punkRepository;
    private final OfferRepository offerRepository;
    private final DTOMapper dtoMapper;

    public List<PunkDTO> getPunks(List<String> fields, Boolean activeOffer) {
        throwIfUnsupported(fields, activeOffer);

        List<Integer> offeredPunkIds = offerRepository.getOfferedPunkIds();
        log.debug("Received {} punk ids with active offers", offeredPunkIds.size());
        return dtoMapper.toDTO(offeredPunkIds);
    }

    public PunkDTO getPunk(int id) {
        Punk punk = punkRepository.getPunk(id)
                .orElseThrow(() -> new EntityNotFoundException("No punk found for id " + id));
        log.debug("Received punk {}", punk);

        Optional<Offer> offer = offerRepository.getActiveOfferByPunkId(id);
        log.debug("Received offer {}", offer);

        return dtoMapper.toDTO(punk, offer.orElse(null));
    }

    private void throwIfUnsupported(List<String> fields, Boolean activeOffer) {
        if (fields == null || !fields.equals(singletonList("id"))) {
            throw new UnsupportedOperationException("Only fields=id is supported");
        }
        if (!Boolean.TRUE.equals(activeOffer)) {
            throw new UnsupportedOperationException("Only activeOffer=true is supported");
        }
    }

}
