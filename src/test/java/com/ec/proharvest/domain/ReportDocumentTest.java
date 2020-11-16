package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDocument.class);
        ReportDocument reportDocument1 = new ReportDocument();
        reportDocument1.setId(1L);
        ReportDocument reportDocument2 = new ReportDocument();
        reportDocument2.setId(reportDocument1.getId());
        assertThat(reportDocument1).isEqualTo(reportDocument2);
        reportDocument2.setId(2L);
        assertThat(reportDocument1).isNotEqualTo(reportDocument2);
        reportDocument1.setId(null);
        assertThat(reportDocument1).isNotEqualTo(reportDocument2);
    }
}
