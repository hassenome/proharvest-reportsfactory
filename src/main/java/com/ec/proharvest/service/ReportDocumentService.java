package com.ec.proharvest.service;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.search.ReportDocumentSearchRepository;
import com.ec.proharvest.service.dto.ReportDocumentDTO;
import com.ec.proharvest.service.mapper.ReportDocumentMapper;
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
 * Service Implementation for managing {@link ReportDocument}.
 */
@Service
@Transactional
public class ReportDocumentService {

    private final Logger log = LoggerFactory.getLogger(ReportDocumentService.class);

    private final ReportDocumentRepository reportDocumentRepository;

    private final ReportDocumentMapper reportDocumentMapper;

    private final ReportDocumentSearchRepository reportDocumentSearchRepository;

    public ReportDocumentService(ReportDocumentRepository reportDocumentRepository, ReportDocumentMapper reportDocumentMapper, ReportDocumentSearchRepository reportDocumentSearchRepository) {
        this.reportDocumentRepository = reportDocumentRepository;
        this.reportDocumentMapper = reportDocumentMapper;
        this.reportDocumentSearchRepository = reportDocumentSearchRepository;
    }

    /**
     * Save a reportDocument.
     *
     * @param reportDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportDocumentDTO save(ReportDocumentDTO reportDocumentDTO) {
        log.debug("Request to save ReportDocument : {}", reportDocumentDTO);
        ReportDocument reportDocument = reportDocumentMapper.toEntity(reportDocumentDTO);
        reportDocument = reportDocumentRepository.save(reportDocument);
        ReportDocumentDTO result = reportDocumentMapper.toDto(reportDocument);
        reportDocumentSearchRepository.save(reportDocument);
        return result;
    }

    /**
     * Get all the reportDocuments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReportDocumentDTO> findAll() {
        log.debug("Request to get all ReportDocuments");
        return reportDocumentRepository.findAll().stream()
            .map(reportDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one reportDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportDocumentDTO> findOne(Long id) {
        log.debug("Request to get ReportDocument : {}", id);
        return reportDocumentRepository.findById(id)
            .map(reportDocumentMapper::toDto);
    }

    /**
     * Delete the reportDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportDocument : {}", id);
        reportDocumentRepository.deleteById(id);
        reportDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportDocument corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReportDocumentDTO> search(String query) {
        log.debug("Request to search ReportDocuments for query {}", query);
        return StreamSupport
            .stream(reportDocumentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reportDocumentMapper::toDto)
        .collect(Collectors.toList());
    }
}
