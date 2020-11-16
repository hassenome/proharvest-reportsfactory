package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportingData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportingData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportingDataRepository extends JpaRepository<ReportingData, Long> {
}
