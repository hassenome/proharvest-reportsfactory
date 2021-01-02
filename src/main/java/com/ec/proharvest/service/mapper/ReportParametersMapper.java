package com.ec.proharvest.service.mapper;


import com.ec.proharvest.domain.*;
import com.ec.proharvest.service.dto.ReportParametersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportParameters} and its DTO {@link ReportParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportParametersMapper extends EntityMapper<ReportParametersDTO, ReportParameters> {



    default ReportParameters fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportParameters reportParameters = new ReportParameters();
        reportParameters.setId(id);
        return reportParameters;
    }
}
