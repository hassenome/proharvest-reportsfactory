package com.ec.proharvest.web.rest;

import com.ec.proharvest.service.ReportingDataSetService;
import com.ec.proharvest.web.rest.errors.BadRequestAlertException;
import com.ec.proharvest.service.dto.ReportingDataSetDTO;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ec.proharvest.domain.ReportingDataSet}.
 */
@RestController
@RequestMapping("/api")
public class ReportingDataSetResource {

    private final Logger log = LoggerFactory.getLogger(ReportingDataSetResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportingDataSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportingDataSetService reportingDataSetService;

    public ReportingDataSetResource(ReportingDataSetService reportingDataSetService) {
        this.reportingDataSetService = reportingDataSetService;
    }

    /**
     * {@code POST  /reporting-data-sets} : Create a new reportingDataSet.
     *
     * @param reportingDataSetDTO the reportingDataSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportingDataSetDTO, or with status {@code 400 (Bad Request)} if the reportingDataSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporting-data-sets")
    public ResponseEntity<ReportingDataSetDTO> createReportingDataSet(@Valid @RequestBody ReportingDataSetDTO reportingDataSetDTO) throws URISyntaxException {
        log.debug("REST request to save ReportingDataSet : {}", reportingDataSetDTO);
        if (reportingDataSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportingDataSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportingDataSetDTO result = reportingDataSetService.save(reportingDataSetDTO);
        return ResponseEntity.created(new URI("/api/reporting-data-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporting-data-sets} : Updates an existing reportingDataSet.
     *
     * @param reportingDataSetDTO the reportingDataSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportingDataSetDTO,
     * or with status {@code 400 (Bad Request)} if the reportingDataSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportingDataSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporting-data-sets")
    public ResponseEntity<ReportingDataSetDTO> updateReportingDataSet(@Valid @RequestBody ReportingDataSetDTO reportingDataSetDTO) throws URISyntaxException {
        log.debug("REST request to update ReportingDataSet : {}", reportingDataSetDTO);
        if (reportingDataSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportingDataSetDTO result = reportingDataSetService.save(reportingDataSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportingDataSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reporting-data-sets} : get all the reportingDataSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportingDataSets in body.
     */
    @GetMapping("/reporting-data-sets")
    public ResponseEntity<List<ReportingDataSetDTO>> getAllReportingDataSets(Pageable pageable) {
        log.debug("REST request to get a page of ReportingDataSets");
        Page<ReportingDataSetDTO> page = reportingDataSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reporting-data-sets/:id} : get the "id" reportingDataSet.
     *
     * @param id the id of the reportingDataSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportingDataSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporting-data-sets/{id}")
    public ResponseEntity<ReportingDataSetDTO> getReportingDataSet(@PathVariable Long id) {
        log.debug("REST request to get ReportingDataSet : {}", id);
        Optional<ReportingDataSetDTO> reportingDataSetDTO = reportingDataSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportingDataSetDTO);
    }

    /**
     * {@code DELETE  /reporting-data-sets/:id} : delete the "id" reportingDataSet.
     *
     * @param id the id of the reportingDataSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporting-data-sets/{id}")
    public ResponseEntity<Void> deleteReportingDataSet(@PathVariable Long id) {
        log.debug("REST request to delete ReportingDataSet : {}", id);
        reportingDataSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reporting-data-sets?query=:query} : search for the reportingDataSet corresponding
     * to the query.
     *
     * @param query the query of the reportingDataSet search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reporting-data-sets")
    public ResponseEntity<List<ReportingDataSetDTO>> searchReportingDataSets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReportingDataSets for query {}", query);
        Page<ReportingDataSetDTO> page = reportingDataSetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
