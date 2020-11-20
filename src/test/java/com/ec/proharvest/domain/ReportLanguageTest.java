package com.ec.proharvest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportLanguageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportLanguage.class);
        ReportLanguage reportLanguage1 = new ReportLanguage();
        reportLanguage1.setId(1L);
        ReportLanguage reportLanguage2 = new ReportLanguage();
        reportLanguage2.setId(reportLanguage1.getId());
        assertThat(reportLanguage1).isEqualTo(reportLanguage2);
        reportLanguage2.setId(2L);
        assertThat(reportLanguage1).isNotEqualTo(reportLanguage2);
        reportLanguage1.setId(null);
        assertThat(reportLanguage1).isNotEqualTo(reportLanguage2);
    }
}
