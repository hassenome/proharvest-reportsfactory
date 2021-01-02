package com.ec.proharvest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportFileDTO.class);
        ReportFileDTO reportFileDTO1 = new ReportFileDTO();
        reportFileDTO1.setId(1L);
        ReportFileDTO reportFileDTO2 = new ReportFileDTO();
        assertThat(reportFileDTO1).isNotEqualTo(reportFileDTO2);
        reportFileDTO2.setId(reportFileDTO1.getId());
        assertThat(reportFileDTO1).isEqualTo(reportFileDTO2);
        reportFileDTO2.setId(2L);
        assertThat(reportFileDTO1).isNotEqualTo(reportFileDTO2);
        reportFileDTO1.setId(null);
        assertThat(reportFileDTO1).isNotEqualTo(reportFileDTO2);
    }
}
