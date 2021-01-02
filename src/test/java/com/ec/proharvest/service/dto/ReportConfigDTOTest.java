package com.ec.proharvest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportConfigDTO.class);
        ReportConfigDTO reportConfigDTO1 = new ReportConfigDTO();
        reportConfigDTO1.setId(1L);
        ReportConfigDTO reportConfigDTO2 = new ReportConfigDTO();
        assertThat(reportConfigDTO1).isNotEqualTo(reportConfigDTO2);
        reportConfigDTO2.setId(reportConfigDTO1.getId());
        assertThat(reportConfigDTO1).isEqualTo(reportConfigDTO2);
        reportConfigDTO2.setId(2L);
        assertThat(reportConfigDTO1).isNotEqualTo(reportConfigDTO2);
        reportConfigDTO1.setId(null);
        assertThat(reportConfigDTO1).isNotEqualTo(reportConfigDTO2);
    }
}
