package com.ec.proharvest.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public abstract class ReportExportConfig implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private String exporterClass;

    public static final String EXPORTER_CLASS_FIELD = "exporterClass";

    public String getExporterClass() {
        return exporterClass;
    }

    public void setExporterClass(String exporterClass) {
        this.exporterClass = Objects.requireNonNull(exporterClass, "exporterClass cannot be null");
    }

    protected ReportExportConfig() {
        this.exporterClass = this.getClass().getSimpleName();
    }

    
    
}
