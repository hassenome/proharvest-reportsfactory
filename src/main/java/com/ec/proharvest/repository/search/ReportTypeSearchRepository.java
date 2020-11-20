package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportType} entity.
 */
public interface ReportTypeSearchRepository extends ElasticsearchRepository<ReportType, Long> {
}
