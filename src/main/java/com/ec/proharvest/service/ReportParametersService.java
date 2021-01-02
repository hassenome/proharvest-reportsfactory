package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportParameters;
import com.ec.proharvest.repository.ReportParametersRepository;
import com.ec.proharvest.repository.search.ReportParametersSearchRepository;
import com.ec.proharvest.service.dto.ReportParametersDTO;
import com.ec.proharvest.service.mapper.ReportParametersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportParameters}.
 */
@Service
@Transactional
public class ReportParametersService {

    private final Logger log = LoggerFactory.getLogger(ReportParametersService.class);

    private final ReportParametersRepository reportParametersRepository;

    private final ReportParametersMapper reportParametersMapper;

    private final ReportParametersSearchRepository reportParametersSearchRepository;

    public ReportParametersService(ReportParametersRepository reportParametersRepository, ReportParametersMapper reportParametersMapper, ReportParametersSearchRepository reportParametersSearchRepository) {
        this.reportParametersRepository = reportParametersRepository;
        this.reportParametersMapper = reportParametersMapper;
        this.reportParametersSearchRepository = reportParametersSearchRepository;
    }

    /**
     * Save a reportParameters.
     *
     * @param reportParametersDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportParametersDTO save(ReportParametersDTO reportParametersDTO) {
        log.debug("Request to save ReportParameters : {}", reportParametersDTO);
        ReportParameters reportParameters = reportParametersMapper.toEntity(reportParametersDTO);
        reportParameters = reportParametersRepository.save(reportParameters);
        ReportParametersDTO result = reportParametersMapper.toDto(reportParameters);
        reportParametersSearchRepository.save(reportParameters);
        return result;
    }

    /**
     * Get all the reportParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReportParametersDTO> findAll() {
        log.debug("Request to get all ReportParameters");
        return reportParametersRepository.findAll().stream()
            .map(reportParametersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one reportParameters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportParametersDTO> findOne(Long id) {
        log.debug("Request to get ReportParameters : {}", id);
        return reportParametersRepository.findById(id)
            .map(reportParametersMapper::toDto);
    }

    /**
     * Delete the reportParameters by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportParameters : {}", id);
        reportParametersRepository.deleteById(id);
        reportParametersSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportParameters corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReportParametersDTO> search(String query) {
        log.debug("Request to search ReportParameters for query {}", query);
        return StreamSupport
            .stream(reportParametersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reportParametersMapper::toDto)
        .collect(Collectors.toList());
    }
}
