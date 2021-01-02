package com.ec.proharvest.web.rest;

import com.ec.proharvest.ReportsfactoryApp;
import com.ec.proharvest.config.TestSecurityConfiguration;
import com.ec.proharvest.domain.ReportingDataSet;
import com.ec.proharvest.repository.ReportingDataSetRepository;
import com.ec.proharvest.repository.search.ReportingDataSetSearchRepository;
import com.ec.proharvest.service.ReportingDataSetService;
import com.ec.proharvest.service.dto.ReportingDataSetDTO;
import com.ec.proharvest.service.mapper.ReportingDataSetMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * Integration tests for the {@link ReportingDataSetResource} REST controller.
 */
@SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportingDataSetResourceIT {

    private static final String DEFAULT_DATA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SET = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SET = "BBBBBBBBBB";

    @Autowired
    private ReportingDataSetRepository reportingDataSetRepository;

    @Autowired
    private ReportingDataSetMapper reportingDataSetMapper;

    @Autowired
    private ReportingDataSetService reportingDataSetService;

    /**
     * This repository is mocked in the com.ec.proharvest.repository.search test package.
     *
     * @see com.ec.proharvest.repository.search.ReportingDataSetSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReportingDataSetSearchRepository mockReportingDataSetSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportingDataSetMockMvc;

    private ReportingDataSet reportingDataSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportingDataSet createEntity(EntityManager em) {
        ReportingDataSet reportingDataSet = new ReportingDataSet()
            .dataName(DEFAULT_DATA_NAME)
            .dataSet(DEFAULT_DATA_SET);
        return reportingDataSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportingDataSet createUpdatedEntity(EntityManager em) {
        ReportingDataSet reportingDataSet = new ReportingDataSet()
            .dataName(UPDATED_DATA_NAME)
            .dataSet(UPDATED_DATA_SET);
        return reportingDataSet;
    }

    @BeforeEach
    public void initTest() {
        reportingDataSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportingDataSet() throws Exception {
        int databaseSizeBeforeCreate = reportingDataSetRepository.findAll().size();
        // Create the ReportingDataSet
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(reportingDataSet);
        restReportingDataSetMockMvc.perform(post("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportingDataSet in the database
        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeCreate + 1);
        ReportingDataSet testReportingDataSet = reportingDataSetList.get(reportingDataSetList.size() - 1);
        assertThat(testReportingDataSet.getDataName()).isEqualTo(DEFAULT_DATA_NAME);
        assertThat(testReportingDataSet.getDataSet()).isEqualTo(DEFAULT_DATA_SET);

        // Validate the ReportingDataSet in Elasticsearch
        verify(mockReportingDataSetSearchRepository, times(1)).save(testReportingDataSet);
    }

    @Test
    @Transactional
    public void createReportingDataSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportingDataSetRepository.findAll().size();

        // Create the ReportingDataSet with an existing ID
        reportingDataSet.setId(1L);
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(reportingDataSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportingDataSetMockMvc.perform(post("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportingDataSet in the database
        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReportingDataSet in Elasticsearch
        verify(mockReportingDataSetSearchRepository, times(0)).save(reportingDataSet);
    }


    @Test
    @Transactional
    public void checkDataNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportingDataSetRepository.findAll().size();
        // set the field null
        reportingDataSet.setDataName(null);

        // Create the ReportingDataSet, which fails.
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(reportingDataSet);


        restReportingDataSetMockMvc.perform(post("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isBadRequest());

        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataSetIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportingDataSetRepository.findAll().size();
        // set the field null
        reportingDataSet.setDataSet(null);

        // Create the ReportingDataSet, which fails.
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(reportingDataSet);


        restReportingDataSetMockMvc.perform(post("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isBadRequest());

        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportingDataSets() throws Exception {
        // Initialize the database
        reportingDataSetRepository.saveAndFlush(reportingDataSet);

        // Get all the reportingDataSetList
        restReportingDataSetMockMvc.perform(get("/api/reporting-data-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportingDataSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataName").value(hasItem(DEFAULT_DATA_NAME)))
            .andExpect(jsonPath("$.[*].dataSet").value(hasItem(DEFAULT_DATA_SET)));
    }
    
    @Test
    @Transactional
    public void getReportingDataSet() throws Exception {
        // Initialize the database
        reportingDataSetRepository.saveAndFlush(reportingDataSet);

        // Get the reportingDataSet
        restReportingDataSetMockMvc.perform(get("/api/reporting-data-sets/{id}", reportingDataSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportingDataSet.getId().intValue()))
            .andExpect(jsonPath("$.dataName").value(DEFAULT_DATA_NAME))
            .andExpect(jsonPath("$.dataSet").value(DEFAULT_DATA_SET));
    }
    @Test
    @Transactional
    public void getNonExistingReportingDataSet() throws Exception {
        // Get the reportingDataSet
        restReportingDataSetMockMvc.perform(get("/api/reporting-data-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportingDataSet() throws Exception {
        // Initialize the database
        reportingDataSetRepository.saveAndFlush(reportingDataSet);

        int databaseSizeBeforeUpdate = reportingDataSetRepository.findAll().size();

        // Update the reportingDataSet
        ReportingDataSet updatedReportingDataSet = reportingDataSetRepository.findById(reportingDataSet.getId()).get();
        // Disconnect from session so that the updates on updatedReportingDataSet are not directly saved in db
        em.detach(updatedReportingDataSet);
        updatedReportingDataSet
            .dataName(UPDATED_DATA_NAME)
            .dataSet(UPDATED_DATA_SET);
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(updatedReportingDataSet);

        restReportingDataSetMockMvc.perform(put("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isOk());

        // Validate the ReportingDataSet in the database
        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeUpdate);
        ReportingDataSet testReportingDataSet = reportingDataSetList.get(reportingDataSetList.size() - 1);
        assertThat(testReportingDataSet.getDataName()).isEqualTo(UPDATED_DATA_NAME);
        assertThat(testReportingDataSet.getDataSet()).isEqualTo(UPDATED_DATA_SET);

        // Validate the ReportingDataSet in Elasticsearch
        verify(mockReportingDataSetSearchRepository, times(1)).save(testReportingDataSet);
    }

    @Test
    @Transactional
    public void updateNonExistingReportingDataSet() throws Exception {
        int databaseSizeBeforeUpdate = reportingDataSetRepository.findAll().size();

        // Create the ReportingDataSet
        ReportingDataSetDTO reportingDataSetDTO = reportingDataSetMapper.toDto(reportingDataSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportingDataSetMockMvc.perform(put("/api/reporting-data-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportingDataSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportingDataSet in the database
        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReportingDataSet in Elasticsearch
        verify(mockReportingDataSetSearchRepository, times(0)).save(reportingDataSet);
    }

    @Test
    @Transactional
    public void deleteReportingDataSet() throws Exception {
        // Initialize the database
        reportingDataSetRepository.saveAndFlush(reportingDataSet);

        int databaseSizeBeforeDelete = reportingDataSetRepository.findAll().size();

        // Delete the reportingDataSet
        restReportingDataSetMockMvc.perform(delete("/api/reporting-data-sets/{id}", reportingDataSet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportingDataSet> reportingDataSetList = reportingDataSetRepository.findAll();
        assertThat(reportingDataSetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReportingDataSet in Elasticsearch
        verify(mockReportingDataSetSearchRepository, times(1)).deleteById(reportingDataSet.getId());
    }

    @Test
    @Transactional
    public void searchReportingDataSet() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        reportingDataSetRepository.saveAndFlush(reportingDataSet);
        when(mockReportingDataSetSearchRepository.search(queryStringQuery("id:" + reportingDataSet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reportingDataSet), PageRequest.of(0, 1), 1));

        // Search the reportingDataSet
        restReportingDataSetMockMvc.perform(get("/api/_search/reporting-data-sets?query=id:" + reportingDataSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportingDataSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataName").value(hasItem(DEFAULT_DATA_NAME)))
            .andExpect(jsonPath("$.[*].dataSet").value(hasItem(DEFAULT_DATA_SET)));
    }
}
