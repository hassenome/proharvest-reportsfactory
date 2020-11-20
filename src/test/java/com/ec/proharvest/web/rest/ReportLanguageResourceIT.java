// package com.ec.proharvest.web.rest;

// import com.ec.proharvest.ReportsfactoryApp;
// import com.ec.proharvest.config.TestSecurityConfiguration;
// import com.ec.proharvest.domain.ReportLanguage;
// import com.ec.proharvest.repository.ReportLanguageRepository;
// import com.ec.proharvest.repository.search.ReportLanguageSearchRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
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
//  * Integration tests for the {@link ReportLanguageResource} REST controller.
//  */
// @SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
// @ExtendWith(MockitoExtension.class)
// @AutoConfigureMockMvc
// @WithMockUser
// public class ReportLanguageResourceIT {

//     private static final String DEFAULT_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_NAME = "BBBBBBBBBB";

//     private static final String DEFAULT_ABBREVIATION = "AAA";
//     private static final String UPDATED_ABBREVIATION = "BBB";

//     @Autowired
//     private ReportLanguageRepository reportLanguageRepository;

//     /**
//      * This repository is mocked in the com.ec.proharvest.repository.search test package.
//      *
//      * @see com.ec.proharvest.repository.search.ReportLanguageSearchRepositoryMockConfiguration
//      */
//     @Autowired
//     private ReportLanguageSearchRepository mockReportLanguageSearchRepository;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restReportLanguageMockMvc;

//     private ReportLanguage reportLanguage;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportLanguage createEntity(EntityManager em) {
//         ReportLanguage reportLanguage = new ReportLanguage()
//             .name(DEFAULT_NAME)
//             .abbreviation(DEFAULT_ABBREVIATION);
//         return reportLanguage;
//     }
//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportLanguage createUpdatedEntity(EntityManager em) {
//         ReportLanguage reportLanguage = new ReportLanguage()
//             .name(UPDATED_NAME)
//             .abbreviation(UPDATED_ABBREVIATION);
//         return reportLanguage;
//     }

//     @BeforeEach
//     public void initTest() {
//         reportLanguage = createEntity(em);
//     }

//     @Test
//     @Transactional
//     public void createReportLanguage() throws Exception {
//         int databaseSizeBeforeCreate = reportLanguageRepository.findAll().size();
//         // Create the ReportLanguage
//         restReportLanguageMockMvc.perform(post("/api/report-languages").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportLanguage)))
//             .andExpect(status().isCreated());

//         // Validate the ReportLanguage in the database
//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeCreate + 1);
//         ReportLanguage testReportLanguage = reportLanguageList.get(reportLanguageList.size() - 1);
//         assertThat(testReportLanguage.getName()).isEqualTo(DEFAULT_NAME);
//         assertThat(testReportLanguage.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);

//         // Validate the ReportLanguage in Elasticsearch
//         verify(mockReportLanguageSearchRepository, times(1)).save(testReportLanguage);
//     }

//     @Test
//     @Transactional
//     public void createReportLanguageWithExistingId() throws Exception {
//         int databaseSizeBeforeCreate = reportLanguageRepository.findAll().size();

//         // Create the ReportLanguage with an existing ID
//         reportLanguage.setId(1L);

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restReportLanguageMockMvc.perform(post("/api/report-languages").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportLanguage)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportLanguage in the database
//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeCreate);

//         // Validate the ReportLanguage in Elasticsearch
//         verify(mockReportLanguageSearchRepository, times(0)).save(reportLanguage);
//     }


//     @Test
//     @Transactional
//     public void checkNameIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportLanguageRepository.findAll().size();
//         // set the field null
//         reportLanguage.setName(null);

//         // Create the ReportLanguage, which fails.


//         restReportLanguageMockMvc.perform(post("/api/report-languages").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportLanguage)))
//             .andExpect(status().isBadRequest());

//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void getAllReportLanguages() throws Exception {
//         // Initialize the database
//         reportLanguageRepository.saveAndFlush(reportLanguage);

//         // Get all the reportLanguageList
//         restReportLanguageMockMvc.perform(get("/api/report-languages?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportLanguage.getId().intValue())))
//             .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//             .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
//     }
    
//     @Test
//     @Transactional
//     public void getReportLanguage() throws Exception {
//         // Initialize the database
//         reportLanguageRepository.saveAndFlush(reportLanguage);

//         // Get the reportLanguage
//         restReportLanguageMockMvc.perform(get("/api/report-languages/{id}", reportLanguage.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(reportLanguage.getId().intValue()))
//             .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//             .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
//     }
//     @Test
//     @Transactional
//     public void getNonExistingReportLanguage() throws Exception {
//         // Get the reportLanguage
//         restReportLanguageMockMvc.perform(get("/api/report-languages/{id}", Long.MAX_VALUE))
//             .andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     public void updateReportLanguage() throws Exception {
//         // Initialize the database
//         reportLanguageRepository.saveAndFlush(reportLanguage);

//         int databaseSizeBeforeUpdate = reportLanguageRepository.findAll().size();

//         // Update the reportLanguage
//         ReportLanguage updatedReportLanguage = reportLanguageRepository.findById(reportLanguage.getId()).get();
//         // Disconnect from session so that the updates on updatedReportLanguage are not directly saved in db
//         em.detach(updatedReportLanguage);
//         updatedReportLanguage
//             .name(UPDATED_NAME)
//             .abbreviation(UPDATED_ABBREVIATION);

//         restReportLanguageMockMvc.perform(put("/api/report-languages").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(updatedReportLanguage)))
//             .andExpect(status().isOk());

//         // Validate the ReportLanguage in the database
//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeUpdate);
//         ReportLanguage testReportLanguage = reportLanguageList.get(reportLanguageList.size() - 1);
//         assertThat(testReportLanguage.getName()).isEqualTo(UPDATED_NAME);
//         assertThat(testReportLanguage.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);

//         // Validate the ReportLanguage in Elasticsearch
//         verify(mockReportLanguageSearchRepository, times(1)).save(testReportLanguage);
//     }

//     @Test
//     @Transactional
//     public void updateNonExistingReportLanguage() throws Exception {
//         int databaseSizeBeforeUpdate = reportLanguageRepository.findAll().size();

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restReportLanguageMockMvc.perform(put("/api/report-languages").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportLanguage)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportLanguage in the database
//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeUpdate);

//         // Validate the ReportLanguage in Elasticsearch
//         verify(mockReportLanguageSearchRepository, times(0)).save(reportLanguage);
//     }

//     @Test
//     @Transactional
//     public void deleteReportLanguage() throws Exception {
//         // Initialize the database
//         reportLanguageRepository.saveAndFlush(reportLanguage);

//         int databaseSizeBeforeDelete = reportLanguageRepository.findAll().size();

//         // Delete the reportLanguage
//         restReportLanguageMockMvc.perform(delete("/api/report-languages/{id}", reportLanguage.getId()).with(csrf())
//             .accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ReportLanguage> reportLanguageList = reportLanguageRepository.findAll();
//         assertThat(reportLanguageList).hasSize(databaseSizeBeforeDelete - 1);

//         // Validate the ReportLanguage in Elasticsearch
//         verify(mockReportLanguageSearchRepository, times(1)).deleteById(reportLanguage.getId());
//     }

//     @Test
//     @Transactional
//     public void searchReportLanguage() throws Exception {
//         // Configure the mock search repository
//         // Initialize the database
//         reportLanguageRepository.saveAndFlush(reportLanguage);
//         when(mockReportLanguageSearchRepository.search(queryStringQuery("id:" + reportLanguage.getId()), PageRequest.of(0, 20)))
//             .thenReturn(new PageImpl<>(Collections.singletonList(reportLanguage), PageRequest.of(0, 1), 1));

//         // Search the reportLanguage
//         restReportLanguageMockMvc.perform(get("/api/_search/report-languages?query=id:" + reportLanguage.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportLanguage.getId().intValue())))
//             .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//             .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
//     }
// }
