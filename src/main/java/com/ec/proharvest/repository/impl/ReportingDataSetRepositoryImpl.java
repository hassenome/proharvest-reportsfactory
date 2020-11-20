package com.ec.proharvest.repository.impl;
import java.util.Set;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.repository.ReportingDataSetRepositoryCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ReportingDataSetRepositoryImpl implements ReportingDataSetRepositoryCustom {

    @Override
    public ArrayNode getDataSetsAsArrayNode(ReportDocument reportDocument) {
        Set<ReportingDataSet> dataSets = reportDocument.getReportingDataSets();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode node = mapper.createArrayNode();
        dataSets.forEach((ReportingDataSet dataSetEntity) -> {
            node.add(dataSetEntity.getDataSet());
        });
        return node;        
    }    
    
}
