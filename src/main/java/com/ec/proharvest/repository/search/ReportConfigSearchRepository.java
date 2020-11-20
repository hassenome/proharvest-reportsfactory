package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportConfig} entity.
 */
public interface ReportConfigSearchRepository extends ElasticsearchRepository<ReportConfig, Long> {
}
