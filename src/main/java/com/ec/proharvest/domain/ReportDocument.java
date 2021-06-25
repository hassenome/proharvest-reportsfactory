package com.ec.proharvest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.ec.proharvest.domain.enumeration.StatusName;

/**
 * A ReportDocument.
 */
@Entity
@Table(name = "report_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportdocument")
public class ReportDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusName status;

    @Column(name = "created")
    private Instant created;

    @Column(name = "modified")
    private Instant modified;

    @Column(name = "file_name")
    private String file_name;

    // TODO: check for missing data when selecting non cached value; perhaps this should not be cached??
    @OneToMany(mappedBy = "reportDocument", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ReportingDataSet> reportingDataSets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    private ReportType reportType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    private ReportConfig reportConfig;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    private ReportParameters reportParameters;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ReportDocument name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusName getStatus() {
        return status;
    }

    public ReportDocument status(StatusName status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusName status) {
        this.status = status;
    }

    public Instant getCreated() {
        return created;
    }

    public ReportDocument created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public ReportDocument modified(Instant modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getFile_name() {
        return file_name;
    }

    public ReportDocument file_name(String file_name) {
        this.file_name = file_name;
        return this;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Set<ReportingDataSet> getReportingDataSets() {
        return reportingDataSets;
    }

    public ReportDocument reportingDataSets(Set<ReportingDataSet> reportingDataSets) {
        this.reportingDataSets = reportingDataSets;
        return this;
    }

    public ReportDocument addReportingDataSets(ReportingDataSet reportingDataSet) {
        this.reportingDataSets.add(reportingDataSet);
        reportingDataSet.setReportDocument(this);
        return this;
    }

    public ReportDocument removeReportingDataSets(ReportingDataSet reportingDataSet) {
        this.reportingDataSets.remove(reportingDataSet);
        reportingDataSet.setReportDocument(null);
        return this;
    }

    public void setReportingDataSets(Set<ReportingDataSet> reportingDataSets) {
        this.reportingDataSets = reportingDataSets;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public ReportDocument reportType(ReportType reportType) {
        this.reportType = reportType;
        return this;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportConfig getReportConfig() {
        return reportConfig;
    }

    public ReportDocument reportConfig(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
        return this;
    }

    public void setReportConfig(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }

    public ReportParameters getReportParameters() {
        return reportParameters;
    }

    public ReportDocument reportParameters(ReportParameters reportParameters) {
        this.reportParameters = reportParameters;
        return this;
    }

    public void setReportParameters(ReportParameters reportParameters) {
        this.reportParameters = reportParameters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportDocument)) {
            return false;
        }
        return id != null && id.equals(((ReportDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDocument{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", file_name='" + getFile_name() + "'" +
            "}";
    }
}
