package com.ec.proharvest.web.rest;

import com.ec.proharvest.ReportsfactoryApp;
import com.ec.proharvest.config.TestSecurityConfiguration;
import com.ec.proharvest.domain.ReportDocument;
import com.ec.proharvest.repository.ReportDocumentRepository;
import com.ec.proharvest.repository.search.ReportDocumentSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReportDocumentResource} REST controller.
 */
@SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportDocumentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ReportDocumentRepository reportDocumentRepository;

    /**
     * This repository is mocked in the com.ec.proharvest.repository.search test package.
     *
     * @see com.ec.proharvest.repository.search.ReportDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReportDocumentSearchRepository mockReportDocumentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportDocumentMockMvc;

    private ReportDocument reportDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportDocument createEntity(EntityManager em) {
        ReportDocument reportDocument = new ReportDocument()
            .name(DEFAULT_NAME);
        return reportDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportDocument createUpdatedEntity(EntityManager em) {
        ReportDocument reportDocument = new ReportDocument()
            .name(UPDATED_NAME);
        return reportDocument;
    }

    @BeforeEach
    public void initTest() {
        reportDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportDocument() throws Exception {
        int databaseSizeBeforeCreate = reportDocumentRepository.findAll().size();
        // Create the ReportDocument
        restReportDocumentMockMvc.perform(post("/api/report-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDocument)))
            .andExpect(status().isCreated());

        // Validate the ReportDocument in the database
        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ReportDocument testReportDocument = reportDocumentList.get(reportDocumentList.size() - 1);
        assertThat(testReportDocument.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ReportDocument in Elasticsearch
        verify(mockReportDocumentSearchRepository, times(1)).save(testReportDocument);
    }

    @Test
    @Transactional
    public void createReportDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportDocumentRepository.findAll().size();

        // Create the ReportDocument with an existing ID
        reportDocument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportDocumentMockMvc.perform(post("/api/report-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDocument)))
            .andExpect(status().isBadRequest());

        // Validate the ReportDocument in the database
        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReportDocument in Elasticsearch
        verify(mockReportDocumentSearchRepository, times(0)).save(reportDocument);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportDocumentRepository.findAll().size();
        // set the field null
        reportDocument.setName(null);

        // Create the ReportDocument, which fails.


        restReportDocumentMockMvc.perform(post("/api/report-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDocument)))
            .andExpect(status().isBadRequest());

        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportDocuments() throws Exception {
        // Initialize the database
        reportDocumentRepository.saveAndFlush(reportDocument);

        // Get all the reportDocumentList
        restReportDocumentMockMvc.perform(get("/api/report-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getReportDocument() throws Exception {
        // Initialize the database
        reportDocumentRepository.saveAndFlush(reportDocument);

        // Get the reportDocument
        restReportDocumentMockMvc.perform(get("/api/report-documents/{id}", reportDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportDocument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingReportDocument() throws Exception {
        // Get the reportDocument
        restReportDocumentMockMvc.perform(get("/api/report-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportDocument() throws Exception {
        // Initialize the database
        reportDocumentRepository.saveAndFlush(reportDocument);

        int databaseSizeBeforeUpdate = reportDocumentRepository.findAll().size();

        // Update the reportDocument
        ReportDocument updatedReportDocument = reportDocumentRepository.findById(reportDocument.getId()).get();
        // Disconnect from session so that the updates on updatedReportDocument are not directly saved in db
        em.detach(updatedReportDocument);
        updatedReportDocument
            .name(UPDATED_NAME);

        restReportDocumentMockMvc.perform(put("/api/report-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReportDocument)))
            .andExpect(status().isOk());

        // Validate the ReportDocument in the database
        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeUpdate);
        ReportDocument testReportDocument = reportDocumentList.get(reportDocumentList.size() - 1);
        assertThat(testReportDocument.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ReportDocument in Elasticsearch
        verify(mockReportDocumentSearchRepository, times(1)).save(testReportDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingReportDocument() throws Exception {
        int databaseSizeBeforeUpdate = reportDocumentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportDocumentMockMvc.perform(put("/api/report-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportDocument)))
            .andExpect(status().isBadRequest());

        // Validate the ReportDocument in the database
        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReportDocument in Elasticsearch
        verify(mockReportDocumentSearchRepository, times(0)).save(reportDocument);
    }

    @Test
    @Transactional
    public void deleteReportDocument() throws Exception {
        // Initialize the database
        reportDocumentRepository.saveAndFlush(reportDocument);

        int databaseSizeBeforeDelete = reportDocumentRepository.findAll().size();

        // Delete the reportDocument
        restReportDocumentMockMvc.perform(delete("/api/report-documents/{id}", reportDocument.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportDocument> reportDocumentList = reportDocumentRepository.findAll();
        assertThat(reportDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReportDocument in Elasticsearch
        verify(mockReportDocumentSearchRepository, times(1)).deleteById(reportDocument.getId());
    }

    @Test
    @Transactional
    public void searchReportDocument() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        reportDocumentRepository.saveAndFlush(reportDocument);
        when(mockReportDocumentSearchRepository.search(queryStringQuery("id:" + reportDocument.getId())))
            .thenReturn(Collections.singletonList(reportDocument));

        // Search the reportDocument
        restReportDocumentMockMvc.perform(get("/api/_search/report-documents?query=id:" + reportDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}