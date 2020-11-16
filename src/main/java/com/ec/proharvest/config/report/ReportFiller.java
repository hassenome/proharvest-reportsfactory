package com.ec.proharvest.config.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportFiller {

    private final Logger log = LoggerFactory.getLogger(ReportFiller.class);    

    public void compileReport(InputStream reportInputStream, OutputStream reportOutputStream) throws JRException {
		try {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportInputStream);
            JRSaver.saveObject(jasperReport, reportOutputStream);
		} catch (JRException ex) {
            throw ex;
        }
    }

    public JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, Collection<?> dataCollection)
            throws JRException {
        JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(dataCollection);
        try {
            return JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);
        } catch (JRException ex) {
            log.error("Unable to fill report " + jasperReport.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }

    public JasperPrint fillReportWithJsonData(JasperReport jasperReport, Map<String, Object> parameters, JsonNode dataSet)
            throws JRException, IOException {
                //convert map dataset to InputStream
                // ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                // ObjectOutputStream out;
                // try {
                //     out = new ObjectOutputStream(byteOut);
                //     out.writeObject(dataSet);
                //     out.flush();
                //     out.close();
                // } catch (IOException e) {
                //     log.error("Unable to stream data source while filling the report" + jasperReport.getName() + ", reason: \n", e.getMessage());
                //     throw e;
                // }
                ObjectMapper objectMapper = new ObjectMapper();
                ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(objectMapper.writeValueAsString(dataSet).getBytes());
                JsonDataSource dataSource = new JsonDataSource(jsonDataStream);   
        try {
            return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException ex) {
            log.error("Unable to fill report " + jasperReport.getName() + ", reason: \n", ex.getMessage());
            throw ex;
        }
    }    

    public JasperReport loadCompiledReport(InputStream reportInputStream) throws JRException {
        try{
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportInputStream);
            return jasperReport;
        } catch (JRException ex) {
            throw ex;
         }
        
    }    
}
