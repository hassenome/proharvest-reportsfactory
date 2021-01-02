package com.ec.proharvest.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportFile} entity.
 */
public class ReportFileDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private Instant created;

    private Instant modified;

    private String path;


    private Long reportDocumentId;

    private String reportDocumentName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getReportDocumentId() {
        return reportDocumentId;
    }

    public void setReportDocumentId(Long reportDocumentId) {
        this.reportDocumentId = reportDocumentId;
    }

    public String getReportDocumentName() {
        return reportDocumentName;
    }

    public void setReportDocumentName(String reportDocumentName) {
        this.reportDocumentName = reportDocumentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportFileDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportFileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportFileDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", path='" + getPath() + "'" +
            ", reportDocumentId=" + getReportDocumentId() +
            ", reportDocumentName='" + getReportDocumentName() + "'" +
            "}";
    }
}
