package com.ec.proharvest.batch.processor;

import java.io.IOException;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportFile;
import com.ec.proharvest.domain.enumeration.StatusName;
import com.ec.proharvest.service.OnPremiseReporting;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JRException;

public class ReportGenerationProcessor implements ItemProcessor<ReportDocument, ReportFile> {

    @Autowired
    private OnPremiseReporting reportsManager;
    
    @Override
    public ReportFile process(ReportDocument item) throws Exception {
        System.out.println("processing report:"); 
        System.out.println(item.toString());
        ReportFile processedFile;
        try{
            this.reportsManager.generateReport(item);
        }catch(IOException | JRException exception){
            item.setStatus(StatusName.ERROR);
        }
        return 
    }

    
}
