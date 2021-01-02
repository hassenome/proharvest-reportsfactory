package com.ec.proharvest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportFileMapperTest {

    private ReportFileMapper reportFileMapper;

    @BeforeEach
    public void setUp() {
        reportFileMapper = new ReportFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportFileMapper.fromId(null)).isNull();
    }
}
