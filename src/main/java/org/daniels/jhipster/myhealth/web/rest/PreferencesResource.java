package org.daniels.jhipster.myhealth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.daniels.jhipster.myhealth.domain.Preferences;
import org.daniels.jhipster.myhealth.repository.PreferencesRepository;
import org.daniels.jhipster.myhealth.repository.search.PreferencesSearchRepository;
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
 * REST controller for managing Preferences.
 */
@RestController
@RequestMapping("/api")
public class PreferencesResource {

    private final Logger log = LoggerFactory.getLogger(PreferencesResource.class);

    @Inject
    private PreferencesRepository preferencesRepository;

    @Inject
    private PreferencesSearchRepository preferencesSearchRepository;

    /**
     * POST  /preferencess -> Create a new preferences.
     */
    @RequestMapping(value = "/preferencess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Preferences preferences) throws URISyntaxException {
        log.debug("REST request to save Preferences : {}", preferences);
        if (preferences.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new preferences cannot already have an ID").build();
        }
        preferencesRepository.save(preferences);
        preferencesSearchRepository.save(preferences);
        return ResponseEntity.created(new URI("/api/preferencess/" + preferences.getId())).build();
    }

    /**
     * PUT  /preferencess -> Updates an existing preferences.
     */
    @RequestMapping(value = "/preferencess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Preferences preferences) throws URISyntaxException {
        log.debug("REST request to update Preferences : {}", preferences);
        if (preferences.getId() == null) {
            return create(preferences);
        }
        preferencesRepository.save(preferences);
        preferencesSearchRepository.save(preferences);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /preferencess -> get all the preferencess.
     */
    @RequestMapping(value = "/preferencess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Preferences> getAll() {
        log.debug("REST request to get all Preferencess");
        return preferencesRepository.findAll();
    }

    /**
     * GET  /preferencess/:id -> get the "id" preferences.
     */
    @RequestMapping(value = "/preferencess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Preferences> get(@PathVariable Long id) {
        log.debug("REST request to get Preferences : {}", id);
        return Optional.ofNullable(preferencesRepository.findOne(id))
            .map(preferences -> new ResponseEntity<>(
                preferences,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /preferencess/:id -> delete the "id" preferences.
     */
    @RequestMapping(value = "/preferencess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Preferences : {}", id);
        preferencesRepository.delete(id);
        preferencesSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/preferencess/:query -> search for the preferences corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/preferencess/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Preferences> search(@PathVariable String query) {
        return StreamSupport
            .stream(preferencesSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
