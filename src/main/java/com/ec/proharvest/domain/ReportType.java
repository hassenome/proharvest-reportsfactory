package com.ec.proharvest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ReportType.
 */
@Entity
@Table(name = "report_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reporttype")
public class ReportType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reference")
    private String reference;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JsonIgnoreProperties(value = "reportTypes", allowSetters = true)
    private ReportLanguage reportLanguage;

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

    public ReportType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public ReportType reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCategory() {
        return category;
    }

    public ReportType category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ReportLanguage getReportLanguage() {
        return reportLanguage;
    }

    public ReportType reportLanguage(ReportLanguage reportLanguage) {
        this.reportLanguage = reportLanguage;
        return this;
    }

    public void setReportLanguage(ReportLanguage reportLanguage) {
        this.reportLanguage = reportLanguage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportType)) {
            return false;
        }
        return id != null && id.equals(((ReportType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", reference='" + getReference() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
