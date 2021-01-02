package com.ec.proharvest.service.mapper;


import com.ec.proharvest.domain.*;
import com.ec.proharvest.service.dto.ReportFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportFile} and its DTO {@link ReportFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportDocumentMapper.class})
public interface ReportFileMapper extends EntityMapper<ReportFileDTO, ReportFile> {

    @Mapping(source = "reportDocument.id", target = "reportDocumentId")
    @Mapping(source = "reportDocument.name", target = "reportDocumentName")
    ReportFileDTO toDto(ReportFile reportFile);

    @Mapping(source = "reportDocumentId", target = "reportDocument")
    ReportFile toEntity(ReportFileDTO reportFileDTO);

    default ReportFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportFile reportFile = new ReportFile();
        reportFile.setId(id);
        return reportFile;
    }
}
