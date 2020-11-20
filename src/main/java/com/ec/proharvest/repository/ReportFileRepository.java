package com.ec.proharvest.repository;

import java.util.List;

import com.ec.proharvest.domain.ReportFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportFileRepository extends JpaRepository<ReportFile, Long> {
    List<ReportFile> findByFileName(String fileName);
}
