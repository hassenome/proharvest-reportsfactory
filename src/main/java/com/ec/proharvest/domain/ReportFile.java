package com.ec.proharvest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ReportFile.
 */
@Entity
@Table(name = "report_file")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reportfile")
public class ReportFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "modified")
    private Instant modified;

    @Column(name = "tags")
    private String tags;

    @Column(name = "path")
    private String path;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportFiles", allowSetters = true)
    private ReportDocument reportDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public ReportFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getCreated() {
        return created;
    }

    public ReportFile created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public ReportFile modified(Instant modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getTags() {
        return tags;
    }

    public ReportFile tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public ReportFile path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ReportDocument getReportDocument() {
        return reportDocument;
    }

    public ReportFile reportDocument(ReportDocument reportDocument) {
        this.reportDocument = reportDocument;
        return this;
    }

    public void setReportDocument(ReportDocument reportDocument) {
        this.reportDocument = reportDocument;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportFile)) {
            return false;
        }
        return id != null && id.equals(((ReportFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportFile{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", tags='" + getTags() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
