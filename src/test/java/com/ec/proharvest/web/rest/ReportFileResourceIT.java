// package com.ec.proharvest.web.rest;

// import com.ec.proharvest.ReportsfactoryApp;
// import com.ec.proharvest.config.TestSecurityConfiguration;
// import com.ec.proharvest.domain.ReportFile;
// import com.ec.proharvest.domain.ReportDocument;
// import com.ec.proharvest.repository.ReportFileRepository;
// import com.ec.proharvest.repository.search.ReportFileSearchRepository;

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
// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
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
//  * Integration tests for the {@link ReportFileResource} REST controller.
//  */
// @SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
// @ExtendWith(MockitoExtension.class)
// @AutoConfigureMockMvc
// @WithMockUser
// public class ReportFileResourceIT {

//     private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

//     private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
//     private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

//     private static final Instant DEFAULT_MODIFIED = Instant.ofEpochMilli(0L);
//     private static final Instant UPDATED_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

//     private static final String DEFAULT_TAGS = "AAAAAAAAAA";
//     private static final String UPDATED_TAGS = "BBBBBBBBBB";

//     private static final String DEFAULT_PATH = "AAAAAAAAAA";
//     private static final String UPDATED_PATH = "BBBBBBBBBB";

//     @Autowired
//     private ReportFileRepository reportFileRepository;

//     /**
//      * This repository is mocked in the com.ec.proharvest.repository.search test package.
//      *
//      * @see com.ec.proharvest.repository.search.ReportFileSearchRepositoryMockConfiguration
//      */
//     @Autowired
//     private ReportFileSearchRepository mockReportFileSearchRepository;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restReportFileMockMvc;

//     private ReportFile reportFile;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportFile createEntity(EntityManager em) {
//         ReportFile reportFile = new ReportFile()
//             .fileName(DEFAULT_FILE_NAME)
//             .created(DEFAULT_CREATED)
//             .modified(DEFAULT_MODIFIED)
//             .tags(DEFAULT_TAGS)
//             .path(DEFAULT_PATH);
//         // Add required entity
//         ReportDocument reportDocument;
//         if (TestUtil.findAll(em, ReportDocument.class).isEmpty()) {
//             reportDocument = ReportDocumentResourceIT.createEntity(em);
//             em.persist(reportDocument);
//             em.flush();
//         } else {
//             reportDocument = TestUtil.findAll(em, ReportDocument.class).get(0);
//         }
//         reportFile.setReportDocument(reportDocument);
//         return reportFile;
//     }
//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ReportFile createUpdatedEntity(EntityManager em) {
//         ReportFile reportFile = new ReportFile()
//             .fileName(UPDATED_FILE_NAME)
//             .created(UPDATED_CREATED)
//             .modified(UPDATED_MODIFIED)
//             .tags(UPDATED_TAGS)
//             .path(UPDATED_PATH);
//         // Add required entity
//         ReportDocument reportDocument;
//         if (TestUtil.findAll(em, ReportDocument.class).isEmpty()) {
//             reportDocument = ReportDocumentResourceIT.createUpdatedEntity(em);
//             em.persist(reportDocument);
//             em.flush();
//         } else {
//             reportDocument = TestUtil.findAll(em, ReportDocument.class).get(0);
//         }
//         reportFile.setReportDocument(reportDocument);
//         return reportFile;
//     }

//     @BeforeEach
//     public void initTest() {
//         reportFile = createEntity(em);
//     }

//     @Test
//     @Transactional
//     public void createReportFile() throws Exception {
//         int databaseSizeBeforeCreate = reportFileRepository.findAll().size();
//         // Create the ReportFile
//         restReportFileMockMvc.perform(post("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportFile)))
//             .andExpect(status().isCreated());

//         // Validate the ReportFile in the database
//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeCreate + 1);
//         ReportFile testReportFile = reportFileList.get(reportFileList.size() - 1);
//         assertThat(testReportFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
//         assertThat(testReportFile.getCreated()).isEqualTo(DEFAULT_CREATED);
//         assertThat(testReportFile.getModified()).isEqualTo(DEFAULT_MODIFIED);
//         assertThat(testReportFile.getTags()).isEqualTo(DEFAULT_TAGS);
//         assertThat(testReportFile.getPath()).isEqualTo(DEFAULT_PATH);

//         // Validate the ReportFile in Elasticsearch
//         verify(mockReportFileSearchRepository, times(1)).save(testReportFile);
//     }

//     @Test
//     @Transactional
//     public void createReportFileWithExistingId() throws Exception {
//         int databaseSizeBeforeCreate = reportFileRepository.findAll().size();

//         // Create the ReportFile with an existing ID
//         reportFile.setId(1L);

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restReportFileMockMvc.perform(post("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportFile)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportFile in the database
//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeCreate);

//         // Validate the ReportFile in Elasticsearch
//         verify(mockReportFileSearchRepository, times(0)).save(reportFile);
//     }


//     @Test
//     @Transactional
//     public void checkFileNameIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportFileRepository.findAll().size();
//         // set the field null
//         reportFile.setFileName(null);

//         // Create the ReportFile, which fails.


//         restReportFileMockMvc.perform(post("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportFile)))
//             .andExpect(status().isBadRequest());

//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void checkCreatedIsRequired() throws Exception {
//         int databaseSizeBeforeTest = reportFileRepository.findAll().size();
//         // set the field null
//         reportFile.setCreated(null);

//         // Create the ReportFile, which fails.


//         restReportFileMockMvc.perform(post("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportFile)))
//             .andExpect(status().isBadRequest());

//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeTest);
//     }

//     @Test
//     @Transactional
//     public void getAllReportFiles() throws Exception {
//         // Initialize the database
//         reportFileRepository.saveAndFlush(reportFile);

//         // Get all the reportFileList
//         restReportFileMockMvc.perform(get("/api/report-files?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportFile.getId().intValue())))
//             .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
//             .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
//             .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
//             .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
//             .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
//     }
    
//     @Test
//     @Transactional
//     public void getReportFile() throws Exception {
//         // Initialize the database
//         reportFileRepository.saveAndFlush(reportFile);

//         // Get the reportFile
//         restReportFileMockMvc.perform(get("/api/report-files/{id}", reportFile.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(reportFile.getId().intValue()))
//             .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
//             .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
//             .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()))
//             .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
//             .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
//     }
//     @Test
//     @Transactional
//     public void getNonExistingReportFile() throws Exception {
//         // Get the reportFile
//         restReportFileMockMvc.perform(get("/api/report-files/{id}", Long.MAX_VALUE))
//             .andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     public void updateReportFile() throws Exception {
//         // Initialize the database
//         reportFileRepository.saveAndFlush(reportFile);

//         int databaseSizeBeforeUpdate = reportFileRepository.findAll().size();

//         // Update the reportFile
//         ReportFile updatedReportFile = reportFileRepository.findById(reportFile.getId()).get();
//         // Disconnect from session so that the updates on updatedReportFile are not directly saved in db
//         em.detach(updatedReportFile);
//         updatedReportFile
//             .fileName(UPDATED_FILE_NAME)
//             .created(UPDATED_CREATED)
//             .modified(UPDATED_MODIFIED)
//             .tags(UPDATED_TAGS)
//             .path(UPDATED_PATH);

//         restReportFileMockMvc.perform(put("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(updatedReportFile)))
//             .andExpect(status().isOk());

//         // Validate the ReportFile in the database
//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeUpdate);
//         ReportFile testReportFile = reportFileList.get(reportFileList.size() - 1);
//         assertThat(testReportFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
//         assertThat(testReportFile.getCreated()).isEqualTo(UPDATED_CREATED);
//         assertThat(testReportFile.getModified()).isEqualTo(UPDATED_MODIFIED);
//         assertThat(testReportFile.getTags()).isEqualTo(UPDATED_TAGS);
//         assertThat(testReportFile.getPath()).isEqualTo(UPDATED_PATH);

//         // Validate the ReportFile in Elasticsearch
//         verify(mockReportFileSearchRepository, times(1)).save(testReportFile);
//     }

//     @Test
//     @Transactional
//     public void updateNonExistingReportFile() throws Exception {
//         int databaseSizeBeforeUpdate = reportFileRepository.findAll().size();

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restReportFileMockMvc.perform(put("/api/report-files").with(csrf())
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(TestUtil.convertObjectToJsonBytes(reportFile)))
//             .andExpect(status().isBadRequest());

//         // Validate the ReportFile in the database
//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeUpdate);

//         // Validate the ReportFile in Elasticsearch
//         verify(mockReportFileSearchRepository, times(0)).save(reportFile);
//     }

//     @Test
//     @Transactional
//     public void deleteReportFile() throws Exception {
//         // Initialize the database
//         reportFileRepository.saveAndFlush(reportFile);

//         int databaseSizeBeforeDelete = reportFileRepository.findAll().size();

//         // Delete the reportFile
//         restReportFileMockMvc.perform(delete("/api/report-files/{id}", reportFile.getId()).with(csrf())
//             .accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ReportFile> reportFileList = reportFileRepository.findAll();
//         assertThat(reportFileList).hasSize(databaseSizeBeforeDelete - 1);

//         // Validate the ReportFile in Elasticsearch
//         verify(mockReportFileSearchRepository, times(1)).deleteById(reportFile.getId());
//     }

//     @Test
//     @Transactional
//     public void searchReportFile() throws Exception {
//         // Configure the mock search repository
//         // Initialize the database
//         reportFileRepository.saveAndFlush(reportFile);
//         when(mockReportFileSearchRepository.search(queryStringQuery("id:" + reportFile.getId()), PageRequest.of(0, 20)))
//             .thenReturn(new PageImpl<>(Collections.singletonList(reportFile), PageRequest.of(0, 1), 1));

//         // Search the reportFile
//         restReportFileMockMvc.perform(get("/api/_search/report-files?query=id:" + reportFile.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(reportFile.getId().intValue())))
//             .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
//             .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
//             .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
//             .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
//             .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
//     }
// }
