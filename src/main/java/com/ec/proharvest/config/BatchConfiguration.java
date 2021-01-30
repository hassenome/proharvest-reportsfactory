package com.ec.proharvest.config;

import com.ec.proharvest.batch.listener.JobCompletionNotificationListener;
import com.ec.proharvest.batch.processor.ReportingDataSetProcessor;
import com.ec.proharvest.batch.writer.DomainWriter;
import com.ec.proharvest.domain.ReportingDataSet;
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

    @Bean
    public JsonItemReader<BaseJsonNode> reader() {

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<BaseJsonNode> jsonObjectReader = new JacksonJsonObjectReader<>(BaseJsonNode.class);
        jsonObjectReader.setMapper(objectMapper);

        // TODO: code batch launcher with file listener
        return new JsonItemReaderBuilder<BaseJsonNode>().jsonObjectReader(jsonObjectReader)
                .resource(resourceLoader.getResource("file:C:/workshop/input/input.json")).name("dataSetJsonItemReader")
                .build();
    }

    @Bean
    public ReportingDataSetProcessor processor() {
        return new ReportingDataSetProcessor();
    }

    @Bean
    public DomainWriter<ReportingDataSet> writer() {
        return new DomainWriter<>(reportingDataSetRepository);
    }

    @Bean
    public Step step1(DomainWriter<ReportingDataSet> writer) {
      return stepBuilderFactory.get("step1")
        .<BaseJsonNode, ReportingDataSet> chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .build();
    }    

    @Bean
    public Job importDataSetJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("importDataSetJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
    }

}