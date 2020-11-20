package com.ec.proharvest.repository;

import java.util.List;

import com.ec.proharvest.domain.ReportDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportDocumentRepository extends JpaRepository<ReportDocument, Long> {
    List<ReportDocument> findByName(String name);

    @Query("select dts from ReportingDataSet dts join fetch dts.reportDocument rp where rp.id = :id")
    List<ReportDocument> findByIdWithData(@Param("id") Long id);
    
    @Query("select dts from ReportingDataSet dts join fetch dts.reportDocument rp where rp.name = :name")
    List<ReportDocument> findByNameWithData(@Param("name") String name);
}
