package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportParameters;
import com.ec.proharvest.repository.ReportParametersRepository;
import com.ec.proharvest.repository.search.ReportParametersSearchRepository;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportParameters}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportParametersResource {

    private final Logger log = LoggerFactory.getLogger(ReportParametersResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportParameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportParametersRepository reportParametersRepository;

    private final ReportParametersSearchRepository reportParametersSearchRepository;

    public ReportParametersResource(ReportParametersRepository reportParametersRepository, ReportParametersSearchRepository reportParametersSearchRepository) {
        this.reportParametersRepository = reportParametersRepository;
        this.reportParametersSearchRepository = reportParametersSearchRepository;
    }

    /**
     * {@code POST  /report-parameters} : Create a new reportParameters.
     *
     * @param reportParameters the reportParameters to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportParameters, or with status {@code 400 (Bad Request)} if the reportParameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-parameters")
    public ResponseEntity<ReportParameters> createReportParameters(@Valid @RequestBody ReportParameters reportParameters) throws URISyntaxException {
        log.debug("REST request to save ReportParameters : {}", reportParameters);
        if (reportParameters.getId() != null) {
            throw new BadRequestAlertException("A new reportParameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportParameters result = reportParametersRepository.save(reportParameters);
        reportParametersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/report-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-parameters} : Updates an existing reportParameters.
     *
     * @param reportParameters the reportParameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportParameters,
     * or with status {@code 400 (Bad Request)} if the reportParameters is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportParameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-parameters")
    public ResponseEntity<ReportParameters> updateReportParameters(@Valid @RequestBody ReportParameters reportParameters) throws URISyntaxException {
        log.debug("REST request to update ReportParameters : {}", reportParameters);
        if (reportParameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportParameters result = reportParametersRepository.save(reportParameters);
        reportParametersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportParameters.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-parameters} : get all the reportParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportParameters in body.
     */
    @GetMapping("/report-parameters")
    public List<ReportParameters> getAllReportParameters() {
        log.debug("REST request to get all ReportParameters");
        return reportParametersRepository.findAll();
    }

    /**
     * {@code GET  /report-parameters/:id} : get the "id" reportParameters.
     *
     * @param id the id of the reportParameters to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportParameters, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-parameters/{id}")
    public ResponseEntity<ReportParameters> getReportParameters(@PathVariable Long id) {
        log.debug("REST request to get ReportParameters : {}", id);
        Optional<ReportParameters> reportParameters = reportParametersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportParameters);
    }

    /**
     * {@code DELETE  /report-parameters/:id} : delete the "id" reportParameters.
     *
     * @param id the id of the reportParameters to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-parameters/{id}")
    public ResponseEntity<Void> deleteReportParameters(@PathVariable Long id) {
        log.debug("REST request to delete ReportParameters : {}", id);
        reportParametersRepository.deleteById(id);
        reportParametersSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-parameters?query=:query} : search for the reportParameters corresponding
     * to the query.
     *
     * @param query the query of the reportParameters search.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-parameters")
    public List<ReportParameters> searchReportParameters(@RequestParam String query) {
        log.debug("REST request to search ReportParameters for query {}", query);
        return StreamSupport
            .stream(reportParametersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
