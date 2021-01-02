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
    private Boolean is128BitKey;    
    private Boolean isCreatingBatchModeBookmarks;
    private Boolean displayMetadataTitle;
    private String metadataCreator;
    private String metadataKeywords;
    private String metadataSubject;
    private String metadataTitle;    
    private String tagLanguage;
    private Boolean isTagged;

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

    public Boolean getIs128BitKey() {
        return is128BitKey;
    }

    public void setIs128BitKey(Boolean is128BitKey) {
        this.is128BitKey = is128BitKey;
    }

    public Boolean getIsCreatingBatchModeBookmarks() {
        return isCreatingBatchModeBookmarks;
    }

    public void setIsCreatingBatchModeBookmarks(Boolean isCreatingBatchModeBookmarks) {
        this.isCreatingBatchModeBookmarks = isCreatingBatchModeBookmarks;
    }

    public Boolean getDisplayMetadataTitle() {
        return displayMetadataTitle;
    }

    public void setDisplayMetadataTitle(Boolean displayMetadataTitle) {
        this.displayMetadataTitle = displayMetadataTitle;
    }

    public String getMetadataCreator() {
        return metadataCreator;
    }

    public void setMetadataCreator(String metadataCreator) {
        this.metadataCreator = metadataCreator;
    }

    public String getMetadataKeywords() {
        return metadataKeywords;
    }

    public void setMetadataKeywords(String metadataKeywords) {
        this.metadataKeywords = metadataKeywords;
    }

    public String getMetadataSubject() {
        return metadataSubject;
    }

    public void setMetadataSubject(String metadataSubject) {
        this.metadataSubject = metadataSubject;
    }

    public String getMetadataTitle() {
        return metadataTitle;
    }

    public void setMetadataTitle(String metadataTitle) {
        this.metadataTitle = metadataTitle;
    }

    public String getTagLanguage() {
        return tagLanguage;
    }

    public void setTagLanguage(String tagLanguage) {
        this.tagLanguage = tagLanguage;
    }

    public Boolean getIsTagged() {
        return isTagged;
    }

    public void setIsTagged(Boolean isTagged) {
        this.isTagged = isTagged;
    }

    @Override
    public String toString() {
        return "PdfReportConfig [allowedPermissionsHint=" + allowedPermissionsHint + ", displayMetadataTitle="
                + displayMetadataTitle + ", encrypted=" + encrypted + ", forceLineBreakPolicy=" + forceLineBreakPolicy
                + ", ignoreHyperlink=" + ignoreHyperlink + ", is128BitKey=" + is128BitKey + ", isCompressed="
                + isCompressed + ", isCreatingBatchModeBookmarks=" + isCreatingBatchModeBookmarks + ", isTagged="
                + isTagged + ", metadataCreator=" + metadataCreator + ", metadataKeywords=" + metadataKeywords
                + ", metadataSubject=" + metadataSubject + ", metadataTitle=" + metadataTitle + ", ownerPassword="
                + ownerPassword + ", sizePageToContent=" + sizePageToContent + ", tagLanguage=" + tagLanguage
                + ", userPassword=" + userPassword + "]";
    }           
}
