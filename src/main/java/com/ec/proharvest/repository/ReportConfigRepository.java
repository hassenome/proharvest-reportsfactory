package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportConfigRepository extends JpaRepository<ReportConfig, Long> {
}
