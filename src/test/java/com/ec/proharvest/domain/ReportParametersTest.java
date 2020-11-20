package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportParametersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportParameters.class);
        ReportParameters reportParameters1 = new ReportParameters();
        reportParameters1.setId(1L);
        ReportParameters reportParameters2 = new ReportParameters();
        reportParameters2.setId(reportParameters1.getId());
        assertThat(reportParameters1).isEqualTo(reportParameters2);
        reportParameters2.setId(2L);
        assertThat(reportParameters1).isNotEqualTo(reportParameters2);
        reportParameters1.setId(null);
        assertThat(reportParameters1).isNotEqualTo(reportParameters2);
    }
}
