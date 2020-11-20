package com.ec.proharvest.domain;

import java.util.Arrays;

public class XlsxReportConfig extends ReportExportConfig {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] sheetNames;

    public String[] getSheetNames() {
        return sheetNames;
    }

    public void setSheetNames(String[] sheetNames) {
        this.sheetNames = sheetNames;
    }

    @Override
    public String toString() {
        return "XlsxReportConfig [sheetNames=" + Arrays.toString(sheetNames) + "]";
    }    
}
