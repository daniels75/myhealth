package org.daniels.jhipster.myhealth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.daniels.jhipster.myhealth.domain.Points;
import org.daniels.jhipster.myhealth.repository.PointsRepository;
import org.daniels.jhipster.myhealth.repository.search.PointsSearchRepository;
import org.daniels.jhipster.myhealth.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing Points.
 */
@RestController
@RequestMapping("/api")
public class PointsResource {

    private final Logger log = LoggerFactory.getLogger(PointsResource.class);

    @Inject
    private PointsRepository pointsRepository;

    @Inject
    private PointsSearchRepository pointsSearchRepository;

    /**
     * POST  /pointss -> Create a new points.
     */
    @RequestMapping(value = "/pointss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Points points) throws URISyntaxException {
        log.debug("REST request to save Points : {}", points);
        if (points.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new points cannot already have an ID").build();
        }
        pointsRepository.save(points);
        pointsSearchRepository.save(points);
        return ResponseEntity.created(new URI("/api/pointss/" + points.getId())).build();
    }

    /**
     * PUT  /pointss -> Updates an existing points.
     */
    @RequestMapping(value = "/pointss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Points points) throws URISyntaxException {
        log.debug("REST request to update Points : {}", points);
        if (points.getId() == null) {
            return create(points);
        }
        pointsRepository.save(points);
        pointsSearchRepository.save(points);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /pointss -> get all the pointss.
     */
    @RequestMapping(value = "/pointss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Points>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Points> page = pointsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pointss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pointss/:id -> get the "id" points.
     */
    @RequestMapping(value = "/pointss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Points> get(@PathVariable Long id) {
        log.debug("REST request to get Points : {}", id);
        return Optional.ofNullable(pointsRepository.findOne(id))
            .map(points -> new ResponseEntity<>(
                points,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pointss/:id -> delete the "id" points.
     */
    @RequestMapping(value = "/pointss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Points : {}", id);
        pointsRepository.delete(id);
        pointsSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/pointss/:query -> search for the points corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pointss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Points> search(@PathVariable String query) {
        return StreamSupport
            .stream(pointsSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
