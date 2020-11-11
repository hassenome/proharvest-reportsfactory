package com.ec.proharvest.service;

import java.util.List;

public interface ReportsManager {
    void compileReportTemplate(String reportName) throws Exception;
    public List<String> getCompiledReports();
    public List<String> getAvailableTemplates();
    public void generateReport(String reportName, String reportType) throws Exception;    
}
