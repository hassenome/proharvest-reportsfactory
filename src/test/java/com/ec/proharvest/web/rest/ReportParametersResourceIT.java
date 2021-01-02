package com.ec.proharvest.web.rest;

import com.ec.proharvest.ReportsfactoryApp;
import com.ec.proharvest.config.TestSecurityConfiguration;
import com.ec.proharvest.domain.ReportParameters;
import com.ec.proharvest.repository.ReportParametersRepository;
import com.ec.proharvest.repository.search.ReportParametersSearchRepository;
import com.ec.proharvest.service.ReportParametersService;
import com.ec.proharvest.service.dto.ReportParametersDTO;
import com.ec.proharvest.service.mapper.ReportParametersMapper;

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
 * Integration tests for the {@link ReportParametersResource} REST controller.
 */
@SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportParametersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUES = "AAAAAAAAAA";
    private static final String UPDATED_VALUES = "BBBBBBBBBB";

    @Autowired
    private ReportParametersRepository reportParametersRepository;

    @Autowired
    private ReportParametersMapper reportParametersMapper;

    @Autowired
    private ReportParametersService reportParametersService;

    /**
     * This repository is mocked in the com.ec.proharvest.repository.search test package.
     *
     * @see com.ec.proharvest.repository.search.ReportParametersSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReportParametersSearchRepository mockReportParametersSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportParametersMockMvc;

    private ReportParameters reportParameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportParameters createEntity(EntityManager em) {
        ReportParameters reportParameters = new ReportParameters()
            .name(DEFAULT_NAME)
            .values(DEFAULT_VALUES);
        return reportParameters;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportParameters createUpdatedEntity(EntityManager em) {
        ReportParameters reportParameters = new ReportParameters()
            .name(UPDATED_NAME)
            .values(UPDATED_VALUES);
        return reportParameters;
    }

    @BeforeEach
    public void initTest() {
        reportParameters = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportParameters() throws Exception {
        int databaseSizeBeforeCreate = reportParametersRepository.findAll().size();
        // Create the ReportParameters
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(reportParameters);
        restReportParametersMockMvc.perform(post("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportParameters in the database
        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeCreate + 1);
        ReportParameters testReportParameters = reportParametersList.get(reportParametersList.size() - 1);
        assertThat(testReportParameters.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReportParameters.getValues()).isEqualTo(DEFAULT_VALUES);

        // Validate the ReportParameters in Elasticsearch
        verify(mockReportParametersSearchRepository, times(1)).save(testReportParameters);
    }

    @Test
    @Transactional
    public void createReportParametersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportParametersRepository.findAll().size();

        // Create the ReportParameters with an existing ID
        reportParameters.setId(1L);
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(reportParameters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportParametersMockMvc.perform(post("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportParameters in the database
        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReportParameters in Elasticsearch
        verify(mockReportParametersSearchRepository, times(0)).save(reportParameters);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportParametersRepository.findAll().size();
        // set the field null
        reportParameters.setName(null);

        // Create the ReportParameters, which fails.
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(reportParameters);


        restReportParametersMockMvc.perform(post("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isBadRequest());

        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValuesIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportParametersRepository.findAll().size();
        // set the field null
        reportParameters.setValues(null);

        // Create the ReportParameters, which fails.
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(reportParameters);


        restReportParametersMockMvc.perform(post("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isBadRequest());

        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportParameters() throws Exception {
        // Initialize the database
        reportParametersRepository.saveAndFlush(reportParameters);

        // Get all the reportParametersList
        restReportParametersMockMvc.perform(get("/api/report-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportParameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES)));
    }
    
    @Test
    @Transactional
    public void getReportParameters() throws Exception {
        // Initialize the database
        reportParametersRepository.saveAndFlush(reportParameters);

        // Get the reportParameters
        restReportParametersMockMvc.perform(get("/api/report-parameters/{id}", reportParameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportParameters.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.values").value(DEFAULT_VALUES));
    }
    @Test
    @Transactional
    public void getNonExistingReportParameters() throws Exception {
        // Get the reportParameters
        restReportParametersMockMvc.perform(get("/api/report-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportParameters() throws Exception {
        // Initialize the database
        reportParametersRepository.saveAndFlush(reportParameters);

        int databaseSizeBeforeUpdate = reportParametersRepository.findAll().size();

        // Update the reportParameters
        ReportParameters updatedReportParameters = reportParametersRepository.findById(reportParameters.getId()).get();
        // Disconnect from session so that the updates on updatedReportParameters are not directly saved in db
        em.detach(updatedReportParameters);
        updatedReportParameters
            .name(UPDATED_NAME)
            .values(UPDATED_VALUES);
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(updatedReportParameters);

        restReportParametersMockMvc.perform(put("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isOk());

        // Validate the ReportParameters in the database
        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeUpdate);
        ReportParameters testReportParameters = reportParametersList.get(reportParametersList.size() - 1);
        assertThat(testReportParameters.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportParameters.getValues()).isEqualTo(UPDATED_VALUES);

        // Validate the ReportParameters in Elasticsearch
        verify(mockReportParametersSearchRepository, times(1)).save(testReportParameters);
    }

    @Test
    @Transactional
    public void updateNonExistingReportParameters() throws Exception {
        int databaseSizeBeforeUpdate = reportParametersRepository.findAll().size();

        // Create the ReportParameters
        ReportParametersDTO reportParametersDTO = reportParametersMapper.toDto(reportParameters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportParametersMockMvc.perform(put("/api/report-parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportParametersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportParameters in the database
        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReportParameters in Elasticsearch
        verify(mockReportParametersSearchRepository, times(0)).save(reportParameters);
    }

    @Test
    @Transactional
    public void deleteReportParameters() throws Exception {
        // Initialize the database
        reportParametersRepository.saveAndFlush(reportParameters);

        int databaseSizeBeforeDelete = reportParametersRepository.findAll().size();

        // Delete the reportParameters
        restReportParametersMockMvc.perform(delete("/api/report-parameters/{id}", reportParameters.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportParameters> reportParametersList = reportParametersRepository.findAll();
        assertThat(reportParametersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReportParameters in Elasticsearch
        verify(mockReportParametersSearchRepository, times(1)).deleteById(reportParameters.getId());
    }

    @Test
    @Transactional
    public void searchReportParameters() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        reportParametersRepository.saveAndFlush(reportParameters);
        when(mockReportParametersSearchRepository.search(queryStringQuery("id:" + reportParameters.getId())))
            .thenReturn(Collections.singletonList(reportParameters));

        // Search the reportParameters
        restReportParametersMockMvc.perform(get("/api/_search/report-parameters?query=id:" + reportParameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportParameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES)));
    }
}
