package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportType;
import com.ec.proharvest.repository.ReportTypeRepository;
import com.ec.proharvest.repository.search.ReportTypeSearchRepository;
import com.ec.proharvest.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ec.proharvest.domain.ReportType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportTypeResource {

    private final Logger log = LoggerFactory.getLogger(ReportTypeResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportTypeRepository reportTypeRepository;

    private final ReportTypeSearchRepository reportTypeSearchRepository;

    public ReportTypeResource(ReportTypeRepository reportTypeRepository, ReportTypeSearchRepository reportTypeSearchRepository) {
        this.reportTypeRepository = reportTypeRepository;
        this.reportTypeSearchRepository = reportTypeSearchRepository;
    }

    /**
     * {@code POST  /report-types} : Create a new reportType.
     *
     * @param reportType the reportType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportType, or with status {@code 400 (Bad Request)} if the reportType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-types")
    public ResponseEntity<ReportType> createReportType(@Valid @RequestBody ReportType reportType) throws URISyntaxException {
        log.debug("REST request to save ReportType : {}", reportType);
        if (reportType.getId() != null) {
            throw new BadRequestAlertException("A new reportType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportType result = reportTypeRepository.save(reportType);
        reportTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/report-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-types} : Updates an existing reportType.
     *
     * @param reportType the reportType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportType,
     * or with status {@code 400 (Bad Request)} if the reportType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-types")
    public ResponseEntity<ReportType> updateReportType(@Valid @RequestBody ReportType reportType) throws URISyntaxException {
        log.debug("REST request to update ReportType : {}", reportType);
        if (reportType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportType result = reportTypeRepository.save(reportType);
        reportTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-types} : get all the reportTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportTypes in body.
     */
    @GetMapping("/report-types")
    public ResponseEntity<List<ReportType>> getAllReportTypes(Pageable pageable) {
        log.debug("REST request to get a page of ReportTypes");
        Page<ReportType> page = reportTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-types/:id} : get the "id" reportType.
     *
     * @param id the id of the reportType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-types/{id}")
    public ResponseEntity<ReportType> getReportType(@PathVariable Long id) {
        log.debug("REST request to get ReportType : {}", id);
        Optional<ReportType> reportType = reportTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportType);
    }

    /**
     * {@code DELETE  /report-types/:id} : delete the "id" reportType.
     *
     * @param id the id of the reportType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-types/{id}")
    public ResponseEntity<Void> deleteReportType(@PathVariable Long id) {
        log.debug("REST request to delete ReportType : {}", id);
        reportTypeRepository.deleteById(id);
        reportTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-types?query=:query} : search for the reportType corresponding
     * to the query.
     *
     * @param query the query of the reportType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-types")
    public ResponseEntity<List<ReportType>> searchReportTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReportTypes for query {}", query);
        Page<ReportType> page = reportTypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
