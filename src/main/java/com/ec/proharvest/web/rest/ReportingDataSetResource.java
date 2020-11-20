package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.ec.proharvest.repository.search.ReportingDataSetSearchRepository;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportingDataSet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportingDataSetResource {

    private final Logger log = LoggerFactory.getLogger(ReportingDataSetResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportingDataSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportingDataSetRepository reportingDataSetRepository;

    private final ReportingDataSetSearchRepository reportingDataSetSearchRepository;

    public ReportingDataSetResource(ReportingDataSetRepository reportingDataSetRepository, ReportingDataSetSearchRepository reportingDataSetSearchRepository) {
        this.reportingDataSetRepository = reportingDataSetRepository;
        this.reportingDataSetSearchRepository = reportingDataSetSearchRepository;
    }

    /**
     * {@code POST  /reporting-data-sets} : Create a new reportingDataSet.
     *
     * @param reportingDataSet the reportingDataSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportingDataSet, or with status {@code 400 (Bad Request)} if the reportingDataSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporting-data-sets")
    public ResponseEntity<ReportingDataSet> createReportingDataSet(@Valid @RequestBody ReportingDataSet reportingDataSet) throws URISyntaxException {
        log.debug("REST request to save ReportingDataSet : {}", reportingDataSet);
        if (reportingDataSet.getId() != null) {
            throw new BadRequestAlertException("A new reportingDataSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportingDataSet result = reportingDataSetRepository.save(reportingDataSet);
        reportingDataSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reporting-data-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporting-data-sets} : Updates an existing reportingDataSet.
     *
     * @param reportingDataSet the reportingDataSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportingDataSet,
     * or with status {@code 400 (Bad Request)} if the reportingDataSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportingDataSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporting-data-sets")
    public ResponseEntity<ReportingDataSet> updateReportingDataSet(@Valid @RequestBody ReportingDataSet reportingDataSet) throws URISyntaxException {
        log.debug("REST request to update ReportingDataSet : {}", reportingDataSet);
        if (reportingDataSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportingDataSet result = reportingDataSetRepository.save(reportingDataSet);
        reportingDataSetSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportingDataSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reporting-data-sets} : get all the reportingDataSet.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportingDataSet in body.
     */
    @GetMapping("/reporting-data-sets")
    public List<ReportingDataSet> getAllReportingDataSet() {
        log.debug("REST request to get all ReportingDataSet");
        return reportingDataSetRepository.findAll();
    }

    /**
     * {@code GET  /reporting-data-sets/:id} : get the "id" reportingDataSet.
     *
     * @param id the id of the reportingDataSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportingDataSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporting-data-sets/{id}")
    public ResponseEntity<ReportingDataSet> getReportingDataSet(@PathVariable Long id) {
        log.debug("REST request to get ReportingDataSet : {}", id);
        Optional<ReportingDataSet> reportingDataSet = reportingDataSetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportingDataSet);
    }

    /**
     * {@code DELETE  /reporting-data-sets/:id} : delete the "id" reportingDataSet.
     *
     * @param id the id of the reportingDataSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporting-data-sets/{id}")
    public ResponseEntity<Void> deleteReportingDataSet(@PathVariable Long id) {
        log.debug("REST request to delete ReportingDataSet : {}", id);
        reportingDataSetRepository.deleteById(id);
        reportingDataSetSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reporting-data-sets?query=:query} : search for the reportingDataSet corresponding
     * to the query.
     *
     * @param query the query of the reportingDataSet search.
     * @return the result of the search.
     */
    @GetMapping("/_search/reporting-data-sets")
    public List<ReportingDataSet> searchReportingDataSet(@RequestParam String query) {
        log.debug("REST request to search ReportingDataSet for query {}", query);
        return StreamSupport
            .stream(reportingDataSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
