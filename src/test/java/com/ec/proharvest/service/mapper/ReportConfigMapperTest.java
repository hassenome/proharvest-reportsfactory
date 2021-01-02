package com.ec.proharvest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportConfigMapperTest {

    private ReportConfigMapper reportConfigMapper;

    @BeforeEach
    public void setUp() {
        reportConfigMapper = new ReportConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportConfigMapper.fromId(null)).isNull();
    }
}
