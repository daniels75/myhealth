package org.daniels.jhipster.myhealth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.daniels.jhipster.myhealth.domain.BloodPressure;
import org.daniels.jhipster.myhealth.repository.BloodPressureRepository;
import org.daniels.jhipster.myhealth.repository.search.BloodPressureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BloodPressure.
 */
@RestController
@RequestMapping("/api")
public class BloodPressureResource {

    private final Logger log = LoggerFactory.getLogger(BloodPressureResource.class);

    @Inject
    private BloodPressureRepository bloodPressureRepository;

    @Inject
    private BloodPressureSearchRepository bloodPressureSearchRepository;

    /**
     * POST  /bloodPressures -> Create a new bloodPressure.
     */
    @RequestMapping(value = "/bloodPressures",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody BloodPressure bloodPressure) throws URISyntaxException {
        log.debug("REST request to save BloodPressure : {}", bloodPressure);
        if (bloodPressure.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bloodPressure cannot already have an ID").build();
        }
        bloodPressureRepository.save(bloodPressure);
        bloodPressureSearchRepository.save(bloodPressure);
        return ResponseEntity.created(new URI("/api/bloodPressures/" + bloodPressure.getId())).build();
    }

    /**
     * PUT  /bloodPressures -> Updates an existing bloodPressure.
     */
    @RequestMapping(value = "/bloodPressures",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody BloodPressure bloodPressure) throws URISyntaxException {
        log.debug("REST request to update BloodPressure : {}", bloodPressure);
        if (bloodPressure.getId() == null) {
            return create(bloodPressure);
        }
        bloodPressureRepository.save(bloodPressure);
        bloodPressureSearchRepository.save(bloodPressure);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bloodPressures -> get all the bloodPressures.
     */
    @RequestMapping(value = "/bloodPressures",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BloodPressure> getAll() {
        log.debug("REST request to get all BloodPressures");
        return bloodPressureRepository.findAll();
    }

    /**
     * GET  /bloodPressures/:id -> get the "id" bloodPressure.
     */
    @RequestMapping(value = "/bloodPressures/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BloodPressure> get(@PathVariable Long id) {
        log.debug("REST request to get BloodPressure : {}", id);
        return Optional.ofNullable(bloodPressureRepository.findOne(id))
            .map(bloodPressure -> new ResponseEntity<>(
                bloodPressure,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bloodPressures/:id -> delete the "id" bloodPressure.
     */
    @RequestMapping(value = "/bloodPressures/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BloodPressure : {}", id);
        bloodPressureRepository.delete(id);
        bloodPressureSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/bloodPressures/:query -> search for the bloodPressure corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bloodPressures/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BloodPressure> search(@PathVariable String query) {
        return StreamSupport
            .stream(bloodPressureSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
