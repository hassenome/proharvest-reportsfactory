package com.ec.proharvest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportParametersMapperTest {

    private ReportParametersMapper reportParametersMapper;

    @BeforeEach
    public void setUp() {
        reportParametersMapper = new ReportParametersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportParametersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportParametersMapper.fromId(null)).isNull();
    }
}
