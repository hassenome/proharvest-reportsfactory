package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportFile} entity.
 */
public interface ReportFileSearchRepository extends ElasticsearchRepository<ReportFile, Long> {
}
