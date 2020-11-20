package com.ec.proharvest.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportFile;
import com.ec.proharvest.repository.ReportFileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import net.sf.jasperreports.engine.JRException;

public abstract class ReportsManager {
    @Autowired
    ReportFileRepository reportFileRepository;

    @Autowired
    ResourceLoader resourceLoader;
    
    public abstract void compileReportTemplate(ReportDocument reportDocument) throws JRException, IOException;
    public abstract List<String> getCompiledReports();
    public abstract List<String> getAvailableTemplates();
    public abstract ReportFile generateReport(ReportDocument reportDocument) throws JRException, IOException;

    protected ReportFile processReportFileEntity(String fileName, ReportDocument reportDocument) {
        List<ReportFile> reportFiles = this.reportFileRepository.findByFileName(fileName);
        ReportFile reportFile;
        if(reportFiles.isEmpty()) {
            reportFile = new ReportFile();
            reportFile.setCreated(Instant.now());
            reportFile.setFileName(fileName);
        } else {
            reportFile = reportFiles.get(0);
        }                
        reportFile.setReportDocument(reportDocument);
        this.reportFileRepository.save(reportFile);
        return reportFile;
    }

    protected InputStream loadRessourceFile(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return resource.getInputStream();
    }

    protected String createtFileName(ReportDocument reportDocument) {
        String fileName = reportDocument.getReportConfig().getPrefix();
        fileName = fileName.concat(reportDocument.getName());
        fileName = fileName.concat(reportDocument.getReportConfig().getSuffix());
        fileName = fileName.concat('.' + reportDocument.getReportConfig().getFileType().toString().toLowerCase());
        return fileName;
    }




}
