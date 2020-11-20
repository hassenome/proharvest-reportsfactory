package com.ec.proharvest.repository.search;

import com.ec.proharvest.domain.ReportLanguage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ReportLanguage} entity.
 */
public interface ReportLanguageSearchRepository extends ElasticsearchRepository<ReportLanguage, Long> {
}
