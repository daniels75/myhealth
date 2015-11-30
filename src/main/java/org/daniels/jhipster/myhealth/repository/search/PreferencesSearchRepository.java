package org.daniels.jhipster.myhealth.repository.search;

import org.daniels.jhipster.myhealth.domain.Preferences;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Preferences entity.
 */
public interface PreferencesSearchRepository extends ElasticsearchRepository<Preferences, Long> {
}
