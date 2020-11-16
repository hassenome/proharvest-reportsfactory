package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportingDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportingData.class);
        ReportingData reportingData1 = new ReportingData();
        reportingData1.setId(1L);
        ReportingData reportingData2 = new ReportingData();
        reportingData2.setId(reportingData1.getId());
        assertThat(reportingData1).isEqualTo(reportingData2);
        reportingData2.setId(2L);
        assertThat(reportingData1).isNotEqualTo(reportingData2);
        reportingData1.setId(null);
        assertThat(reportingData1).isNotEqualTo(reportingData2);
    }
}
