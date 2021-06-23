// package com.ec.proharvest.web.rest;

// import com.ec.proharvest.ReportsfactoryApp;
// import com.ec.proharvest.config.TestSecurityConfiguration;
// import com.ec.proharvest.domain.ReportType;
// import com.ec.proharvest.repository.ReportTypeRepository;
// import com.ec.proharvest.repository.search.ReportTypeSearchRepository;
// import com.ec.proharvest.service.ReportTypeService;
// import com.ec.proharvest.service.dto.ReportTypeDTO;
// import com.ec.proharvest.service.mapper.ReportTypeMapper;

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
//  * Integration tests for the {@link ReportTypeResource} REST controller.
//  */
// @SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
// @ExtendWith(MockitoExtension.class)
// @AutoConfigureMockMvc
// @WithMockUser
// public class ReportTypeResourceIT {

//     private static final String DEFAULT_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_NAME = "BBBBBBBBBB";

//     private static final String DEFAULT_TEMPLATE_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_TEMPLATE_NAME = "BBBBBBBBBB";

//     @Autowired
//     private ReportTypeRepository reportTypeRepository;

//     @Autowired
//     private ReportTypeMapper reportTypeMapper;

//     @Autowired
//     private ReportTypeService reportTypeService;

//     /**
//      * This repository is mocked in the com.ec.proharvest.repository.search test package.
//      *
//      * @see com.ec.proharvest.repository.search.ReportTypeSearchRepositoryMockConfiguration
//      */
//     @Autowired
//     private ReportTypeSearchRepository mockReportTypeSearchRepository;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restReportTypeMockMvc;

//     private ReportType reportType;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportType createEntity(EntityManager em) {
//         ReportType reportType = new ReportType()
//             .name(DEFAULT_NAME)
//             .templateName(DEFAULT_TEMPLATE_NAME);
//         return reportType;
//     }
//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportType createUpdatedEntity(EntityManager em) {
//         ReportType reportType = new ReportType()
//             .name(UPDATED_NAME)
//             .templateName(UPDATED_TEMPLATE_NAME);
//         return reportType;
//     }

//     @BeforeEach
//     public void initTest() {
//         reportType = createEntity(em);
//     }

//     @Test
//     @Transactional
//     public void createReportType() throws Exception {
//         int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();
//         // Create the ReportType
//         ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);
//         restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
//             .andExpect(status().isCreated());

//         // Validate the ReportType in the database
//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate + 1);
//         ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
//         assertThat(testReportType.getName()).isEqualTo(DEFAULT_NAME);
//         assertThat(testReportType.getTemplateName()).isEqualTo(DEFAULT_TEMPLATE_NAME);

//         // Validate the ReportType in Elasticsearch
//         verify(mockReportTypeSearchRepository, times(1)).save(testReportType);
//     }

//     @Test
//     @Transactional
//     public void createReportTypeWithExistingId() throws Exception {
//         int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();

//         // Create the ReportType with an existing ID
//         reportType.setId(1L);
//         ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportType in the database
//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate);

//         // Validate the ReportType in Elasticsearch
//         verify(mockReportTypeSearchRepository, times(0)).save(reportType);
//     }


//     @Test
//     @Transactional
//     public void checkNameIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportTypeRepository.findAll().size();
//         // set the field null
//         reportType.setName(null);

//         // Create the ReportType, which fails.
//         ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);


//         restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
//             .andExpect(status().isBadRequest());

//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void getAllReportTypes() throws Exception {
//         // Initialize the database
//         reportTypeRepository.saveAndFlush(reportType);

//         // Get all the reportTypeList
//         restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
//             .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//             .andExpect(jsonPath("$.[*].templateName").value(hasItem(DEFAULT_TEMPLATE_NAME)));
//     }
    
//     @Test
//     @Transactional
//     public void getReportType() throws Exception {
//         // Initialize the database
//         reportTypeRepository.saveAndFlush(reportType);

//         // Get the reportType
//         restReportTypeMockMvc.perform(get("/api/report-types/{id}", reportType.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(reportType.getId().intValue()))
//             .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//             .andExpect(jsonPath("$.templateName").value(DEFAULT_TEMPLATE_NAME));
//     }
//     @Test
//     @Transactional
//     public void getNonExistingReportType() throws Exception {
//         // Get the reportType
//         restReportTypeMockMvc.perform(get("/api/report-types/{id}", Long.MAX_VALUE))
//             .andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     public void updateReportType() throws Exception {
//         // Initialize the database
//         reportTypeRepository.saveAndFlush(reportType);

//         int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

//         // Update the reportType
//         ReportType updatedReportType = reportTypeRepository.findById(reportType.getId()).get();
//         // Disconnect from session so that the updates on updatedReportType are not directly saved in db
//         em.detach(updatedReportType);
//         updatedReportType
//             .name(UPDATED_NAME)
//             .templateName(UPDATED_TEMPLATE_NAME);
//         ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(updatedReportType);

//         restReportTypeMockMvc.perform(put("/api/report-types").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
//             .andExpect(status().isOk());

//         // Validate the ReportType in the database
//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);
//         ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
//         assertThat(testReportType.getName()).isEqualTo(UPDATED_NAME);
//         assertThat(testReportType.getTemplateName()).isEqualTo(UPDATED_TEMPLATE_NAME);

//         // Validate the ReportType in Elasticsearch
//         verify(mockReportTypeSearchRepository, times(1)).save(testReportType);
//     }

//     @Test
//     @Transactional
//     public void updateNonExistingReportType() throws Exception {
//         int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

//         // Create the ReportType
//         ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restReportTypeMockMvc.perform(put("/api/report-types").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportType in the database
//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);

//         // Validate the ReportType in Elasticsearch
//         verify(mockReportTypeSearchRepository, times(0)).save(reportType);
//     }

//     @Test
//     @Transactional
//     public void deleteReportType() throws Exception {
//         // Initialize the database
//         reportTypeRepository.saveAndFlush(reportType);

//         int databaseSizeBeforeDelete = reportTypeRepository.findAll().size();

//         // Delete the reportType
//         restReportTypeMockMvc.perform(delete("/api/report-types/{id}", reportType.getId()).with(csrf())
//             .accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ReportType> reportTypeList = reportTypeRepository.findAll();
//         assertThat(reportTypeList).hasSize(databaseSizeBeforeDelete - 1);

//         // Validate the ReportType in Elasticsearch
//         verify(mockReportTypeSearchRepository, times(1)).deleteById(reportType.getId());
//     }

//     @Test
//     @Transactional
//     public void searchReportType() throws Exception {
//         // Configure the mock search repository
//         // Initialize the database
//         reportTypeRepository.saveAndFlush(reportType);
//         when(mockReportTypeSearchRepository.search(queryStringQuery("id:" + reportType.getId()), PageRequest.of(0, 20)))
//             .thenReturn(new PageImpl<>(Collections.singletonList(reportType), PageRequest.of(0, 1), 1));

//         // Search the reportType
//         restReportTypeMockMvc.perform(get("/api/_search/report-types?query=id:" + reportType.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
//             .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//             .andExpect(jsonPath("$.[*].templateName").value(hasItem(DEFAULT_TEMPLATE_NAME)));
//     }
// }
