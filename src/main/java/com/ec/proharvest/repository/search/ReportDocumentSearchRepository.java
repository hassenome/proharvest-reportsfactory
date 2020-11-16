package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportDocument} entity.
 */
public interface ReportDocumentSearchRepository extends ElasticsearchRepository<ReportDocument, Long> {
}
