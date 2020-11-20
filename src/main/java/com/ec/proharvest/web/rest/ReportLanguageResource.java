package com.ec.proharvest.web.rest;

import com.ec.proharvest.domain.ReportLanguage;
import com.ec.proharvest.repository.ReportLanguageRepository;
import com.ec.proharvest.repository.search.ReportLanguageSearchRepository;
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
 * REST controller for managing {@link com.ec.proharvest.domain.ReportLanguage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportLanguageResource {

    private final Logger log = LoggerFactory.getLogger(ReportLanguageResource.class);

    private static final String ENTITY_NAME = "reportsfactoryReportLanguage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportLanguageRepository reportLanguageRepository;

    private final ReportLanguageSearchRepository reportLanguageSearchRepository;

    public ReportLanguageResource(ReportLanguageRepository reportLanguageRepository, ReportLanguageSearchRepository reportLanguageSearchRepository) {
        this.reportLanguageRepository = reportLanguageRepository;
        this.reportLanguageSearchRepository = reportLanguageSearchRepository;
    }

    /**
     * {@code POST  /report-languages} : Create a new reportLanguage.
     *
     * @param reportLanguage the reportLanguage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportLanguage, or with status {@code 400 (Bad Request)} if the reportLanguage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-languages")
    public ResponseEntity<ReportLanguage> createReportLanguage(@Valid @RequestBody ReportLanguage reportLanguage) throws URISyntaxException {
        log.debug("REST request to save ReportLanguage : {}", reportLanguage);
        if (reportLanguage.getId() != null) {
            throw new BadRequestAlertException("A new reportLanguage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportLanguage result = reportLanguageRepository.save(reportLanguage);
        reportLanguageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/report-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-languages} : Updates an existing reportLanguage.
     *
     * @param reportLanguage the reportLanguage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportLanguage,
     * or with status {@code 400 (Bad Request)} if the reportLanguage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportLanguage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-languages")
    public ResponseEntity<ReportLanguage> updateReportLanguage(@Valid @RequestBody ReportLanguage reportLanguage) throws URISyntaxException {
        log.debug("REST request to update ReportLanguage : {}", reportLanguage);
        if (reportLanguage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportLanguage result = reportLanguageRepository.save(reportLanguage);
        reportLanguageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportLanguage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-languages} : get all the reportLanguages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportLanguages in body.
     */
    @GetMapping("/report-languages")
    public ResponseEntity<List<ReportLanguage>> getAllReportLanguages(Pageable pageable) {
        log.debug("REST request to get a page of ReportLanguages");
        Page<ReportLanguage> page = reportLanguageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-languages/:id} : get the "id" reportLanguage.
     *
     * @param id the id of the reportLanguage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportLanguage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-languages/{id}")
    public ResponseEntity<ReportLanguage> getReportLanguage(@PathVariable Long id) {
        log.debug("REST request to get ReportLanguage : {}", id);
        Optional<ReportLanguage> reportLanguage = reportLanguageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportLanguage);
    }

    /**
     * {@code DELETE  /report-languages/:id} : delete the "id" reportLanguage.
     *
     * @param id the id of the reportLanguage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-languages/{id}")
    public ResponseEntity<Void> deleteReportLanguage(@PathVariable Long id) {
        log.debug("REST request to delete ReportLanguage : {}", id);
        reportLanguageRepository.deleteById(id);
        reportLanguageSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-languages?query=:query} : search for the reportLanguage corresponding
     * to the query.
     *
     * @param query the query of the reportLanguage search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-languages")
    public ResponseEntity<List<ReportLanguage>> searchReportLanguages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReportLanguages for query {}", query);
        Page<ReportLanguage> page = reportLanguageSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
