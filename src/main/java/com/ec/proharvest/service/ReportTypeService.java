package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportType;
import com.ec.proharvest.repository.ReportTypeRepository;
import com.ec.proharvest.repository.search.ReportTypeSearchRepository;
import com.ec.proharvest.service.dto.ReportTypeDTO;
import com.ec.proharvest.service.mapper.ReportTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportType}.
 */
@Service
@Transactional
public class ReportTypeService {

    private final Logger log = LoggerFactory.getLogger(ReportTypeService.class);

    private final ReportTypeRepository reportTypeRepository;

    private final ReportTypeMapper reportTypeMapper;

    private final ReportTypeSearchRepository reportTypeSearchRepository;

    public ReportTypeService(ReportTypeRepository reportTypeRepository, ReportTypeMapper reportTypeMapper, ReportTypeSearchRepository reportTypeSearchRepository) {
        this.reportTypeRepository = reportTypeRepository;
        this.reportTypeMapper = reportTypeMapper;
        this.reportTypeSearchRepository = reportTypeSearchRepository;
    }

    /**
     * Save a reportType.
     *
     * @param reportTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportTypeDTO save(ReportTypeDTO reportTypeDTO) {
        log.debug("Request to save ReportType : {}", reportTypeDTO);
        ReportType reportType = reportTypeMapper.toEntity(reportTypeDTO);
        reportType = reportTypeRepository.save(reportType);
        ReportTypeDTO result = reportTypeMapper.toDto(reportType);
        reportTypeSearchRepository.save(reportType);
        return result;
    }

    /**
     * Get all the reportTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportTypes");
        return reportTypeRepository.findAll(pageable)
            .map(reportTypeMapper::toDto);
    }


    /**
     * Get one reportType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportTypeDTO> findOne(Long id) {
        log.debug("Request to get ReportType : {}", id);
        return reportTypeRepository.findById(id)
            .map(reportTypeMapper::toDto);
    }

    /**
     * Delete the reportType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportType : {}", id);
        reportTypeRepository.deleteById(id);
        reportTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportType corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReportTypes for query {}", query);
        return reportTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(reportTypeMapper::toDto);
    }
}
