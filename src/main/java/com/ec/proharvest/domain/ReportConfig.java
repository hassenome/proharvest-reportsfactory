package com.ec.proharvest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.ec.proharvest.domain.enumeration.FileType;

import com.ec.proharvest.domain.enumeration.StorageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * A ReportConfig.
 */
@Entity
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
    @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
@Table(name = "report_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportconfig")
public class ReportConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type")
    private StorageType storageType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Type(type = "jsonb")
    @Column(name = "report_export_config", columnDefinition = "jsonb")
    private BaseJsonNode reportExportConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public ReportConfig fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public ReportConfig storageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public String getName() {
        return name;
    }

    public ReportConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ReportConfig prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public ReportConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAuthor() {
        return author;
    }

    public ReportConfig author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public ReportConfig title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportConfig)) {
            return false;
        }
        return id != null && id.equals(((ReportConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportConfig [author=" + author + ", fileType=" + fileType + ", id=" + id + ", name=" + name
                + ", prefix=" + prefix + ", reportExportConfig=" + reportExportConfig + ", storageType=" + storageType
                + ", suffix=" + suffix + ", title=" + title + "]";
    }

    // TODO export this to some conversion/transformer class
    public ReportExportConfig getReportExportConfig() {
        String exporterClass = reportExportConfig.path(ReportExportConfig.EXPORTER_CLASS_FIELD).textValue();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (exporterClass.equals(PdfReportConfig.class.getSimpleName())) {
                return mapper.treeToValue(reportExportConfig, PdfReportConfig.class);
            } else {
                if (exporterClass.equals(XlsxReportConfig.class.getSimpleName())) {
                    return mapper.treeToValue(reportExportConfig, XlsxReportConfig.class);
                }
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void setReportExportConfig(ReportExportConfig reportExportConfig) {
        ObjectMapper mapper = new ObjectMapper();
        this.reportExportConfig = mapper.valueToTree(reportExportConfig);
    }
}
