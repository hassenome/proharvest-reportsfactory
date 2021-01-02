package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportFile;
import com.ec.proharvest.repository.ReportFileRepository;
import com.ec.proharvest.repository.search.ReportFileSearchRepository;
import com.ec.proharvest.service.dto.ReportFileDTO;
import com.ec.proharvest.service.mapper.ReportFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportFile}.
 */
@Service
@Transactional
public class ReportFileService {

    private final Logger log = LoggerFactory.getLogger(ReportFileService.class);

    private final ReportFileRepository reportFileRepository;

    private final ReportFileMapper reportFileMapper;

    private final ReportFileSearchRepository reportFileSearchRepository;

    public ReportFileService(ReportFileRepository reportFileRepository, ReportFileMapper reportFileMapper, ReportFileSearchRepository reportFileSearchRepository) {
        this.reportFileRepository = reportFileRepository;
        this.reportFileMapper = reportFileMapper;
        this.reportFileSearchRepository = reportFileSearchRepository;
    }

    /**
     * Save a reportFile.
     *
     * @param reportFileDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportFileDTO save(ReportFileDTO reportFileDTO) {
        log.debug("Request to save ReportFile : {}", reportFileDTO);
        ReportFile reportFile = reportFileMapper.toEntity(reportFileDTO);
        reportFile = reportFileRepository.save(reportFile);
        ReportFileDTO result = reportFileMapper.toDto(reportFile);
        reportFileSearchRepository.save(reportFile);
        return result;
    }

    /**
     * Get all the reportFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportFiles");
        return reportFileRepository.findAll(pageable)
            .map(reportFileMapper::toDto);
    }


    /**
     * Get one reportFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportFileDTO> findOne(Long id) {
        log.debug("Request to get ReportFile : {}", id);
        return reportFileRepository.findById(id)
            .map(reportFileMapper::toDto);
    }

    /**
     * Delete the reportFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportFile : {}", id);
        reportFileRepository.deleteById(id);
        reportFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReportFiles for query {}", query);
        return reportFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(reportFileMapper::toDto);
    }
}
