package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportingDataSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportingDataSets} entity.
 */
public interface ReportingDataSetSearchRepository extends ElasticsearchRepository<ReportingDataSet, Long> {
}
