package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.PdfReportConfig;
import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportParameters;
import com.ec.proharvest.domain.ReportType;
import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.domain.enumeration.FileType;
import com.ec.proharvest.domain.enumeration.StorageType;
import com.ec.proharvest.repository.ReportConfigRepository;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.ReportParametersRepository;
import com.ec.proharvest.repository.ReportTypeRepository;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.ec.proharvest.service.OnPremiseReporting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.google.common.net.HttpHeaders;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;

import io.github.jhipster.web.util.PaginationUtil;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
public class ReportResource {
    @Autowired
    ReportDocumentRepository reportDocumentRepository;

    @Autowired
    ReportConfigRepository reportConfigRepository;

    @Autowired
    ReportTypeRepository reportTypeRepository;

    @Autowired
    ReportParametersRepository reportParametersRepository;

    @Autowired
    ReportingDataSetRepository reportingDataSetRepository;

    // TODO: for test only, remove these, then later remove the entier class
    @Autowired
    JobLauncher jobLauncher;
     
    @Autowired
    @Qualifier("importDataSetJob")
    Job importDataSetJob;

    @Autowired
    @Qualifier("generateReportsJob")
    Job generateReportsJob;

    private final OnPremiseReporting reportsManager;

    public ReportResource(OnPremiseReporting reportsManager) {
        this.reportsManager = reportsManager;
    }

    @GetMapping("/reports/compile")
    public ResponseEntity<String> compileReport() {
        try {
            // this.createData();
            ReportDocument rptDoc = this.reportDocumentRepository.findByName("EC").get(0);
            this.reportsManager.compileReportTemplate(rptDoc);
            // HttpHeaders headers =
            // PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
            // "ok");
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/reports/generate")
    public ResponseEntity<String> generateReport() {
        try {
            ReportDocument rptDoc = this.reportDocumentRepository.findByNameWithData("EC").get(0);
            this.reportsManager.generateReport(rptDoc);
            // HttpHeaders headers =
            // PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
            // "ok");
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/reports/run")
    public ResponseEntity<String> runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
            jobLauncher.run(importDataSetJob, params);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/reports/rungen")
    public ResponseEntity<String> runGenJob() {
        try {
            JobParameters params = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
            jobLauncher.run(generateReportsJob, params);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    private void createData() {
        PdfReportConfig pdfConfig = new PdfReportConfig();
        pdfConfig.setAllowedPermissionsHint("PRINTING");
        pdfConfig.setEncrypted(true);
        pdfConfig.setForceLineBreakPolicy(false);
        pdfConfig.setIgnoreHyperlink(false);
        pdfConfig.setIsCompressed(false);
        pdfConfig.setSizePageToContent(true);
        pdfConfig.setUserPassword("PRH");
        pdfConfig.setOwnerPassword("ECH");

        ReportConfig reportConf = new ReportConfig();
        reportConf.setAuthor("E-CHAMBER");
        reportConf.setFileType(FileType.PDF);
        reportConf.setName("EC-prototype-pdf");
        reportConf.setPrefix("ec_");
        reportConf.setReportExportConfig(pdfConfig);
        reportConf.setStorageType(StorageType.OP);
        reportConf.setSuffix("_testbench");

        this.reportConfigRepository.save(reportConf);

        ReportType reportType = new ReportType();
        reportType.setName("prototype");
        reportType.setTemplateName("template");

        this.reportTypeRepository.save(reportType);

        ReportParameters reportparams = new ReportParameters();
        reportparams.setName("customerType");
        ObjectMapper mapper = new ObjectMapper();
        String reportParam = "{\"userName\": \"E-CHAMBER\"}";
        JsonNode parametersSet;
        try {
            parametersSet = mapper.readTree(reportParam);
            reportparams.setParametersSet((BaseJsonNode) parametersSet);
            this.reportParametersRepository.save(reportparams);
        } catch (JsonProcessingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ReportDocument testReportDocument = new ReportDocument();
        testReportDocument.setName("EC");
        testReportDocument.setReportConfig(reportConf);
        testReportDocument.setReportParameters(reportparams);
        testReportDocument.setReportType(reportType);

        this.reportDocumentRepository.save(testReportDocument);

        // setting reporting data
        ReportingDataSet reportData1 = new ReportingDataSet();
        ReportingDataSet reportData2 = new ReportingDataSet();
        reportData1.setDataName("John");
        reportData2.setDataName("Lee");
        mapper = new ObjectMapper();
        String dataSet1 = "{\"age\": 31,\"city\": \"New York\",\"name\": \"John\"}";
        String dataSet2 = "{\"age\": 44,\"city\": \"Old York\",\"name\": \"Lee\"}";
        JsonNode dataSetNode1;
        JsonNode dataSetNode2;
        try {
            dataSetNode1 = mapper.readTree(dataSet1);
            dataSetNode2 = mapper.readTree(dataSet2);
            reportData1.setDataSet((BaseJsonNode) dataSetNode1);
            reportData2.setDataSet((BaseJsonNode) dataSetNode2);
            reportData1.setReportDocument(testReportDocument);
            reportData2.setReportDocument(testReportDocument);

            this.reportingDataSetRepository.save(reportData1);
            this.reportingDataSetRepository.save(reportData2);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
}
    
}
