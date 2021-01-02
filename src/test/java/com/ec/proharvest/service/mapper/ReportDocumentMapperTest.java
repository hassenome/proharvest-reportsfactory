package com.ec.proharvest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportDocumentMapperTest {

    private ReportDocumentMapper reportDocumentMapper;

    @BeforeEach
    public void setUp() {
        reportDocumentMapper = new ReportDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportDocumentMapper.fromId(null)).isNull();
    }
}
