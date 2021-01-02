package com.ec.proharvest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportParametersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportParametersDTO.class);
        ReportParametersDTO reportParametersDTO1 = new ReportParametersDTO();
        reportParametersDTO1.setId(1L);
        ReportParametersDTO reportParametersDTO2 = new ReportParametersDTO();
        assertThat(reportParametersDTO1).isNotEqualTo(reportParametersDTO2);
        reportParametersDTO2.setId(reportParametersDTO1.getId());
        assertThat(reportParametersDTO1).isEqualTo(reportParametersDTO2);
        reportParametersDTO2.setId(2L);
        assertThat(reportParametersDTO1).isNotEqualTo(reportParametersDTO2);
        reportParametersDTO1.setId(null);
        assertThat(reportParametersDTO1).isNotEqualTo(reportParametersDTO2);
    }
}
