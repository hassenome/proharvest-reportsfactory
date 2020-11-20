package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportParameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportParameters} entity.
 */
public interface ReportParametersSearchRepository extends ElasticsearchRepository<ReportParameters, Long> {
}
