package com.ec.proharvest.web.rest;

import com.ec.proharvest.service.ReportDocumentService;
import com.ec.proharvest.web.rest.errors.BadRequestAlertException;
import com.ec.proharvest.service.dto.ReportDocumentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportDocument}.
 */
@RestController
@RequestMapping("/api")
public class ReportDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ReportDocumentResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportDocumentService reportDocumentService;

    public ReportDocumentResource(ReportDocumentService reportDocumentService) {
        this.reportDocumentService = reportDocumentService;
    }

    /**
     * {@code POST  /report-documents} : Create a new reportDocument.
     *
     * @param reportDocumentDTO the reportDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportDocumentDTO, or with status {@code 400 (Bad Request)} if the reportDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-documents")
    public ResponseEntity<ReportDocumentDTO> createReportDocument(@Valid @RequestBody ReportDocumentDTO reportDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ReportDocument : {}", reportDocumentDTO);
        if (reportDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportDocumentDTO result = reportDocumentService.save(reportDocumentDTO);
        return ResponseEntity.created(new URI("/api/report-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-documents} : Updates an existing reportDocument.
     *
     * @param reportDocumentDTO the reportDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the reportDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-documents")
    public ResponseEntity<ReportDocumentDTO> updateReportDocument(@Valid @RequestBody ReportDocumentDTO reportDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ReportDocument : {}", reportDocumentDTO);
        if (reportDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportDocumentDTO result = reportDocumentService.save(reportDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-documents} : get all the reportDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportDocuments in body.
     */
    @GetMapping("/report-documents")
    public List<ReportDocumentDTO> getAllReportDocuments() {
        log.debug("REST request to get all ReportDocuments");
        return reportDocumentService.findAll();
    }

    /**
     * {@code GET  /report-documents/:id} : get the "id" reportDocument.
     *
     * @param id the id of the reportDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-documents/{id}")
    public ResponseEntity<ReportDocumentDTO> getReportDocument(@PathVariable Long id) {
        log.debug("REST request to get ReportDocument : {}", id);
        Optional<ReportDocumentDTO> reportDocumentDTO = reportDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportDocumentDTO);
    }

    /**
     * {@code DELETE  /report-documents/:id} : delete the "id" reportDocument.
     *
     * @param id the id of the reportDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-documents/{id}")
    public ResponseEntity<Void> deleteReportDocument(@PathVariable Long id) {
        log.debug("REST request to delete ReportDocument : {}", id);
        reportDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-documents?query=:query} : search for the reportDocument corresponding
     * to the query.
     *
     * @param query the query of the reportDocument search.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-documents")
    public List<ReportDocumentDTO> searchReportDocuments(@RequestParam String query) {
        log.debug("REST request to search ReportDocuments for query {}", query);
        return reportDocumentService.search(query);
    }
}
