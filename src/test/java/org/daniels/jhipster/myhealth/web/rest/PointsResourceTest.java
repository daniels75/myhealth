package org.daniels.jhipster.myhealth.web.rest;

import org.daniels.jhipster.myhealth.Application;
import org.daniels.jhipster.myhealth.domain.Points;
import org.daniels.jhipster.myhealth.repository.PointsRepository;
import org.daniels.jhipster.myhealth.repository.search.PointsSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PointsResource REST controller.
 *
 * @see PointsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PointsResourceTest {


    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();

    private static final Integer DEFAULT_EXERCISE = 0;
    private static final Integer UPDATED_EXERCISE = 1;

    private static final Integer DEFAULT_MEALS = 0;
    private static final Integer UPDATED_MEALS = 1;

    private static final Integer DEFAULT_ALCOHOL = 0;
    private static final Integer UPDATED_ALCOHOL = 1;
    private static final String DEFAULT_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_NOTES = "UPDATED_TEXT";

    @Inject
    private PointsRepository pointsRepository;

    @Inject
    private PointsSearchRepository pointsSearchRepository;

    private MockMvc restPointsMockMvc;

    private Points points;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PointsResource pointsResource = new PointsResource();
        ReflectionTestUtils.setField(pointsResource, "pointsRepository", pointsRepository);
        ReflectionTestUtils.setField(pointsResource, "pointsSearchRepository", pointsSearchRepository);
        this.restPointsMockMvc = MockMvcBuilders.standaloneSetup(pointsResource).build();
    }

    @Before
    public void initTest() {
        points = new Points();
        points.setDate(DEFAULT_DATE);
        points.setExercise(DEFAULT_EXERCISE);
        points.setMeals(DEFAULT_MEALS);
        points.setAlcohol(DEFAULT_ALCOHOL);
        points.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createPoints() throws Exception {
        int databaseSizeBeforeCreate = pointsRepository.findAll().size();

        // Create the Points
        restPointsMockMvc.perform(post("/api/pointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(points)))
                .andExpect(status().isCreated());

        // Validate the Points in the database
        List<Points> pointss = pointsRepository.findAll();
        assertThat(pointss).hasSize(databaseSizeBeforeCreate + 1);
        Points testPoints = pointss.get(pointss.size() - 1);
        assertThat(testPoints.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPoints.getExercise()).isEqualTo(DEFAULT_EXERCISE);
        assertThat(testPoints.getMeals()).isEqualTo(DEFAULT_MEALS);
        assertThat(testPoints.getAlcohol()).isEqualTo(DEFAULT_ALCOHOL);
        assertThat(testPoints.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(pointsRepository.findAll()).hasSize(0);
        // set the field null
        points.setDate(null);

        // Create the Points, which fails.
        restPointsMockMvc.perform(post("/api/pointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(points)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Points> pointss = pointsRepository.findAll();
        assertThat(pointss).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPointss() throws Exception {
        // Initialize the database
        pointsRepository.saveAndFlush(points);

        // Get all the pointss
        restPointsMockMvc.perform(get("/api/pointss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(points.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].exercise").value(hasItem(DEFAULT_EXERCISE)))
                .andExpect(jsonPath("$.[*].meals").value(hasItem(DEFAULT_MEALS)))
                .andExpect(jsonPath("$.[*].alcohol").value(hasItem(DEFAULT_ALCOHOL)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getPoints() throws Exception {
        // Initialize the database
        pointsRepository.saveAndFlush(points);

        // Get the points
        restPointsMockMvc.perform(get("/api/pointss/{id}", points.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(points.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.exercise").value(DEFAULT_EXERCISE))
            .andExpect(jsonPath("$.meals").value(DEFAULT_MEALS))
            .andExpect(jsonPath("$.alcohol").value(DEFAULT_ALCOHOL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPoints() throws Exception {
        // Get the points
        restPointsMockMvc.perform(get("/api/pointss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoints() throws Exception {
        // Initialize the database
        pointsRepository.saveAndFlush(points);

		int databaseSizeBeforeUpdate = pointsRepository.findAll().size();

        // Update the points
        points.setDate(UPDATED_DATE);
        points.setExercise(UPDATED_EXERCISE);
        points.setMeals(UPDATED_MEALS);
        points.setAlcohol(UPDATED_ALCOHOL);
        points.setNotes(UPDATED_NOTES);
        restPointsMockMvc.perform(put("/api/pointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(points)))
                .andExpect(status().isOk());

        // Validate the Points in the database
        List<Points> pointss = pointsRepository.findAll();
        assertThat(pointss).hasSize(databaseSizeBeforeUpdate);
        Points testPoints = pointss.get(pointss.size() - 1);
        assertThat(testPoints.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPoints.getExercise()).isEqualTo(UPDATED_EXERCISE);
        assertThat(testPoints.getMeals()).isEqualTo(UPDATED_MEALS);
        assertThat(testPoints.getAlcohol()).isEqualTo(UPDATED_ALCOHOL);
        assertThat(testPoints.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deletePoints() throws Exception {
        // Initialize the database
        pointsRepository.saveAndFlush(points);

		int databaseSizeBeforeDelete = pointsRepository.findAll().size();

        // Get the points
        restPointsMockMvc.perform(delete("/api/pointss/{id}", points.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Points> pointss = pointsRepository.findAll();
        assertThat(pointss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
