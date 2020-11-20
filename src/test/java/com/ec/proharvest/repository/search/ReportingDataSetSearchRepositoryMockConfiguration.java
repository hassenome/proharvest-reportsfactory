package com.ec.proharvest.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ReportingDataSetSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReportingDataSetSearchRepositoryMockConfiguration {

    @MockBean
    private ReportingDataSetSearchRepository mockReportingDataSetSearchRepository;

}
