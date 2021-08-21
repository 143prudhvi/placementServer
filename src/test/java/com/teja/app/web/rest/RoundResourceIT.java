package com.teja.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.teja.app.IntegrationTest;
import com.teja.app.domain.Round;
import com.teja.app.repository.RoundRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RoundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoundResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final String DEFAULT_SKILLS_REQUIRED = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS_REQUIRED = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rounds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoundMockMvc;

    private Round round;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Round createEntity(EntityManager em) {
        Round round = new Round()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .duration(DEFAULT_DURATION)
            .skillsRequired(DEFAULT_SKILLS_REQUIRED)
            .link(DEFAULT_LINK);
        return round;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Round createUpdatedEntity(EntityManager em) {
        Round round = new Round()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .skillsRequired(UPDATED_SKILLS_REQUIRED)
            .link(UPDATED_LINK);
        return round;
    }

    @BeforeEach
    public void initTest() {
        round = createEntity(em);
    }

    @Test
    @Transactional
    void createRound() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();
        // Create the Round
        restRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate + 1);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRound.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRound.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testRound.getSkillsRequired()).isEqualTo(DEFAULT_SKILLS_REQUIRED);
        assertThat(testRound.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    void createRoundWithExistingId() throws Exception {
        // Create the Round with an existing ID
        round.setId(1L);

        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRounds() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get all the roundList
        restRoundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(round.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].skillsRequired").value(hasItem(DEFAULT_SKILLS_REQUIRED)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }

    @Test
    @Transactional
    void getRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get the round
        restRoundMockMvc
            .perform(get(ENTITY_API_URL_ID, round.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(round.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.skillsRequired").value(DEFAULT_SKILLS_REQUIRED))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK));
    }

    @Test
    @Transactional
    void getNonExistingRound() throws Exception {
        // Get the round
        restRoundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round
        Round updatedRound = roundRepository.findById(round.getId()).get();
        // Disconnect from session so that the updates on updatedRound are not directly saved in db
        em.detach(updatedRound);
        updatedRound
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .skillsRequired(UPDATED_SKILLS_REQUIRED)
            .link(UPDATED_LINK);

        restRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRound.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRound))
            )
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRound.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRound.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRound.getSkillsRequired()).isEqualTo(UPDATED_SKILLS_REQUIRED);
        assertThat(testRound.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void putNonExistingRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, round.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(round))
            )
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(round))
            )
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoundWithPatch() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round using partial update
        Round partialUpdatedRound = new Round();
        partialUpdatedRound.setId(round.getId());

        partialUpdatedRound.skillsRequired(UPDATED_SKILLS_REQUIRED).link(UPDATED_LINK);

        restRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRound.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRound))
            )
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRound.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRound.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testRound.getSkillsRequired()).isEqualTo(UPDATED_SKILLS_REQUIRED);
        assertThat(testRound.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void fullUpdateRoundWithPatch() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round using partial update
        Round partialUpdatedRound = new Round();
        partialUpdatedRound.setId(round.getId());

        partialUpdatedRound
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .skillsRequired(UPDATED_SKILLS_REQUIRED)
            .link(UPDATED_LINK);

        restRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRound.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRound))
            )
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRound.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRound.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRound.getSkillsRequired()).isEqualTo(UPDATED_SKILLS_REQUIRED);
        assertThat(testRound.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, round.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(round))
            )
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(round))
            )
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();
        round.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoundMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeDelete = roundRepository.findAll().size();

        // Delete the round
        restRoundMockMvc
            .perform(delete(ENTITY_API_URL_ID, round.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
