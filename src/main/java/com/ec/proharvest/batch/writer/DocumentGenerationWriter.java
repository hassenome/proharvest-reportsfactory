package com.ec.proharvest.batch.writer;

import java.util.ArrayList;
import java.util.List;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportFile;

import org.springframework.batch.item.ItemWriter;

public class DocumentGenerationWriter<T> implements ItemWriter<T> {

    private DomainWriter<ReportFile> fDmWriter;
    private DomainWriter<ReportDocument> rDmWriter;

    
    public DocumentGenerationWriter(DomainWriter<ReportFile> fDmWriter, DomainWriter<ReportDocument> rDmWriter) {
        this.fDmWriter = fDmWriter;
        this.rDmWriter = rDmWriter;
    }


    @Override
    public void write(List<? extends ReportFile> items) throws Exception {
        this.fDmWriter.write(items);
        this.rDmWriter.write(this.extractReportDocuments(items));
        
    }

    private List<? extends ReportDocument> extractReportDocuments(List<? extends ReportFile> items){
        List<ReportDocument> rpDocuments = new ArrayList<>();
        items.forEach(reportFile -> rpDocuments.add(reportFile.getReportDocument()));
        return rpDocuments;
    }

}