package com.ec.proharvest.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ec.proharvest.batch.listener.JobCompletionNotificationListener;
import com.ec.proharvest.batch.processor.ReportGenerationProcessor;
import com.ec.proharvest.batch.processor.ReportingDataSetProcessor;
import com.ec.proharvest.batch.reader.DomainReader;
import com.ec.proharvest.batch.writer.DomainWriter;
import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportFile;
import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.domain.enumeration.StatusName;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.ReportFileRepository;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BaseJsonNode;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ReportingDataSetRepository reportingDataSetRepository;

    @Autowired
    private ReportDocumentRepository reportDocumentRepository;

    @Autowired
    private ReportFileRepository reportFileRepository;

    // @Bean
    // public Step stepOne() {
    //     return steps.get("stepOne").tasklet(new MyTaskOne()).build();
    // }

    // @Bean
    // public Step stepTwo() {
    //     return steps.get("stepTwo").tasklet(new MyTaskTwo()).build();
    // }

    // @Bean
    // public Job demoJob() {
    //     return jobs.get("demoJob").incrementer(new RunIdIncrementer()).start(stepOne()).next(stepTwo()).build();
    // }

    /*
        ------------ batch readers ------------
    */
    
    
    @Bean
    public JsonItemReader<BaseJsonNode> jsonReader() {

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<BaseJsonNode> jsonObjectReader = new JacksonJsonObjectReader<>(BaseJsonNode.class);
        jsonObjectReader.setMapper(objectMapper);

        // TODO: code batch launcher with file listener
        return new JsonItemReaderBuilder<BaseJsonNode>().jsonObjectReader(jsonObjectReader)
                .resource(resourceLoader.getResource("file:C:/workshop/input/input.json")).name("dataSetJsonItemReader")
                .build();
    }

    @Bean
    public DomainReader<ReportDocument> reportsReader() {
        // fetch parameters
        List<StatusName> arguments = new ArrayList();
        arguments.add(StatusName.NEW);
        // sort policy (required by the reader)
        Map<String, Sort.Direction> sorts = Collections.singletonMap("id", Sort.Direction.ASC);
        DomainReader<ReportDocument> dmReader = new DomainReader<>(reportDocumentRepository, "findByStatusWithData", arguments);
        dmReader.setPageSize(10);
        dmReader.setSort(sorts);
        return dmReader;
    }
    /*
        ------------ batch processors ------------
    */
    @Bean
    public ReportingDataSetProcessor dataSetProcessor() {
        return new ReportingDataSetProcessor();
    }

    @Bean
    public ReportGenerationProcessor reportGenerationProcessor() {
        return new ReportGenerationProcessor();
    }

        /*
        ------------ batch writers ------------
    */

    @Bean
    public DomainWriter<ReportingDataSet> dataSetWriter() {
        return new DomainWriter<>(reportingDataSetRepository);
    }

    @Bean
    public DomainWriter<ReportFile> reportFileWriter() {
        return new DomainWriter<>(reportFileRepository);
    }

    /*
        ------------ batch steps ------------
    */    
    @Bean
    public Step fetchDataSets() {
      return stepBuilderFactory.get("fetchDataSets")
        .<BaseJsonNode, ReportingDataSet> chunk(10)
        .reader(jsonReader())
        .processor(dataSetProcessor())
        .writer(dataSetWriter())
        .build();
    }
    
    @Bean
    public Step generateReports() {
      return stepBuilderFactory.get("generateReports")
        .<ReportDocument, ReportFile> chunk(10)
        .reader(reportsReader())
        .processor(reportGenerationProcessor())
        .writer(reportFileWriter())
        .build();
    } 


    /*
        ------------ batch jobs ------------
    */    
    @Bean
    public Job importDataSetJob(JobCompletionNotificationListener listener, Step fetchDataSets) {
    return jobBuilderFactory.get("importDataSetJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(fetchDataSets)
        .end()
        .build();
    }


    @Bean
    public Job generateReportsJob(JobCompletionNotificationListener listener, Step generateReports) {
    return jobBuilderFactory.get("generateReportsJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(generateReports)
        .end()
        .build();
    }

}