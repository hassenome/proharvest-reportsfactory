package com.ec.proharvest.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.ec.proharvest.config.report.ReportExporter;
import com.ec.proharvest.config.report.ReportFiller;
import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportFile;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.ec.proharvest.service.ReportsManager;
import com.ec.proharvest.service.error.InvalidFileTypeException;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OnPremiseReporting extends ReportsManager {
    @Autowired
    ReportFiller reportFiller;

    @Autowired
    ReportExporter reportExporter;

    @Autowired
    ReportingDataSetRepository dataSetRepository;

    // private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    // private final ReportingDataRepository reportingDataRepository;
    private final ReportDocumentRepository reportDocumentRepository;

    // TODO: export these to properties
    private static final String TEMPLATES_INPUT_STREAM = "file:/var/lib/proharvest/reportsfactory/templates/";
    private static final String COMPILED_REPORTS_OUTPUT_PATH = "/var/lib/proharvest/reportsfactory/templates/";
    private static final String GENERATED_REPORTS_OUTPUT_PATH = "/var/lib/proharvest/reportsfactory/output/";

    private final Logger log = LoggerFactory.getLogger(OnPremiseReporting.class);

    public OnPremiseReporting(ReportDocumentRepository reportDocumentRepository) {
        // this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.reportDocumentRepository = reportDocumentRepository;
    }

    @Override
    public void compileReportTemplate(ReportDocument reportDocument) throws JRException, IOException {
        try {
            InputStream reportInputStream = this
                    .loadRessourceFile(TEMPLATES_INPUT_STREAM.concat(reportDocument.getName()).concat(".jrxml"));
            OutputStream reportOutputStream = new FileOutputStream(
                    COMPILED_REPORTS_OUTPUT_PATH.concat(reportDocument.getName()).concat(".jasper"));
            reportFiller.compileReport(reportInputStream, reportOutputStream);
        } catch (JRException ex) {
            log.error("Unable to compile report from ressource " + reportDocument.getName() + ", reason: ", ex.getClass());
            throw ex;
        } catch (IOException e) {
            log.error("Unable to load report template from ressource " + reportDocument.getName() + ", reason: ", e.getMessage());
            throw e;
        }
    }

    @Override
    public ReportFile generateReport(ReportDocument reportDocument) throws JRException, IOException {
        OutputStream reportOutputStream = null;
        String fileName = this.createtFileName(reportDocument);
        reportOutputStream = new FileOutputStream(GENERATED_REPORTS_OUTPUT_PATH.concat(fileName));
        try {
            InputStream compiledReportInputStream = this
                    .loadRessourceFile("file:" + COMPILED_REPORTS_OUTPUT_PATH.concat(reportDocument.getName()).concat(".jasper"));
            JasperReport compiledReport = reportFiller.loadCompiledReport(compiledReportInputStream);
            // reportDocument.setReportingDataSets(new HashSet<>(dataSetRepository.findByReportDocument(reportDocument)));
            ArrayNode node = dataSetRepository.getDataSetsAsArrayNode(reportDocument);
            JasperPrint jspPrint = reportFiller.fillReportWithJsonData(compiledReport, reportDocument.getReportParameters().getMapParametersSet(), node);
            switch (reportDocument.getReportConfig().getFileType()) {
                case PDF:                    
                    reportExporter.exportToPdf(jspPrint, reportOutputStream, reportDocument.getReportConfig());
                    break;

                case HTML:
                    reportExporter.exportToHtml(jspPrint, reportOutputStream);
                    break;

                default:
                    throw new InvalidFileTypeException("Unhadled file type " + reportDocument.getReportConfig().getFileType());
            }
            return this.processReportFileEntity(fileName, reportDocument);
        } catch (JRException | IOException ex) {
            throw ex;
        } finally{
            reportOutputStream.close();
        }
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
