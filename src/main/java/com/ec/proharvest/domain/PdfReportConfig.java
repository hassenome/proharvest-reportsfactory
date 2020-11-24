package com.ec.proharvest.domain;

public class PdfReportConfig extends ReportExportConfig {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String allowedPermissionsHint;

    private Boolean sizePageToContent;

    private Boolean ignoreHyperlink;

    private Boolean forceLineBreakPolicy;

    private Boolean isCompressed;

    private Boolean encrypted;

    private String ownerPassword;

    private String userPassword;

    public String getAllowedPermissionsHint() {
        return allowedPermissionsHint;
    }

    public void setAllowedPermissionsHint(String allowedPermissionsHint) {
        this.allowedPermissionsHint = allowedPermissionsHint;
    }

    public Boolean getSizePageToContent() {
        return sizePageToContent;
    }

    public void setSizePageToContent(Boolean sizePageToContent) {
        this.sizePageToContent = sizePageToContent;
    }

    public Boolean getIgnoreHyperlink() {
        return ignoreHyperlink;
    }

    public void setIgnoreHyperlink(Boolean ignoreHyperlink) {
        this.ignoreHyperlink = ignoreHyperlink;
    }

    public Boolean getForceLineBreakPolicy() {
        return forceLineBreakPolicy;
    }

    public void setForceLineBreakPolicy(Boolean forceLineBreakPolicy) {
        this.forceLineBreakPolicy = forceLineBreakPolicy;
    }

    public Boolean getIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }    
}
