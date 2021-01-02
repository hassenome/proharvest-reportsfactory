package com.ec.proharvest.service.mapper;


import com.ec.proharvest.domain.*;
import com.ec.proharvest.service.dto.ReportingDataSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportingDataSet} and its DTO {@link ReportingDataSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportDocumentMapper.class})
public interface ReportingDataSetMapper extends EntityMapper<ReportingDataSetDTO, ReportingDataSet> {

    @Mapping(source = "reportDocument.id", target = "reportDocumentId")
    ReportingDataSetDTO toDto(ReportingDataSet reportingDataSet);

    @Mapping(source = "reportDocumentId", target = "reportDocument")
    ReportingDataSet toEntity(ReportingDataSetDTO reportingDataSetDTO);

    default ReportingDataSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportingDataSet reportingDataSet = new ReportingDataSet();
        reportingDataSet.setId(id);
        return reportingDataSet;
    }
}
