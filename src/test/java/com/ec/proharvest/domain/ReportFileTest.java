package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportFile.class);
        ReportFile reportFile1 = new ReportFile();
        reportFile1.setId(1L);
        ReportFile reportFile2 = new ReportFile();
        reportFile2.setId(reportFile1.getId());
        assertThat(reportFile1).isEqualTo(reportFile2);
        reportFile2.setId(2L);
        assertThat(reportFile1).isNotEqualTo(reportFile2);
        reportFile1.setId(null);
        assertThat(reportFile1).isNotEqualTo(reportFile2);
    }
}
