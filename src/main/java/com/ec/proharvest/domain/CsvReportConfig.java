package com.ec.proharvest.domain;

public class CsvReportConfig extends ReportExportConfig {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int offsetX;
    private int offsetY;
    private int startPageIndex;
    private int endPageIndex;

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getStartPageIndex() {
        return startPageIndex;
    }

    public void setStartPageIndex(int startPageIndex) {
        this.startPageIndex = startPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    @Override
    public String toString() {
        return "CsvReportConfig [endPageIndex=" + endPageIndex + ", offsetX=" + offsetX + ", offsetY=" + offsetY
                + ", startPageIndex=" + startPageIndex + "]";
    }
       
}
