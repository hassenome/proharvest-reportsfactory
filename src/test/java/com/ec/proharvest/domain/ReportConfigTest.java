package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportConfig.class);
        ReportConfig reportConfig1 = new ReportConfig();
        reportConfig1.setId(1L);
        ReportConfig reportConfig2 = new ReportConfig();
        reportConfig2.setId(reportConfig1.getId());
        assertThat(reportConfig1).isEqualTo(reportConfig2);
        reportConfig2.setId(2L);
        assertThat(reportConfig1).isNotEqualTo(reportConfig2);
        reportConfig1.setId(null);
        assertThat(reportConfig1).isNotEqualTo(reportConfig2);
    }
}
