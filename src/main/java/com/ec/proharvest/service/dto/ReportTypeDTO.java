package com.ec.proharvest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportType} entity.
 */
public class ReportTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String templateName;

    
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", templateName='" + getTemplateName() + "'" +
            "}";
    }
}
