package com.ec.proharvest.service.mapper;


import com.ec.proharvest.domain.*;
import com.ec.proharvest.service.dto.ReportConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportConfig} and its DTO {@link ReportConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportConfigMapper extends EntityMapper<ReportConfigDTO, ReportConfig> {



    default ReportConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportConfig reportConfig = new ReportConfig();
        reportConfig.setId(id);
        return reportConfig;
    }
}
