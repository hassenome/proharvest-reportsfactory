package com.ec.proharvest.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ReportingDataSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReportingDataSearchRepositoryMockConfiguration {

    @MockBean
    private ReportingDataSearchRepository mockReportingDataSearchRepository;

}
