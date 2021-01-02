package com.ec.proharvest.domain;

public class HtmlReportConfig extends ReportExportConfig {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Boolean convertSvgToImage;
    private Boolean embedImage;
    private int startPageIndex;
    private int endPageIndex;
    private Boolean ignoreHyperlink;
    private Boolean ignorePageMargins;
    private int offsetX;
    private int offsetY;
    private Boolean removeEmptySpaceBetweenRows;
    private Boolean useBackgroundImageToAlign;
    private Boolean whitePageBackground;
    private Float zoomRatio;

    public Boolean getConvertSvgToImage() {
        return convertSvgToImage;
    }

    public void setConvertSvgToImage(Boolean convertSvgToImage) {
        this.convertSvgToImage = convertSvgToImage;
    }

    public Boolean getEmbedImage() {
        return embedImage;
    }

    public void setEmbedImage(Boolean embedImage) {
        this.embedImage = embedImage;
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

    public Boolean getIgnoreHyperlink() {
        return ignoreHyperlink;
    }

    public void setIgnoreHyperlink(Boolean ignoreHyperlink) {
        this.ignoreHyperlink = ignoreHyperlink;
    }

    public Boolean getIgnorePageMargins() {
        return ignorePageMargins;
    }

    public void setIgnorePageMargins(Boolean ignorePageMargins) {
        this.ignorePageMargins = ignorePageMargins;
    }

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

    public Boolean getRemoveEmptySpaceBetweenRows() {
        return removeEmptySpaceBetweenRows;
    }

    public void setRemoveEmptySpaceBetweenRows(Boolean removeEmptySpaceBetweenRows) {
        this.removeEmptySpaceBetweenRows = removeEmptySpaceBetweenRows;
    }

    public Boolean getUseBackgroundImageToAlign() {
        return useBackgroundImageToAlign;
    }

    public void setUseBackgroundImageToAlign(Boolean useBackgroundImageToAlign) {
        this.useBackgroundImageToAlign = useBackgroundImageToAlign;
    }

    public Boolean getWhitePageBackground() {
        return whitePageBackground;
    }

    public void setWhitePageBackground(Boolean whitePageBackground) {
        this.whitePageBackground = whitePageBackground;
    }

    public Float getZoomRatio() {
        return zoomRatio;
    }

    public void setZoomRatio(Float zoomRatio) {
        this.zoomRatio = zoomRatio;
    }

    @Override
    public String toString() {
        return "HtmlReportConfig [convertSvgToImage=" + convertSvgToImage + ", embedImage=" + embedImage
                + ", endPageIndex=" + endPageIndex + ", ignoreHyperlink=" + ignoreHyperlink + ", ignorePageMargins="
                + ignorePageMargins + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", removeEmptySpaceBetweenRows="
                + removeEmptySpaceBetweenRows + ", startPageIndex=" + startPageIndex + ", useBackgroundImageToAlign="
                + useBackgroundImageToAlign + ", whitePageBackground=" + whitePageBackground + ", zoomRatio="
                + zoomRatio + "]";
    }        
}
