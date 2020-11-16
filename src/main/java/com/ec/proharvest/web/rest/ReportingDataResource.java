package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportingData;
import com.ec.proharvest.repository.ReportingDataRepository;
import com.ec.proharvest.repository.search.ReportingDataSearchRepository;
import com.ec.proharvest.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportingData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportingDataResource {

    private final Logger log = LoggerFactory.getLogger(ReportingDataResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportingData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportingDataRepository reportingDataRepository;

    private final ReportingDataSearchRepository reportingDataSearchRepository;

    public ReportingDataResource(ReportingDataRepository reportingDataRepository, ReportingDataSearchRepository reportingDataSearchRepository) {
        this.reportingDataRepository = reportingDataRepository;
        this.reportingDataSearchRepository = reportingDataSearchRepository;
    }

    /**
     * {@code POST  /reporting-data} : Create a new reportingData.
     *
     * @param reportingData the reportingData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportingData, or with status {@code 400 (Bad Request)} if the reportingData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporting-data")
    public ResponseEntity<ReportingData> createReportingData(@Valid @RequestBody ReportingData reportingData) throws URISyntaxException {
        log.debug("REST request to save ReportingData : {}", reportingData);
        if (reportingData.getId() != null) {
            throw new BadRequestAlertException("A new reportingData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportingData result = reportingDataRepository.save(reportingData);
        reportingDataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reporting-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporting-data} : Updates an existing reportingData.
     *
     * @param reportingData the reportingData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportingData,
     * or with status {@code 400 (Bad Request)} if the reportingData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportingData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporting-data")
    public ResponseEntity<ReportingData> updateReportingData(@Valid @RequestBody ReportingData reportingData) throws URISyntaxException {
        log.debug("REST request to update ReportingData : {}", reportingData);
        if (reportingData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportingData result = reportingDataRepository.save(reportingData);
        reportingDataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportingData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reporting-data} : get all the reportingData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportingData in body.
     */
    @GetMapping("/reporting-data")
    public List<ReportingData> getAllReportingData() {
        log.debug("REST request to get all ReportingData");
        return reportingDataRepository.findAll();
    }

    /**
     * {@code GET  /reporting-data/:id} : get the "id" reportingData.
     *
     * @param id the id of the reportingData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportingData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporting-data/{id}")
    public ResponseEntity<ReportingData> getReportingData(@PathVariable Long id) {
        log.debug("REST request to get ReportingData : {}", id);
        Optional<ReportingData> reportingData = reportingDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportingData);
    }

    /**
     * {@code DELETE  /reporting-data/:id} : delete the "id" reportingData.
     *
     * @param id the id of the reportingData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporting-data/{id}")
    public ResponseEntity<Void> deleteReportingData(@PathVariable Long id) {
        log.debug("REST request to delete ReportingData : {}", id);
        reportingDataRepository.deleteById(id);
        reportingDataSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reporting-data?query=:query} : search for the reportingData corresponding
     * to the query.
     *
     * @param query the query of the reportingData search.
     * @return the result of the search.
     */
    @GetMapping("/_search/reporting-data")
    public List<ReportingData> searchReportingData(@RequestParam String query) {
        log.debug("REST request to search ReportingData for query {}", query);
        return StreamSupport
            .stream(reportingDataSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
