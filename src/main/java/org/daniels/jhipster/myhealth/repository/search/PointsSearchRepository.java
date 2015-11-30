package org.daniels.jhipster.myhealth.repository.search;

import org.daniels.jhipster.myhealth.domain.Points;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Points entity.
 */
public interface PointsSearchRepository extends ElasticsearchRepository<Points, Long> {
}
