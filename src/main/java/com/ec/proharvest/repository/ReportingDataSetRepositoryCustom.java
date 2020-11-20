package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportDocument;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface ReportingDataSetRepositoryCustom {
    /*
    * Will fetch the data from the ReportDocument (memory only; no database call) and convert List<ReportingDataSet> to ArrayNode
    */
    ArrayNode getDataSetsAsArrayNode(ReportDocument reportDocument);  
}
