// package com.ec.proharvest.web.rest;

// import com.ec.proharvest.ReportsfactoryApp;
// import com.ec.proharvest.config.TestSecurityConfiguration;
// import com.ec.proharvest.domain.ReportingData;
// import com.ec.proharvest.repository.ReportingDataRepository;
// import com.ec.proharvest.repository.search.ReportingDataSearchRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;
// import javax.persistence.EntityManager;
// import java.util.Collections;
// import java.util.List;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// /**
//  * Integration tests for the {@link ReportingDataResource} REST controller.
//  */
// @SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
// @ExtendWith(MockitoExtension.class)
// @AutoConfigureMockMvc
// @WithMockUser
// public class ReportingDataResourceIT {

//     private static final String DEFAULT_DATA_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_DATA_NAME = "BBBBBBBBBB";

//     // private static final String DEFAULT_DATA_SET = "AAAAAAAAAA";
//     private static final String UPDATED_DATA_SET = "BBBBBBBBBB";

//     @Autowired
//     private ReportingDataRepository reportingDataRepository;

//     /**
//      * This repository is mocked in the com.ec.proharvest.repository.search test package.
//      *
//      * @see com.ec.proharvest.repository.search.ReportingDataSearchRepositoryMockConfiguration
//      */
//     @Autowired
//     private ReportingDataSearchRepository mockReportingDataSearchRepository;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restReportingDataMockMvc;

//     private ReportingData reportingData;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     // public static ReportingData createEntity(EntityManager em) {
//     //     ReportingData reportingData = new ReportingData()
//     //         .dataName(DEFAULT_DATA_NAME)
//     //         .dataSet(DEFAULT_DATA_SET);
//     //     return reportingData;
//     // }
//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportingData createUpdatedEntity(EntityManager em) {
//         ReportingData reportingData = new ReportingData()
//             .dataName(UPDATED_DATA_NAME)
//             .dataSet(UPDATED_DATA_SET);
//         return reportingData;
//     }

//     @BeforeEach
//     public void initTest() {
//         reportingData = createEntity(em);
//     }

//     @Test
//     @Transactional
//     public void createReportingData() throws Exception {
//         int databaseSizeBeforeCreate = reportingDataRepository.findAll().size();
//         // Create the ReportingData
//         restReportingDataMockMvc.perform(post("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportingData)))
//             .andExpect(status().isCreated());

//         // Validate the ReportingData in the database
//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeCreate + 1);
//         ReportingData testReportingData = reportingDataList.get(reportingDataList.size() - 1);
//         assertThat(testReportingData.getDataName()).isEqualTo(DEFAULT_DATA_NAME);
//         assertThat(testReportingData.getDataSet()).isEqualTo(DEFAULT_DATA_SET);

//         // Validate the ReportingData in Elasticsearch
//         verify(mockReportingDataSearchRepository, times(1)).save(testReportingData);
//     }

//     @Test
//     @Transactional
//     public void createReportingDataWithExistingId() throws Exception {
//         int databaseSizeBeforeCreate = reportingDataRepository.findAll().size();

//         // Create the ReportingData with an existing ID
//         reportingData.setId(1L);

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restReportingDataMockMvc.perform(post("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportingData)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportingData in the database
//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeCreate);

//         // Validate the ReportingData in Elasticsearch
//         verify(mockReportingDataSearchRepository, times(0)).save(reportingData);
//     }


//     @Test
//     @Transactional
//     public void checkDataNameIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportingDataRepository.findAll().size();
//         // set the field null
//         reportingData.setDataName(null);

//         // Create the ReportingData, which fails.


//         restReportingDataMockMvc.perform(post("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportingData)))
//             .andExpect(status().isBadRequest());

//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void checkDataSetIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportingDataRepository.findAll().size();
//         // set the field null
//         reportingData.setDataSet(null);

//         // Create the ReportingData, which fails.


//         restReportingDataMockMvc.perform(post("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportingData)))
//             .andExpect(status().isBadRequest());

//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void getAllReportingData() throws Exception {
//         // Initialize the database
//         reportingDataRepository.saveAndFlush(reportingData);

//         // Get all the reportingDataList
//         restReportingDataMockMvc.perform(get("/api/reporting-data?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportingData.getId().intValue())))
//             .andExpect(jsonPath("$.[*].dataName").value(hasItem(DEFAULT_DATA_NAME)))
//             .andExpect(jsonPath("$.[*].dataSet").value(hasItem(DEFAULT_DATA_SET)));
//     }
    
//     @Test
//     @Transactional
//     public void getReportingData() throws Exception {
//         // Initialize the database
//         reportingDataRepository.saveAndFlush(reportingData);

//         // Get the reportingData
//         restReportingDataMockMvc.perform(get("/api/reporting-data/{id}", reportingData.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(reportingData.getId().intValue()))
//             .andExpect(jsonPath("$.dataName").value(DEFAULT_DATA_NAME))
//             .andExpect(jsonPath("$.dataSet").value(DEFAULT_DATA_SET));
//     }
//     @Test
//     @Transactional
//     public void getNonExistingReportingData() throws Exception {
//         // Get the reportingData
//         restReportingDataMockMvc.perform(get("/api/reporting-data/{id}", Long.MAX_VALUE))
//             .andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     public void updateReportingData() throws Exception {
//         // Initialize the database
//         reportingDataRepository.saveAndFlush(reportingData);

//         int databaseSizeBeforeUpdate = reportingDataRepository.findAll().size();

//         // Update the reportingData
//         ReportingData updatedReportingData = reportingDataRepository.findById(reportingData.getId()).get();
//         // Disconnect from session so that the updates on updatedReportingData are not directly saved in db
//         em.detach(updatedReportingData);
//         updatedReportingData
//             .dataName(UPDATED_DATA_NAME)
//             .dataSet(UPDATED_DATA_SET);

//         restReportingDataMockMvc.perform(put("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(updatedReportingData)))
//             .andExpect(status().isOk());

//         // Validate the ReportingData in the database
//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeUpdate);
//         ReportingData testReportingData = reportingDataList.get(reportingDataList.size() - 1);
//         assertThat(testReportingData.getDataName()).isEqualTo(UPDATED_DATA_NAME);
//         assertThat(testReportingData.getDataSet()).isEqualTo(UPDATED_DATA_SET);

//         // Validate the ReportingData in Elasticsearch
//         verify(mockReportingDataSearchRepository, times(1)).save(testReportingData);
//     }

//     @Test
//     @Transactional
//     public void updateNonExistingReportingData() throws Exception {
//         int databaseSizeBeforeUpdate = reportingDataRepository.findAll().size();

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restReportingDataMockMvc.perform(put("/api/reporting-data").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportingData)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportingData in the database
//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeUpdate);

//         // Validate the ReportingData in Elasticsearch
//         verify(mockReportingDataSearchRepository, times(0)).save(reportingData);
//     }

//     @Test
//     @Transactional
//     public void deleteReportingData() throws Exception {
//         // Initialize the database
//         reportingDataRepository.saveAndFlush(reportingData);

//         int databaseSizeBeforeDelete = reportingDataRepository.findAll().size();

//         // Delete the reportingData
//         restReportingDataMockMvc.perform(delete("/api/reporting-data/{id}", reportingData.getId()).with(csrf())
//             .accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ReportingData> reportingDataList = reportingDataRepository.findAll();
//         assertThat(reportingDataList).hasSize(databaseSizeBeforeDelete - 1);

//         // Validate the ReportingData in Elasticsearch
//         verify(mockReportingDataSearchRepository, times(1)).deleteById(reportingData.getId());
//     }

//     @Test
//     @Transactional
//     public void searchReportingData() throws Exception {
//         // Configure the mock search repository
//         // Initialize the database
//         reportingDataRepository.saveAndFlush(reportingData);
//         when(mockReportingDataSearchRepository.search(queryStringQuery("id:" + reportingData.getId())))
//             .thenReturn(Collections.singletonList(reportingData));

//         // Search the reportingData
//         restReportingDataMockMvc.perform(get("/api/_search/reporting-data?query=id:" + reportingData.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportingData.getId().intValue())))
//             .andExpect(jsonPath("$.[*].dataName").value(hasItem(DEFAULT_DATA_NAME)))
//             .andExpect(jsonPath("$.[*].dataSet").value(hasItem(DEFAULT_DATA_SET)));
//     }
// }
