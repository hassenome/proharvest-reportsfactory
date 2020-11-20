package com.ec.proharvest.config.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

import com.ec.proharvest.config.report.error.WrongParametersException;
import com.ec.proharvest.domain.PdfReportConfig;
import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.domain.ReportFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportExporter {
    
    private final Logger log = LoggerFactory.getLogger(ReportFiller.class);

    public void exportToPdf(JasperPrint jasperPrint, OutputStream fileOutputStream, ReportConfig reportConfig) throws JRException {

        if (!(reportConfig.getReportExportConfig() instanceof PdfReportConfig)) { 
            throw new WrongParametersException("Expected field reportExportConfig to be of type" + PdfReportConfig.class + " , got " + reportConfig.getReportExportConfig().getClass() + " instead");
          }
        PdfReportConfig pdfReportConfig = (PdfReportConfig) reportConfig.getReportExportConfig();
        // print report to file
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileOutputStream));

        SimplePdfReportConfiguration simplePdfConfig = new SimplePdfReportConfiguration();
        simplePdfConfig.setSizePageToContent(pdfReportConfig.getSizePageToContent());
        simplePdfConfig.setForceLineBreakPolicy(pdfReportConfig.getForceLineBreakPolicy());

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor(reportConfig.getAuthor());
        exportConfig.setEncrypted(pdfReportConfig.getEncrypted());
        exportConfig.setAllowedPermissionsHint(pdfReportConfig.getAllowedPermissionsHint());

        exporter.setConfiguration(simplePdfConfig);
        exporter.setConfiguration(exportConfig);
        try {
            exporter.exportReport();

        } catch (JRException ex) {
            log.error("Error while exporting PDF report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToXlsx(JasperPrint jasperPrint,  OutputStream fileOutputStream, String sheetName) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileOutputStream));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[] { sheetName });

        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting XLSX report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToCsv(JasperPrint jasperPrint,  OutputStream fileOutputStream) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(fileOutputStream));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting CSV report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToHtml(JasperPrint jasperPrint,  OutputStream fileOutputStream) throws JRException {
        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(fileOutputStream));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting HTML report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

}
