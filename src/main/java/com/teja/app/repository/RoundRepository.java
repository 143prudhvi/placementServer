package com.teja.app.repository;

import com.teja.app.domain.Round;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Round entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {}
