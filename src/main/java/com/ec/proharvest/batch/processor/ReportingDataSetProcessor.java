package com.ec.proharvest.batch.processor;

import com.ec.proharvest.domain.ReportingDataSet;
import com.fasterxml.jackson.databind.node.BaseJsonNode;

import org.springframework.batch.item.ItemProcessor;

public class ReportingDataSetProcessor implements ItemProcessor<BaseJsonNode, ReportingDataSet> {

    @Override
    public ReportingDataSet process(BaseJsonNode arg0) throws Exception {
        System.out.println("processing item:"); 
        System.out.println(arg0.toString());
        ReportingDataSet rpDataSet = new ReportingDataSet();
        rpDataSet.setDataName("input");
        rpDataSet.setDataSet(arg0);
        return rpDataSet; 
    }

    
}
