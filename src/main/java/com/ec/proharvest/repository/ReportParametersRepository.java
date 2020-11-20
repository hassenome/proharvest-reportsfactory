package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportParameters;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportParameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportParametersRepository extends JpaRepository<ReportParameters, Long> {
}
