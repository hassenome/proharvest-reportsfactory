package com.ec.proharvest.config.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportFiller {

    private final Logger log = LoggerFactory.getLogger(ReportFiller.class);

    public ReportFiller() {}    

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
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);
            return jasperPrint;
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
