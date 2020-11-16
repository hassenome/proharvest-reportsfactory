package com.ec.proharvest.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ec.proharvest.config.report.ReportExporter;
import com.ec.proharvest.config.report.ReportFiller;
import com.ec.proharvest.domain.ReportingData;
import com.ec.proharvest.repository.ReportingDataRepository;
import com.ec.proharvest.service.ReportsManager;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OnPremiseReporting implements ReportsManager {
    @Autowired
    ReportFiller reportFiller;

    @Autowired
    ReportExporter reportExporter;

    @Autowired
    ResourceLoader resourceLoader;

    // private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final ReportingDataRepository reportingDataRepository;

    private static final String templatesInputStream = "file:/var/lib/proharvest/reportsfactory/templates/";
    private static final String compiledReportsOutputPath = "/var/lib/proharvest/reportsfactory/templates/";
    private static final String reportsOutputPath = "/var/lib/proharvest/reportsfactory/output/";

    private final Logger log = LoggerFactory.getLogger(ReportFiller.class);

    public OnPremiseReporting(ReportingDataRepository reportingDataRepository) {
        // this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.reportingDataRepository = reportingDataRepository;
    }

    @Override
    public void compileReportTemplate(String reportName) throws JRException, IOException {
        try {
            InputStream reportInputStream = this
                    .loadRessourceFile(templatesInputStream.concat(reportName).concat(".jrxml"));
            OutputStream reportOutputStream = new FileOutputStream(
                    compiledReportsOutputPath.concat(reportName).concat(".jasper"));
            reportFiller.compileReport(reportInputStream, reportOutputStream);
        } catch (JRException ex) {
            log.error("Unable to compile report from ressource " + reportName + ", reason: ", ex.getClass());
            throw ex;
        } catch (IOException e) {
            log.error("Unable to load report template from ressource " + reportName + ", reason: ", e.getMessage());
            throw e;
        }
    }

    @Override
    public void generateReport(String reportName, String reportType) throws JRException, IOException {
        try {
            InputStream compiledReportInputStream = this
                    .loadRessourceFile("file:" + compiledReportsOutputPath.concat(reportName).concat(".jasper"));
            JasperReport compiledReport = reportFiller.loadCompiledReport(compiledReportInputStream);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("userName", "Electronic-chamber");
            // creating the report datasource
            List<ReportingData> sd = this.reportingDataRepository.findAll();
            JsonNode dataSet = sd.get(1).getDataSet();
            JasperPrint jspPrint = reportFiller.fillReportWithJsonData(compiledReport, parameters, dataSet);
            OutputStream reportOutputStream;
            switch (reportType) {
                case "PDF":
                    reportOutputStream = new FileOutputStream(reportsOutputPath.concat(reportName).concat(".pdf"));
                    reportExporter.exportToPdf(jspPrint, reportOutputStream, "e-chamber");
                    break;

                case "HTML":
                    reportOutputStream = new FileOutputStream(reportsOutputPath.concat(reportName).concat(".html"));
                    reportExporter.exportToHtml(jspPrint, reportOutputStream);
                    break;

                default:
                    break;
            }
        } catch (JRException | IOException ex) {
            throw ex;
        }
    }

    private InputStream loadRessourceFile(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return resource.getInputStream();
    }

    @Override
    public List<String> getAvailableTemplates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getCompiledReports() {
        // TODO Auto-generated method stub
        return null;
    }
}
