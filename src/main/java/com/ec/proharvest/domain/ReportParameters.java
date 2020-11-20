package com.ec.proharvest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import java.io.Serializable;
import java.util.Map;

/**
 * A ReportParameters.
 */
@Entity
@Table(name = "report_parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportparameters")
public class ReportParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Type(type = "jsonb")
    @Column(name = "parameters_set", nullable = false, columnDefinition = "jsonb")
    private BaseJsonNode parametersSet;

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

    public ReportParameters name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportParameters)) {
            return false;
        }
        return id != null && id.equals(((ReportParameters) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportParameters [id=" + id + ", name=" + name + ", parametersSet=" + parametersSet + "]";
    }

    public BaseJsonNode getParametersSet() {
        return parametersSet;
    }

    public void setParametersSet(BaseJsonNode parametersSet) {
        this.parametersSet = parametersSet;
    }

    public ReportParameters parametersSet(BaseJsonNode parametersSet) {
        this.parametersSet = parametersSet;
        return this;
    }
    
    // TODO: check wether an external mapper/converter is required for this mapping
    public Map<String, Object> getMapParametersSet() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(this.parametersSet, new TypeReference<Map<String, Object>>(){});
    }
}
