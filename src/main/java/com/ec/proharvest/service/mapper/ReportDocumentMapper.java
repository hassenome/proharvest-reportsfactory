package com.ec.proharvest.service.mapper;


import com.ec.proharvest.domain.*;
import com.ec.proharvest.service.dto.ReportDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportDocument} and its DTO {@link ReportDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportTypeMapper.class, ReportConfigMapper.class, ReportParametersMapper.class})
public interface ReportDocumentMapper extends EntityMapper<ReportDocumentDTO, ReportDocument> {

    @Mapping(source = "reportType.id", target = "reportTypeId")
    @Mapping(source = "reportType.name", target = "reportTypeName")
    @Mapping(source = "reportConfig.id", target = "reportConfigId")
    @Mapping(source = "reportConfig.name", target = "reportConfigName")
    @Mapping(source = "reportParameters.id", target = "reportParametersId")
    @Mapping(source = "reportParameters.name", target = "reportParametersName")
    ReportDocumentDTO toDto(ReportDocument reportDocument);

    @Mapping(target = "reportingDataSets", ignore = true)
    @Mapping(target = "removeReportingDataSets", ignore = true)
    @Mapping(source = "reportTypeId", target = "reportType")
    @Mapping(source = "reportConfigId", target = "reportConfig")
    @Mapping(source = "reportParametersId", target = "reportParameters")
    ReportDocument toEntity(ReportDocumentDTO reportDocumentDTO);

    default ReportDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportDocument reportDocument = new ReportDocument();
        reportDocument.setId(id);
        return reportDocument;
    }
}
