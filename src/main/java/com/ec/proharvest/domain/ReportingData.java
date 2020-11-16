package com.ec.proharvest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * A ReportingData.
 */
@Entity
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
    @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
@Table(name = "reporting_data")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportingdata")
public class ReportingData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_name", nullable = false)
    private String dataName;

    @NotNull
    @Type(type = "jsonb")
    @Column(name = "data_set", nullable = false, columnDefinition = "jsonb")
    private JsonNode dataSet;

    @ManyToOne
    @JsonIgnoreProperties(value = "reportingData", allowSetters = true)
    private ReportDocument reportDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public ReportingData dataName(String dataName) {
        this.dataName = dataName;
        return this;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
    public ReportingData dataSet(JsonNode dataSet) {
        this.dataSet = dataSet;
        return this;
    }

    public JsonNode getDataSet() {
        return dataSet;
    }

    public void setDataSet(JsonNode dataSet) {
        this.dataSet = dataSet;
    }

    public ReportDocument getReportDocument() {
        return reportDocument;
    }

    public ReportingData reportDocument(ReportDocument reportDocument) {
        this.reportDocument = reportDocument;
        return this;
    }

    public void setReportDocument(ReportDocument reportDocument) {
        this.reportDocument = reportDocument;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingData)) {
            return false;
        }
        return id != null && id.equals(((ReportingData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingData{" +
            "id=" + getId() +
            ", dataName='" + getDataName() + "'" +
            ", dataSet='" + getDataSet() + "'" +
            "}";
    }
}
