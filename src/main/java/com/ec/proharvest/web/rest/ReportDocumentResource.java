package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.search.ReportDocumentSearchRepository;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportDocument}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ReportDocumentResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportDocumentRepository reportDocumentRepository;

    private final ReportDocumentSearchRepository reportDocumentSearchRepository;

    public ReportDocumentResource(ReportDocumentRepository reportDocumentRepository, ReportDocumentSearchRepository reportDocumentSearchRepository) {
        this.reportDocumentRepository = reportDocumentRepository;
        this.reportDocumentSearchRepository = reportDocumentSearchRepository;
    }

    /**
     * {@code POST  /report-documents} : Create a new reportDocument.
     *
     * @param reportDocument the reportDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportDocument, or with status {@code 400 (Bad Request)} if the reportDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-documents")
    public ResponseEntity<ReportDocument> createReportDocument(@Valid @RequestBody ReportDocument reportDocument) throws URISyntaxException {
        log.debug("REST request to save ReportDocument : {}", reportDocument);
        if (reportDocument.getId() != null) {
            throw new BadRequestAlertException("A new reportDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportDocument result = reportDocumentRepository.save(reportDocument);
        reportDocumentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/report-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-documents} : Updates an existing reportDocument.
     *
     * @param reportDocument the reportDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportDocument,
     * or with status {@code 400 (Bad Request)} if the reportDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-documents")
    public ResponseEntity<ReportDocument> updateReportDocument(@Valid @RequestBody ReportDocument reportDocument) throws URISyntaxException {
        log.debug("REST request to update ReportDocument : {}", reportDocument);
        if (reportDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportDocument result = reportDocumentRepository.save(reportDocument);
        reportDocumentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-documents} : get all the reportDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportDocuments in body.
     */
    @GetMapping("/report-documents")
    public List<ReportDocument> getAllReportDocuments() {
        log.debug("REST request to get all ReportDocuments");
        return reportDocumentRepository.findAll();
    }

    /**
     * {@code GET  /report-documents/:id} : get the "id" reportDocument.
     *
     * @param id the id of the reportDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-documents/{id}")
    public ResponseEntity<ReportDocument> getReportDocument(@PathVariable Long id) {
        log.debug("REST request to get ReportDocument : {}", id);
        Optional<ReportDocument> reportDocument = reportDocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportDocument);
    }

    /**
     * {@code DELETE  /report-documents/:id} : delete the "id" reportDocument.
     *
     * @param id the id of the reportDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-documents/{id}")
    public ResponseEntity<Void> deleteReportDocument(@PathVariable Long id) {
        log.debug("REST request to delete ReportDocument : {}", id);
        reportDocumentRepository.deleteById(id);
        reportDocumentSearchRepository.deleteById(id);
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
    public List<ReportDocument> searchReportDocuments(@RequestParam String query) {
        log.debug("REST request to search ReportDocuments for query {}", query);
        return StreamSupport
            .stream(reportDocumentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
