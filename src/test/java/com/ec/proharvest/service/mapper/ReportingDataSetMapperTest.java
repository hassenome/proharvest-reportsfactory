package com.ec.proharvest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportingDataSetMapperTest {

    private ReportingDataSetMapper reportingDataSetMapper;

    @BeforeEach
    public void setUp() {
        reportingDataSetMapper = new ReportingDataSetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportingDataSetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportingDataSetMapper.fromId(null)).isNull();
    }
}
