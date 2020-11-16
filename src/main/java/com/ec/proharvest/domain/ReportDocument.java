package com.ec.proharvest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ReportDocument.
 */
@Entity
@Table(name = "report_document")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ReportingData> reportingData = new HashSet<>();

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

    public Set<ReportingData> getReportingData() {
        return reportingData;
    }

    public ReportDocument reportingData(Set<ReportingData> reportingData) {
        this.reportingData = reportingData;
        return this;
    }

    public ReportDocument addReportingData(ReportingData reportingData) {
        this.reportingData.add(reportingData);
        reportingData.setReportDocument(this);
        return this;
    }

    public ReportDocument removeReportingData(ReportingData reportingData) {
        this.reportingData.remove(reportingData);
        reportingData.setReportDocument(null);
        return this;
    }

    public void setReportingData(Set<ReportingData> reportingData) {
        this.reportingData = reportingData;
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
