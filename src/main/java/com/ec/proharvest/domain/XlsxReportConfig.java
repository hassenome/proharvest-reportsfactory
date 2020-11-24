package com.ec.proharvest.domain;

import java.util.Arrays;

public class XlsxReportConfig extends ReportExportConfig {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] sheetNames;

    private Boolean autoFitPageHeight;
    private Boolean cellLocked;
    private Boolean collapseRowSpan;
    private Float columnWidthRatio;


    public String[] getSheetNames() {
        return sheetNames;
    }

    public void setSheetNames(String[] sheetNames) {
        this.sheetNames = sheetNames;
    }

    public Boolean getAutoFitPageHeight() {
        return autoFitPageHeight;
    }

    public void setAutoFitPageHeight(Boolean autoFitPageHeight) {
        this.autoFitPageHeight = autoFitPageHeight;
    }

    public Boolean getCellLocked() {
        return cellLocked;
    }

    public void setCellLocked(Boolean cellLocked) {
        this.cellLocked = cellLocked;
    }

    public Boolean getCollapseRowSpan() {
        return collapseRowSpan;
    }

    public void setCollapseRowSpan(Boolean collapseRowSpan) {
        this.collapseRowSpan = collapseRowSpan;
    }

    public Float getColumnWidthRatio() {
        return columnWidthRatio;
    }

    public void setColumnWidthRatio(Float columnWidthRatio) {
        this.columnWidthRatio = columnWidthRatio;
    }

    @Override
    public String toString() {
        return "XlsxReportConfig [autoFitPageHeight=" + autoFitPageHeight + ", cellLocked=" + cellLocked
                + ", collapseRowSpan=" + collapseRowSpan + ", columnWidthRatio=" + columnWidthRatio + ", sheetNames="
                + Arrays.toString(sheetNames) + "]";
    }    
}
