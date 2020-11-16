package com.ec.proharvest.repository;

import com.ec.proharvest.domain.ReportDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportDocumentRepository extends JpaRepository<ReportDocument, Long> {
}
