package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportingData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportingData} entity.
 */
public interface ReportingDataSearchRepository extends ElasticsearchRepository<ReportingData, Long> {
}
