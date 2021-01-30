package com.ec.proharvest.service.dto;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.node.BaseJsonNode;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportingDataSet} entity.
 */
public class ReportingDataSetDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String dataName;

    @NotNull
    private BaseJsonNode dataSet;


    private Long reportDocumentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public BaseJsonNode getDataSet() {
        return dataSet;
    }

    public void setDataSet(BaseJsonNode dataSet) {
        this.dataSet = dataSet;
    }

    public Long getReportDocumentId() {
        return reportDocumentId;
    }

    public void setReportDocumentId(Long reportDocumentId) {
        this.reportDocumentId = reportDocumentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingDataSetDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportingDataSetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingDataSetDTO{" +
            "id=" + getId() +
            ", dataName='" + getDataName() + "'" +
            ", dataSet='" + getDataSet() + "'" +
            ", reportDocumentId=" + getReportDocumentId() +
            "}";
    }
}
