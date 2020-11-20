package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.repository.ReportConfigRepository;
import com.ec.proharvest.repository.search.ReportConfigSearchRepository;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportConfig}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportConfigResource {

    private final Logger log = LoggerFactory.getLogger(ReportConfigResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportConfigRepository reportConfigRepository;

    private final ReportConfigSearchRepository reportConfigSearchRepository;

    public ReportConfigResource(ReportConfigRepository reportConfigRepository, ReportConfigSearchRepository reportConfigSearchRepository) {
        this.reportConfigRepository = reportConfigRepository;
        this.reportConfigSearchRepository = reportConfigSearchRepository;
    }

    /**
     * {@code POST  /report-configs} : Create a new reportConfig.
     *
     * @param reportConfig the reportConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportConfig, or with status {@code 400 (Bad Request)} if the reportConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-configs")
    public ResponseEntity<ReportConfig> createReportConfig(@Valid @RequestBody ReportConfig reportConfig) throws URISyntaxException {
        log.debug("REST request to save ReportConfig : {}", reportConfig);
        if (reportConfig.getId() != null) {
            throw new BadRequestAlertException("A new reportConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportConfig result = reportConfigRepository.save(reportConfig);
        reportConfigSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/report-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-configs} : Updates an existing reportConfig.
     *
     * @param reportConfig the reportConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportConfig,
     * or with status {@code 400 (Bad Request)} if the reportConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-configs")
    public ResponseEntity<ReportConfig> updateReportConfig(@Valid @RequestBody ReportConfig reportConfig) throws URISyntaxException {
        log.debug("REST request to update ReportConfig : {}", reportConfig);
        if (reportConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportConfig result = reportConfigRepository.save(reportConfig);
        reportConfigSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-configs} : get all the reportConfigs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportConfigs in body.
     */
    @GetMapping("/report-configs")
    public ResponseEntity<List<ReportConfig>> getAllReportConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ReportConfigs");
        Page<ReportConfig> page = reportConfigRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-configs/:id} : get the "id" reportConfig.
     *
     * @param id the id of the reportConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-configs/{id}")
    public ResponseEntity<ReportConfig> getReportConfig(@PathVariable Long id) {
        log.debug("REST request to get ReportConfig : {}", id);
        Optional<ReportConfig> reportConfig = reportConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportConfig);
    }

    /**
     * {@code DELETE  /report-configs/:id} : delete the "id" reportConfig.
     *
     * @param id the id of the reportConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-configs/{id}")
    public ResponseEntity<Void> deleteReportConfig(@PathVariable Long id) {
        log.debug("REST request to delete ReportConfig : {}", id);
        reportConfigRepository.deleteById(id);
        reportConfigSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-configs?query=:query} : search for the reportConfig corresponding
     * to the query.
     *
     * @param query the query of the reportConfig search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-configs")
    public ResponseEntity<List<ReportConfig>> searchReportConfigs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReportConfigs for query {}", query);
        Page<ReportConfig> page = reportConfigSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
