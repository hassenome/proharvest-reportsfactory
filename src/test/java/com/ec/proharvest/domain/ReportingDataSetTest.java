package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportingDataSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportingDataSet.class);
        ReportingDataSet reportingDataSet1 = new ReportingDataSet();
        reportingDataSet1.setId(1L);
        ReportingDataSet reportingDataSet2 = new ReportingDataSet();
        reportingDataSet2.setId(reportingDataSet1.getId());
        assertThat(reportingDataSet1).isEqualTo(reportingDataSet2);
        reportingDataSet2.setId(2L);
        assertThat(reportingDataSet1).isNotEqualTo(reportingDataSet2);
        reportingDataSet1.setId(null);
        assertThat(reportingDataSet1).isNotEqualTo(reportingDataSet2);
    }
}
