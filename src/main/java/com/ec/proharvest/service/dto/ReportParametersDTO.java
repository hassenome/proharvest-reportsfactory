package com.ec.proharvest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportParameters} entity.
 */
public class ReportParametersDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String values;

    
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

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportParametersDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportParametersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportParametersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", values='" + getValues() + "'" +
            "}";
    }
}
