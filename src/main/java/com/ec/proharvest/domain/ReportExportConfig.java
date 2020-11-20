package com.ec.proharvest.domain;

import java.io.Serializable;

public abstract class ReportExportConfig implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String exporterClass;

    public static final String EXPORTER_CLASS_FIELD = "exporterClass";

    public String getExporterClass() {
        return exporterClass;
    }

    public void setExporterClass(String exporterClass) {
        this.exporterClass = exporterClass;
    }
    
}
