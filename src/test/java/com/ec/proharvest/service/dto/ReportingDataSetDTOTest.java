package com.ec.proharvest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportingDataSetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportingDataSetDTO.class);
        ReportingDataSetDTO reportingDataSetDTO1 = new ReportingDataSetDTO();
        reportingDataSetDTO1.setId(1L);
        ReportingDataSetDTO reportingDataSetDTO2 = new ReportingDataSetDTO();
        assertThat(reportingDataSetDTO1).isNotEqualTo(reportingDataSetDTO2);
        reportingDataSetDTO2.setId(reportingDataSetDTO1.getId());
        assertThat(reportingDataSetDTO1).isEqualTo(reportingDataSetDTO2);
        reportingDataSetDTO2.setId(2L);
        assertThat(reportingDataSetDTO1).isNotEqualTo(reportingDataSetDTO2);
        reportingDataSetDTO1.setId(null);
        assertThat(reportingDataSetDTO1).isNotEqualTo(reportingDataSetDTO2);
    }
}
