package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportLanguage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportLanguage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportLanguageRepository extends JpaRepository<ReportLanguage, Long> {
}
