package com.ec.proharvest.repository;

import java.util.List;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.domain.ReportingDataSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportingDataSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportingDataSetRepository extends JpaRepository<ReportingDataSet, Long>, ReportingDataSetRepositoryCustom {
    List<ReportingDataSet> findByReportDocument(ReportDocument reportDocument);
}
