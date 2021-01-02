package com.ec.proharvest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ec.proharvest.web.rest.TestUtil;

public class ReportDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDocumentDTO.class);
        ReportDocumentDTO reportDocumentDTO1 = new ReportDocumentDTO();
        reportDocumentDTO1.setId(1L);
        ReportDocumentDTO reportDocumentDTO2 = new ReportDocumentDTO();
        assertThat(reportDocumentDTO1).isNotEqualTo(reportDocumentDTO2);
        reportDocumentDTO2.setId(reportDocumentDTO1.getId());
        assertThat(reportDocumentDTO1).isEqualTo(reportDocumentDTO2);
        reportDocumentDTO2.setId(2L);
        assertThat(reportDocumentDTO1).isNotEqualTo(reportDocumentDTO2);
        reportDocumentDTO1.setId(null);
        assertThat(reportDocumentDTO1).isNotEqualTo(reportDocumentDTO2);
    }
}
