package com.ec.proharvest.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.ec.proharvest.domain.enumeration.StatusName;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportDocument} entity.
 */
public class ReportDocumentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private StatusName status;

    private Instant created;

    private Instant modified;

    private String file_name;


    private Long reportTypeId;

    private String reportTypeName;

    private Long reportConfigId;

    private String reportConfigName;

    private Long reportParametersId;

    private String reportParametersName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusName getStatus() {
        return status;
    }

    public void setStatus(StatusName status) {
        this.status = status;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    public Long getReportConfigId() {
        return reportConfigId;
    }

    public void setReportConfigId(Long reportConfigId) {
        this.reportConfigId = reportConfigId;
    }

    public String getReportConfigName() {
        return reportConfigName;
    }

    public void setReportConfigName(String reportConfigName) {
        this.reportConfigName = reportConfigName;
    }

    public Long getReportParametersId() {
        return reportParametersId;
    }

    public void setReportParametersId(Long reportParametersId) {
        this.reportParametersId = reportParametersId;
    }

    public String getReportParametersName() {
        return reportParametersName;
    }

    public void setReportParametersName(String reportParametersName) {
        this.reportParametersName = reportParametersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportDocumentDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportDocumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDocumentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", file_name='" + getFile_name() + "'" +
            ", reportTypeId=" + getReportTypeId() +
            ", reportTypeName='" + getReportTypeName() + "'" +
            ", reportConfigId=" + getReportConfigId() +
            ", reportConfigName='" + getReportConfigName() + "'" +
            ", reportParametersId=" + getReportParametersId() +
            ", reportParametersName='" + getReportParametersName() + "'" +
            "}";
    }
}
