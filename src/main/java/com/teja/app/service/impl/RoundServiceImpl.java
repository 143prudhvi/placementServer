package com.teja.app.service.impl;

import com.teja.app.domain.Round;
import com.teja.app.repository.RoundRepository;
import com.teja.app.service.RoundService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Round}.
 */
@Service
@Transactional
public class RoundServiceImpl implements RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundServiceImpl.class);

    private final RoundRepository roundRepository;

    public RoundServiceImpl(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public Round save(Round round) {
        log.debug("Request to save Round : {}", round);
        return roundRepository.save(round);
    }

    @Override
    public Optional<Round> partialUpdate(Round round) {
        log.debug("Request to partially update Round : {}", round);

        return roundRepository
            .findById(round.getId())
            .map(
                existingRound -> {
                    if (round.getStartDate() != null) {
                        existingRound.setStartDate(round.getStartDate());
                    }
                    if (round.getEndDate() != null) {
                        existingRound.setEndDate(round.getEndDate());
                    }
                    if (round.getDuration() != null) {
                        existingRound.setDuration(round.getDuration());
                    }
                    if (round.getSkillsRequired() != null) {
                        existingRound.setSkillsRequired(round.getSkillsRequired());
                    }
                    if (round.getLink() != null) {
                        existingRound.setLink(round.getLink());
                    }

                    return existingRound;
                }
            )
            .map(roundRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Round> findAll() {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Round> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.deleteById(id);
    }
}
