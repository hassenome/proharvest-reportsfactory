package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.ec.proharvest.repository.search.ReportingDataSetSearchRepository;
import com.ec.proharvest.service.dto.ReportingDataSetDTO;
import com.ec.proharvest.service.mapper.ReportingDataSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportingDataSet}.
 */
@Service
@Transactional
public class ReportingDataSetService {

    private final Logger log = LoggerFactory.getLogger(ReportingDataSetService.class);

    private final ReportingDataSetRepository reportingDataSetRepository;

    private final ReportingDataSetMapper reportingDataSetMapper;

    private final ReportingDataSetSearchRepository reportingDataSetSearchRepository;

    public ReportingDataSetService(ReportingDataSetRepository reportingDataSetRepository, ReportingDataSetMapper reportingDataSetMapper, ReportingDataSetSearchRepository reportingDataSetSearchRepository) {
        this.reportingDataSetRepository = reportingDataSetRepository;
        this.reportingDataSetMapper = reportingDataSetMapper;
        this.reportingDataSetSearchRepository = reportingDataSetSearchRepository;
    }

    /**
     * Save a reportingDataSet.
     *
     * @param reportingDataSetDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportingDataSetDTO save(ReportingDataSetDTO reportingDataSetDTO) {
        log.debug("Request to save ReportingDataSet : {}", reportingDataSetDTO);
        ReportingDataSet reportingDataSet = reportingDataSetMapper.toEntity(reportingDataSetDTO);
        reportingDataSet = reportingDataSetRepository.save(reportingDataSet);
        ReportingDataSetDTO result = reportingDataSetMapper.toDto(reportingDataSet);
        reportingDataSetSearchRepository.save(reportingDataSet);
        return result;
    }

    /**
     * Get all the reportingDataSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportingDataSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportingDataSets");
        return reportingDataSetRepository.findAll(pageable)
            .map(reportingDataSetMapper::toDto);
    }


    /**
     * Get one reportingDataSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportingDataSetDTO> findOne(Long id) {
        log.debug("Request to get ReportingDataSet : {}", id);
        return reportingDataSetRepository.findById(id)
            .map(reportingDataSetMapper::toDto);
    }

    /**
     * Delete the reportingDataSet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportingDataSet : {}", id);
        reportingDataSetRepository.deleteById(id);
        reportingDataSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportingDataSet corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportingDataSetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReportingDataSets for query {}", query);
        return reportingDataSetSearchRepository.search(queryStringQuery(query), pageable)
            .map(reportingDataSetMapper::toDto);
    }
}
