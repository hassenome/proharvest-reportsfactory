package com.ec.proharvest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.ec.proharvest.domain.enumeration.FileType;
import com.ec.proharvest.domain.enumeration.StorageType;

/**
 * A DTO for the {@link com.ec.proharvest.domain.ReportConfig} entity.
 */
public class ReportConfigDTO implements Serializable {
    
    private Long id;

    @NotNull
    private FileType fileType;

    private StorageType storageType;

    @NotNull
    private String name;

    private String prefix;

    private String suffix;

    private String author;

    private String allowedPermissionsHint;

    private Boolean sizePageToContent;

    private Boolean ignoreHyperlink;

    private Boolean forceLineBreakPolicy;

    private Boolean isCompressed;

    private Boolean encrypted;

    private String ownerPassword;

    private String userPassword;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAllowedPermissionsHint() {
        return allowedPermissionsHint;
    }

    public void setAllowedPermissionsHint(String allowedPermissionsHint) {
        this.allowedPermissionsHint = allowedPermissionsHint;
    }

    public Boolean isSizePageToContent() {
        return sizePageToContent;
    }

    public void setSizePageToContent(Boolean sizePageToContent) {
        this.sizePageToContent = sizePageToContent;
    }

    public Boolean isIgnoreHyperlink() {
        return ignoreHyperlink;
    }

    public void setIgnoreHyperlink(Boolean ignoreHyperlink) {
        this.ignoreHyperlink = ignoreHyperlink;
    }

    public Boolean isForceLineBreakPolicy() {
        return forceLineBreakPolicy;
    }

    public void setForceLineBreakPolicy(Boolean forceLineBreakPolicy) {
        this.forceLineBreakPolicy = forceLineBreakPolicy;
    }

    public Boolean isIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
    }

    public Boolean isEncrypted() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportConfigDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportConfigDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportConfigDTO{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", storageType='" + getStorageType() + "'" +
            ", name='" + getName() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", author='" + getAuthor() + "'" +
            ", allowedPermissionsHint='" + getAllowedPermissionsHint() + "'" +
            ", sizePageToContent='" + isSizePageToContent() + "'" +
            ", ignoreHyperlink='" + isIgnoreHyperlink() + "'" +
            ", forceLineBreakPolicy='" + isForceLineBreakPolicy() + "'" +
            ", isCompressed='" + isIsCompressed() + "'" +
            ", encrypted='" + isEncrypted() + "'" +
            ", ownerPassword='" + getOwnerPassword() + "'" +
            ", userPassword='" + getUserPassword() + "'" +
            "}";
    }
}
