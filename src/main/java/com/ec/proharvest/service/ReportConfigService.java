package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.repository.ReportConfigRepository;
import com.ec.proharvest.repository.search.ReportConfigSearchRepository;
import com.ec.proharvest.service.dto.ReportConfigDTO;
import com.ec.proharvest.service.mapper.ReportConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportConfig}.
 */
@Service
@Transactional
public class ReportConfigService {

    private final Logger log = LoggerFactory.getLogger(ReportConfigService.class);

    private final ReportConfigRepository reportConfigRepository;

    private final ReportConfigMapper reportConfigMapper;

    private final ReportConfigSearchRepository reportConfigSearchRepository;

    public ReportConfigService(ReportConfigRepository reportConfigRepository, ReportConfigMapper reportConfigMapper, ReportConfigSearchRepository reportConfigSearchRepository) {
        this.reportConfigRepository = reportConfigRepository;
        this.reportConfigMapper = reportConfigMapper;
        this.reportConfigSearchRepository = reportConfigSearchRepository;
    }

    /**
     * Save a reportConfig.
     *
     * @param reportConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportConfigDTO save(ReportConfigDTO reportConfigDTO) {
        log.debug("Request to save ReportConfig : {}", reportConfigDTO);
        ReportConfig reportConfig = reportConfigMapper.toEntity(reportConfigDTO);
        reportConfig = reportConfigRepository.save(reportConfig);
        ReportConfigDTO result = reportConfigMapper.toDto(reportConfig);
        reportConfigSearchRepository.save(reportConfig);
        return result;
    }

    /**
     * Get all the reportConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportConfigs");
        return reportConfigRepository.findAll(pageable)
            .map(reportConfigMapper::toDto);
    }


    /**
     * Get one reportConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportConfigDTO> findOne(Long id) {
        log.debug("Request to get ReportConfig : {}", id);
        return reportConfigRepository.findById(id)
            .map(reportConfigMapper::toDto);
    }

    /**
     * Delete the reportConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportConfig : {}", id);
        reportConfigRepository.deleteById(id);
        reportConfigSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportConfig corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportConfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReportConfigs for query {}", query);
        return reportConfigSearchRepository.search(queryStringQuery(query), pageable)
            .map(reportConfigMapper::toDto);
    }
}
