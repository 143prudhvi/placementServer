package com.teja.app.service;

import com.teja.app.domain.Round;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Round}.
 */
public interface RoundService {
    /**
     * Save a round.
     *
     * @param round the entity to save.
     * @return the persisted entity.
     */
    Round save(Round round);

    /**
     * Partially updates a round.
     *
     * @param round the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Round> partialUpdate(Round round);

    /**
     * Get all the rounds.
     *
     * @return the list of entities.
     */
    List<Round> findAll();

    /**
     * Get the "id" round.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Round> findOne(Long id);

    /**
     * Delete the "id" round.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
