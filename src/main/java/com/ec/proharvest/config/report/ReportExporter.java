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
import com.ec.proharvest.domain.CsvReportConfig;
import com.ec.proharvest.domain.HtmlReportConfig;
import com.ec.proharvest.domain.PdfReportConfig;
import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.domain.ReportExportConfig;
import com.ec.proharvest.domain.XlsxReportConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportExporter {
    
    private final Logger log = LoggerFactory.getLogger(ReportFiller.class);

    public void exportToPdf(JasperPrint jasperPrint, OutputStream fileOutputStream, ReportConfig reportConfig) throws JRException {

        this.checkExporterClass(reportConfig, PdfReportConfig.class);
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
        exportConfig.set128BitKey(pdfReportConfig.getIs128BitKey());
        exportConfig.setCompressed(pdfReportConfig.getIsCompressed());
        exportConfig.setCreatingBatchModeBookmarks(pdfReportConfig.getIsCreatingBatchModeBookmarks());
        exportConfig.setDisplayMetadataTitle(pdfReportConfig.getDisplayMetadataTitle());
        exportConfig.setMetadataCreator(pdfReportConfig.getMetadataCreator());
        exportConfig.setMetadataKeywords(pdfReportConfig.getMetadataKeywords());
        exportConfig.setMetadataSubject(pdfReportConfig.getMetadataSubject());
        exportConfig.setMetadataTitle(pdfReportConfig.getMetadataTitle());
        exportConfig.setOwnerPassword(pdfReportConfig.getOwnerPassword());
        // May be usefull to use setPdfJavaScript for advanced PDF functionnalities
        exportConfig.setTagLanguage(pdfReportConfig.getTagLanguage());
        exportConfig.setTagged(pdfReportConfig.getIsTagged());
        exportConfig.setUserPassword(pdfReportConfig.getUserPassword());

        exporter.setConfiguration(simplePdfConfig);
        exporter.setConfiguration(exportConfig);
        try {
            exporter.exportReport();

        } catch (JRException ex) {
            log.error("Error while exporting PDF report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToXlsx(JasperPrint jasperPrint, OutputStream fileOutputStream, ReportConfig reportConfig) throws JRException {
        this.checkExporterClass(reportConfig, XlsxReportConfig.class);
        XlsxReportConfig xlsxReportConfig = (XlsxReportConfig) reportConfig.getReportExportConfig();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileOutputStream));

        SimpleXlsxReportConfiguration simpleXlsxConfig = new SimpleXlsxReportConfiguration();
        simpleXlsxConfig.setSheetNames(xlsxReportConfig.getSheetNames());
        simpleXlsxConfig.setAutoFitPageHeight(xlsxReportConfig.getAutoFitPageHeight());
        simpleXlsxConfig.setCellLocked(xlsxReportConfig.getCellLocked());
        simpleXlsxConfig.setCollapseRowSpan(xlsxReportConfig.getCollapseRowSpan());
        simpleXlsxConfig.setColumnWidthRatio(xlsxReportConfig.getColumnWidthRatio());
        exporter.setConfiguration(simpleXlsxConfig);
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting XLSX report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToCsv(JasperPrint jasperPrint, OutputStream fileOutputStream, ReportConfig reportConfig) throws JRException {        
        this.checkExporterClass(reportConfig, CsvReportConfig.class);
        CsvReportConfig csvReportConfig = (CsvReportConfig) reportConfig.getReportExportConfig();

        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(fileOutputStream));

        SimpleCsvReportConfiguration simpleCsvConfig = new SimpleCsvReportConfiguration();
        simpleCsvConfig.setOffsetX(csvReportConfig.getOffsetX());
        simpleCsvConfig.setOffsetY(csvReportConfig.getOffsetY());
        simpleCsvConfig.setStartPageIndex(csvReportConfig.getStartPageIndex());
        simpleCsvConfig.setEndPageIndex(csvReportConfig.getEndPageIndex());
        exporter.setConfiguration(simpleCsvConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting CSV report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public void exportToHtml(JasperPrint jasperPrint,  OutputStream fileOutputStream, ReportConfig reportConfig) throws JRException {
        this.checkExporterClass(reportConfig, HtmlReportConfig.class);
        HtmlReportConfig htmlReportConfig = (HtmlReportConfig) reportConfig.getReportExportConfig();

        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(fileOutputStream));
        
        SimpleHtmlReportConfiguration simpleHtmlConfig = new SimpleHtmlReportConfiguration();
        simpleHtmlConfig.setConvertSvgToImage(htmlReportConfig.getConvertSvgToImage());
        simpleHtmlConfig.setEmbedImage(htmlReportConfig.getEmbedImage());
        simpleHtmlConfig.setStartPageIndex(htmlReportConfig.getStartPageIndex());
        simpleHtmlConfig.setEndPageIndex(htmlReportConfig.getEndPageIndex());
        simpleHtmlConfig.setIgnoreHyperlink(htmlReportConfig.getIgnoreHyperlink());
        simpleHtmlConfig.setIgnorePageMargins(htmlReportConfig.getIgnorePageMargins());
        simpleHtmlConfig.setOffsetX(htmlReportConfig.getOffsetX());
        simpleHtmlConfig.setOffsetY(htmlReportConfig.getOffsetY());
        simpleHtmlConfig.setRemoveEmptySpaceBetweenRows(htmlReportConfig.getRemoveEmptySpaceBetweenRows());
        simpleHtmlConfig.setUseBackgroundImageToAlign(htmlReportConfig.getUseBackgroundImageToAlign());
        simpleHtmlConfig.setWhitePageBackground(htmlReportConfig.getWhitePageBackground());
        simpleHtmlConfig.setZoomRatio(htmlReportConfig.getZoomRatio());        
        exporter.setConfiguration(simpleHtmlConfig);
        
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.error("Error while exporting HTML report " + jasperPrint.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    private void checkExporterClass(ReportConfig reportConfig, Class<? extends ReportExportConfig> c) {
        if (!(reportConfig.getReportExportConfig().getClass().isInstance(c))) { 
            throw new WrongParametersException("Expected field reportExportConfig to be of type" + PdfReportConfig.class + " , got " + reportConfig.getReportExportConfig().getClass() + " instead");
          }
    }    

}
