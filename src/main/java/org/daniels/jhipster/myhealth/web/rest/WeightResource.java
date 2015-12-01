package org.daniels.jhipster.myhealth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.daniels.jhipster.myhealth.domain.Weight;
import org.daniels.jhipster.myhealth.repository.WeightRepository;
import org.daniels.jhipster.myhealth.repository.search.WeightSearchRepository;
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
 * REST controller for managing Weight.
 */
@RestController
@RequestMapping("/api")
public class WeightResource {

    private final Logger log = LoggerFactory.getLogger(WeightResource.class);

    @Inject
    private WeightRepository weightRepository;

    @Inject
    private WeightSearchRepository weightSearchRepository;

    /**
     * POST  /weights -> Create a new weight.
     */
    @RequestMapping(value = "/weights",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Weight weight) throws URISyntaxException {
        log.debug("REST request to save Weight : {}", weight);
        if (weight.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new weight cannot already have an ID").build();
        }
        weightRepository.save(weight);
        weightSearchRepository.save(weight);
        return ResponseEntity.created(new URI("/api/weights/" + weight.getId())).build();
    }

    /**
     * PUT  /weights -> Updates an existing weight.
     */
    @RequestMapping(value = "/weights",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Weight weight) throws URISyntaxException {
        log.debug("REST request to update Weight : {}", weight);
        if (weight.getId() == null) {
            return create(weight);
        }
        weightRepository.save(weight);
        weightSearchRepository.save(weight);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /weights -> get all the weights.
     */
    @RequestMapping(value = "/weights",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Weight> getAll() {
        log.debug("REST request to get all Weights");
        return weightRepository.findAll();
    }

    /**
     * GET  /weights/:id -> get the "id" weight.
     */
    @RequestMapping(value = "/weights/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Weight> get(@PathVariable Long id) {
        log.debug("REST request to get Weight : {}", id);
        return Optional.ofNullable(weightRepository.findOne(id))
            .map(weight -> new ResponseEntity<>(
                weight,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /weights/:id -> delete the "id" weight.
     */
    @RequestMapping(value = "/weights/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Weight : {}", id);
        weightRepository.delete(id);
        weightSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/weights/:query -> search for the weight corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/weights/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Weight> search(@PathVariable String query) {
        return StreamSupport
            .stream(weightSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
