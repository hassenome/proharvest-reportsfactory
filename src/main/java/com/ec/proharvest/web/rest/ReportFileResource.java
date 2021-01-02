package com.ec.proharvest.web.rest;

import com.ec.proharvest.service.ReportFileService;
import com.ec.proharvest.web.rest.errors.BadRequestAlertException;
import com.ec.proharvest.service.dto.ReportFileDTO;

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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportFile}.
 */
@RestController
@RequestMapping("/api")
public class ReportFileResource {

    private final Logger log = LoggerFactory.getLogger(ReportFileResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportFileService reportFileService;

    public ReportFileResource(ReportFileService reportFileService) {
        this.reportFileService = reportFileService;
    }

    /**
     * {@code POST  /report-files} : Create a new reportFile.
     *
     * @param reportFileDTO the reportFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportFileDTO, or with status {@code 400 (Bad Request)} if the reportFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-files")
    public ResponseEntity<ReportFileDTO> createReportFile(@Valid @RequestBody ReportFileDTO reportFileDTO) throws URISyntaxException {
        log.debug("REST request to save ReportFile : {}", reportFileDTO);
        if (reportFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportFileDTO result = reportFileService.save(reportFileDTO);
        return ResponseEntity.created(new URI("/api/report-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-files} : Updates an existing reportFile.
     *
     * @param reportFileDTO the reportFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportFileDTO,
     * or with status {@code 400 (Bad Request)} if the reportFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-files")
    public ResponseEntity<ReportFileDTO> updateReportFile(@Valid @RequestBody ReportFileDTO reportFileDTO) throws URISyntaxException {
        log.debug("REST request to update ReportFile : {}", reportFileDTO);
        if (reportFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportFileDTO result = reportFileService.save(reportFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-files} : get all the reportFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportFiles in body.
     */
    @GetMapping("/report-files")
    public ResponseEntity<List<ReportFileDTO>> getAllReportFiles(Pageable pageable) {
        log.debug("REST request to get a page of ReportFiles");
        Page<ReportFileDTO> page = reportFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-files/:id} : get the "id" reportFile.
     *
     * @param id the id of the reportFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-files/{id}")
    public ResponseEntity<ReportFileDTO> getReportFile(@PathVariable Long id) {
        log.debug("REST request to get ReportFile : {}", id);
        Optional<ReportFileDTO> reportFileDTO = reportFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportFileDTO);
    }

    /**
     * {@code DELETE  /report-files/:id} : delete the "id" reportFile.
     *
     * @param id the id of the reportFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-files/{id}")
    public ResponseEntity<Void> deleteReportFile(@PathVariable Long id) {
        log.debug("REST request to delete ReportFile : {}", id);
        reportFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-files?query=:query} : search for the reportFile corresponding
     * to the query.
     *
     * @param query the query of the reportFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-files")
    public ResponseEntity<List<ReportFileDTO>> searchReportFiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReportFiles for query {}", query);
        Page<ReportFileDTO> page = reportFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
