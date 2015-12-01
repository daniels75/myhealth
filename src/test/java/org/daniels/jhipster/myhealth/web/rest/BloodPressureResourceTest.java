package org.daniels.jhipster.myhealth.web.rest;

import org.daniels.jhipster.myhealth.Application;
import org.daniels.jhipster.myhealth.domain.BloodPressure;
import org.daniels.jhipster.myhealth.repository.BloodPressureRepository;
import org.daniels.jhipster.myhealth.repository.search.BloodPressureSearchRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BloodPressureResource REST controller.
 *
 * @see BloodPressureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BloodPressureResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_TIMESTAMP = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TIMESTAMP = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TIMESTAMP_STR = dateTimeFormatter.print(DEFAULT_TIMESTAMP);

    private static final Integer DEFAULT_SYSTOLIC = 0;
    private static final Integer UPDATED_SYSTOLIC = 1;

    private static final Integer DEFAULT_DIASTOLIC = 0;
    private static final Integer UPDATED_DIASTOLIC = 1;

    @Inject
    private BloodPressureRepository bloodPressureRepository;

    @Inject
    private BloodPressureSearchRepository bloodPressureSearchRepository;

    private MockMvc restBloodPressureMockMvc;

    private BloodPressure bloodPressure;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BloodPressureResource bloodPressureResource = new BloodPressureResource();
        ReflectionTestUtils.setField(bloodPressureResource, "bloodPressureRepository", bloodPressureRepository);
        ReflectionTestUtils.setField(bloodPressureResource, "bloodPressureSearchRepository", bloodPressureSearchRepository);
        this.restBloodPressureMockMvc = MockMvcBuilders.standaloneSetup(bloodPressureResource).build();
    }

    @Before
    public void initTest() {
        bloodPressure = new BloodPressure();
        bloodPressure.setTimestamp(DEFAULT_TIMESTAMP);
        bloodPressure.setSystolic(DEFAULT_SYSTOLIC);
        bloodPressure.setDiastolic(DEFAULT_DIASTOLIC);
    }

    @Test
    @Transactional
    public void createBloodPressure() throws Exception {
        int databaseSizeBeforeCreate = bloodPressureRepository.findAll().size();

        // Create the BloodPressure
        restBloodPressureMockMvc.perform(post("/api/bloodPressures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
                .andExpect(status().isCreated());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(databaseSizeBeforeCreate + 1);
        BloodPressure testBloodPressure = bloodPressures.get(bloodPressures.size() - 1);
        assertThat(testBloodPressure.getTimestamp().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testBloodPressure.getSystolic()).isEqualTo(DEFAULT_SYSTOLIC);
        assertThat(testBloodPressure.getDiastolic()).isEqualTo(DEFAULT_DIASTOLIC);
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bloodPressureRepository.findAll()).hasSize(0);
        // set the field null
        bloodPressure.setTimestamp(null);

        // Create the BloodPressure, which fails.
        restBloodPressureMockMvc.perform(post("/api/bloodPressures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSystolicIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bloodPressureRepository.findAll()).hasSize(0);
        // set the field null
        bloodPressure.setSystolic(null);

        // Create the BloodPressure, which fails.
        restBloodPressureMockMvc.perform(post("/api/bloodPressures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(0);
    }

    @Test
    @Transactional
    public void checkDiastolicIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bloodPressureRepository.findAll()).hasSize(0);
        // set the field null
        bloodPressure.setDiastolic(null);

        // Create the BloodPressure, which fails.
        restBloodPressureMockMvc.perform(post("/api/bloodPressures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllBloodPressures() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressures
        restBloodPressureMockMvc.perform(get("/api/bloodPressures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bloodPressure.getId().intValue())))
                .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP_STR)))
                .andExpect(jsonPath("$.[*].systolic").value(hasItem(DEFAULT_SYSTOLIC)))
                .andExpect(jsonPath("$.[*].diastolic").value(hasItem(DEFAULT_DIASTOLIC)));
    }

    @Test
    @Transactional
    public void getBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get the bloodPressure
        restBloodPressureMockMvc.perform(get("/api/bloodPressures/{id}", bloodPressure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bloodPressure.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP_STR))
            .andExpect(jsonPath("$.systolic").value(DEFAULT_SYSTOLIC))
            .andExpect(jsonPath("$.diastolic").value(DEFAULT_DIASTOLIC));
    }

    @Test
    @Transactional
    public void getNonExistingBloodPressure() throws Exception {
        // Get the bloodPressure
        restBloodPressureMockMvc.perform(get("/api/bloodPressures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

		int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();

        // Update the bloodPressure
        bloodPressure.setTimestamp(UPDATED_TIMESTAMP);
        bloodPressure.setSystolic(UPDATED_SYSTOLIC);
        bloodPressure.setDiastolic(UPDATED_DIASTOLIC);
        restBloodPressureMockMvc.perform(put("/api/bloodPressures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
                .andExpect(status().isOk());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(databaseSizeBeforeUpdate);
        BloodPressure testBloodPressure = bloodPressures.get(bloodPressures.size() - 1);
        assertThat(testBloodPressure.getTimestamp().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testBloodPressure.getSystolic()).isEqualTo(UPDATED_SYSTOLIC);
        assertThat(testBloodPressure.getDiastolic()).isEqualTo(UPDATED_DIASTOLIC);
    }

    @Test
    @Transactional
    public void deleteBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

		int databaseSizeBeforeDelete = bloodPressureRepository.findAll().size();

        // Get the bloodPressure
        restBloodPressureMockMvc.perform(delete("/api/bloodPressures/{id}", bloodPressure.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BloodPressure> bloodPressures = bloodPressureRepository.findAll();
        assertThat(bloodPressures).hasSize(databaseSizeBeforeDelete - 1);
    }
}
