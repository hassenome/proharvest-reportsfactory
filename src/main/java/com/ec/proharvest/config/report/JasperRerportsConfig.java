package com.ec.proharvest.config.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperRerportsConfig {

    // TODO: add document path params
    @Bean
    public ReportFiller reportFiller() {
        return new ReportFiller();
    }

    @Bean
    public ReportExporter reportExporter() {
        return new ReportExporter();
    }

}