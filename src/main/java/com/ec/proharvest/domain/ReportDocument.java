package com.ec.proharvest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ReportDocument.
 */
@Entity
@Table(name = "report_document")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportdocument")
public class ReportDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "reportDocument")
    private Set<ReportingDataSet> reportingDataSets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private ReportType reportType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private ReportConfig reportConfig;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportDocuments", allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    public Set<ReportingDataSet> getReportingDataSets() {
        return reportingDataSets;
    }

    public ReportDocument reportingDataSets(Set<ReportingDataSet> reportingDataSets) {
        this.reportingDataSets = reportingDataSets;
        return this;
    }

    public ReportDocument addReportingDataSets(ReportingDataSet reportingDataSets) {
        this.reportingDataSets.add(reportingDataSets);
        reportingDataSets.setReportDocument(this);
        return this;
    }

    public ReportDocument removeReportingDataSets(ReportingDataSet reportingDataSets) {
        this.reportingDataSets.remove(reportingDataSets);
        reportingDataSets.setReportDocument(null);
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
            "}";
    }

}
