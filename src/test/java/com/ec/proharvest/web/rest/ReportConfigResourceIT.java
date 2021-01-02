package com.ec.proharvest.web.rest;

import com.ec.proharvest.ReportsfactoryApp;
import com.ec.proharvest.config.TestSecurityConfiguration;
import com.ec.proharvest.domain.ReportConfig;
import com.ec.proharvest.repository.ReportConfigRepository;
import com.ec.proharvest.repository.search.ReportConfigSearchRepository;
import com.ec.proharvest.service.ReportConfigService;
import com.ec.proharvest.service.dto.ReportConfigDTO;
import com.ec.proharvest.service.mapper.ReportConfigMapper;

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

import com.ec.proharvest.domain.enumeration.FileType;
import com.ec.proharvest.domain.enumeration.StorageType;
/**
 * Integration tests for the {@link ReportConfigResource} REST controller.
 */
@SpringBootTest(classes = { ReportsfactoryApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportConfigResourceIT {

    private static final FileType DEFAULT_FILE_TYPE = FileType.PDF;
    private static final FileType UPDATED_FILE_TYPE = FileType.HTML;

    private static final StorageType DEFAULT_STORAGE_TYPE = StorageType.OP;
    private static final StorageType UPDATED_STORAGE_TYPE = StorageType.S3;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PERMISSIONS_HINT = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PERMISSIONS_HINT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SIZE_PAGE_TO_CONTENT = false;
    private static final Boolean UPDATED_SIZE_PAGE_TO_CONTENT = true;

    private static final Boolean DEFAULT_IGNORE_HYPERLINK = false;
    private static final Boolean UPDATED_IGNORE_HYPERLINK = true;

    private static final Boolean DEFAULT_FORCE_LINE_BREAK_POLICY = false;
    private static final Boolean UPDATED_FORCE_LINE_BREAK_POLICY = true;

    private static final Boolean DEFAULT_IS_COMPRESSED = false;
    private static final Boolean UPDATED_IS_COMPRESSED = true;

    private static final Boolean DEFAULT_ENCRYPTED = false;
    private static final Boolean UPDATED_ENCRYPTED = true;

    private static final String DEFAULT_OWNER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_USER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_USER_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private ReportConfigRepository reportConfigRepository;

    @Autowired
    private ReportConfigMapper reportConfigMapper;

    @Autowired
    private ReportConfigService reportConfigService;

    /**
     * This repository is mocked in the com.ec.proharvest.repository.search test package.
     *
     * @see com.ec.proharvest.repository.search.ReportConfigSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReportConfigSearchRepository mockReportConfigSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportConfigMockMvc;

    private ReportConfig reportConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportConfig createEntity(EntityManager em) {
        ReportConfig reportConfig = new ReportConfig()
            .fileType(DEFAULT_FILE_TYPE)
            .storageType(DEFAULT_STORAGE_TYPE)
            .name(DEFAULT_NAME)
            .prefix(DEFAULT_PREFIX)
            .suffix(DEFAULT_SUFFIX)
            .author(DEFAULT_AUTHOR)
            .allowedPermissionsHint(DEFAULT_ALLOWED_PERMISSIONS_HINT)
            .sizePageToContent(DEFAULT_SIZE_PAGE_TO_CONTENT)
            .ignoreHyperlink(DEFAULT_IGNORE_HYPERLINK)
            .forceLineBreakPolicy(DEFAULT_FORCE_LINE_BREAK_POLICY)
            .isCompressed(DEFAULT_IS_COMPRESSED)
            .encrypted(DEFAULT_ENCRYPTED)
            .ownerPassword(DEFAULT_OWNER_PASSWORD)
            .userPassword(DEFAULT_USER_PASSWORD);
        return reportConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportConfig createUpdatedEntity(EntityManager em) {
        ReportConfig reportConfig = new ReportConfig()
            .fileType(UPDATED_FILE_TYPE)
            .storageType(UPDATED_STORAGE_TYPE)
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .suffix(UPDATED_SUFFIX)
            .author(UPDATED_AUTHOR)
            .allowedPermissionsHint(UPDATED_ALLOWED_PERMISSIONS_HINT)
            .sizePageToContent(UPDATED_SIZE_PAGE_TO_CONTENT)
            .ignoreHyperlink(UPDATED_IGNORE_HYPERLINK)
            .forceLineBreakPolicy(UPDATED_FORCE_LINE_BREAK_POLICY)
            .isCompressed(UPDATED_IS_COMPRESSED)
            .encrypted(UPDATED_ENCRYPTED)
            .ownerPassword(UPDATED_OWNER_PASSWORD)
            .userPassword(UPDATED_USER_PASSWORD);
        return reportConfig;
    }

    @BeforeEach
    public void initTest() {
        reportConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportConfig() throws Exception {
        int databaseSizeBeforeCreate = reportConfigRepository.findAll().size();
        // Create the ReportConfig
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(reportConfig);
        restReportConfigMockMvc.perform(post("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportConfig in the database
        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ReportConfig testReportConfig = reportConfigList.get(reportConfigList.size() - 1);
        assertThat(testReportConfig.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testReportConfig.getStorageType()).isEqualTo(DEFAULT_STORAGE_TYPE);
        assertThat(testReportConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReportConfig.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testReportConfig.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testReportConfig.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testReportConfig.getAllowedPermissionsHint()).isEqualTo(DEFAULT_ALLOWED_PERMISSIONS_HINT);
        assertThat(testReportConfig.isSizePageToContent()).isEqualTo(DEFAULT_SIZE_PAGE_TO_CONTENT);
        assertThat(testReportConfig.isIgnoreHyperlink()).isEqualTo(DEFAULT_IGNORE_HYPERLINK);
        assertThat(testReportConfig.isForceLineBreakPolicy()).isEqualTo(DEFAULT_FORCE_LINE_BREAK_POLICY);
        assertThat(testReportConfig.isIsCompressed()).isEqualTo(DEFAULT_IS_COMPRESSED);
        assertThat(testReportConfig.isEncrypted()).isEqualTo(DEFAULT_ENCRYPTED);
        assertThat(testReportConfig.getOwnerPassword()).isEqualTo(DEFAULT_OWNER_PASSWORD);
        assertThat(testReportConfig.getUserPassword()).isEqualTo(DEFAULT_USER_PASSWORD);

        // Validate the ReportConfig in Elasticsearch
        verify(mockReportConfigSearchRepository, times(1)).save(testReportConfig);
    }

    @Test
    @Transactional
    public void createReportConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportConfigRepository.findAll().size();

        // Create the ReportConfig with an existing ID
        reportConfig.setId(1L);
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(reportConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportConfigMockMvc.perform(post("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportConfig in the database
        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReportConfig in Elasticsearch
        verify(mockReportConfigSearchRepository, times(0)).save(reportConfig);
    }


    @Test
    @Transactional
    public void checkFileTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportConfigRepository.findAll().size();
        // set the field null
        reportConfig.setFileType(null);

        // Create the ReportConfig, which fails.
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(reportConfig);


        restReportConfigMockMvc.perform(post("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportConfigRepository.findAll().size();
        // set the field null
        reportConfig.setName(null);

        // Create the ReportConfig, which fails.
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(reportConfig);


        restReportConfigMockMvc.perform(post("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportConfigs() throws Exception {
        // Initialize the database
        reportConfigRepository.saveAndFlush(reportConfig);

        // Get all the reportConfigList
        restReportConfigMockMvc.perform(get("/api/report-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].storageType").value(hasItem(DEFAULT_STORAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].allowedPermissionsHint").value(hasItem(DEFAULT_ALLOWED_PERMISSIONS_HINT)))
            .andExpect(jsonPath("$.[*].sizePageToContent").value(hasItem(DEFAULT_SIZE_PAGE_TO_CONTENT.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreHyperlink").value(hasItem(DEFAULT_IGNORE_HYPERLINK.booleanValue())))
            .andExpect(jsonPath("$.[*].forceLineBreakPolicy").value(hasItem(DEFAULT_FORCE_LINE_BREAK_POLICY.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompressed").value(hasItem(DEFAULT_IS_COMPRESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].encrypted").value(hasItem(DEFAULT_ENCRYPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].ownerPassword").value(hasItem(DEFAULT_OWNER_PASSWORD)))
            .andExpect(jsonPath("$.[*].userPassword").value(hasItem(DEFAULT_USER_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getReportConfig() throws Exception {
        // Initialize the database
        reportConfigRepository.saveAndFlush(reportConfig);

        // Get the reportConfig
        restReportConfigMockMvc.perform(get("/api/report-configs/{id}", reportConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportConfig.getId().intValue()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.storageType").value(DEFAULT_STORAGE_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.allowedPermissionsHint").value(DEFAULT_ALLOWED_PERMISSIONS_HINT))
            .andExpect(jsonPath("$.sizePageToContent").value(DEFAULT_SIZE_PAGE_TO_CONTENT.booleanValue()))
            .andExpect(jsonPath("$.ignoreHyperlink").value(DEFAULT_IGNORE_HYPERLINK.booleanValue()))
            .andExpect(jsonPath("$.forceLineBreakPolicy").value(DEFAULT_FORCE_LINE_BREAK_POLICY.booleanValue()))
            .andExpect(jsonPath("$.isCompressed").value(DEFAULT_IS_COMPRESSED.booleanValue()))
            .andExpect(jsonPath("$.encrypted").value(DEFAULT_ENCRYPTED.booleanValue()))
            .andExpect(jsonPath("$.ownerPassword").value(DEFAULT_OWNER_PASSWORD))
            .andExpect(jsonPath("$.userPassword").value(DEFAULT_USER_PASSWORD));
    }
    @Test
    @Transactional
    public void getNonExistingReportConfig() throws Exception {
        // Get the reportConfig
        restReportConfigMockMvc.perform(get("/api/report-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportConfig() throws Exception {
        // Initialize the database
        reportConfigRepository.saveAndFlush(reportConfig);

        int databaseSizeBeforeUpdate = reportConfigRepository.findAll().size();

        // Update the reportConfig
        ReportConfig updatedReportConfig = reportConfigRepository.findById(reportConfig.getId()).get();
        // Disconnect from session so that the updates on updatedReportConfig are not directly saved in db
        em.detach(updatedReportConfig);
        updatedReportConfig
            .fileType(UPDATED_FILE_TYPE)
            .storageType(UPDATED_STORAGE_TYPE)
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .suffix(UPDATED_SUFFIX)
            .author(UPDATED_AUTHOR)
            .allowedPermissionsHint(UPDATED_ALLOWED_PERMISSIONS_HINT)
            .sizePageToContent(UPDATED_SIZE_PAGE_TO_CONTENT)
            .ignoreHyperlink(UPDATED_IGNORE_HYPERLINK)
            .forceLineBreakPolicy(UPDATED_FORCE_LINE_BREAK_POLICY)
            .isCompressed(UPDATED_IS_COMPRESSED)
            .encrypted(UPDATED_ENCRYPTED)
            .ownerPassword(UPDATED_OWNER_PASSWORD)
            .userPassword(UPDATED_USER_PASSWORD);
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(updatedReportConfig);

        restReportConfigMockMvc.perform(put("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ReportConfig in the database
        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeUpdate);
        ReportConfig testReportConfig = reportConfigList.get(reportConfigList.size() - 1);
        assertThat(testReportConfig.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testReportConfig.getStorageType()).isEqualTo(UPDATED_STORAGE_TYPE);
        assertThat(testReportConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportConfig.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testReportConfig.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testReportConfig.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testReportConfig.getAllowedPermissionsHint()).isEqualTo(UPDATED_ALLOWED_PERMISSIONS_HINT);
        assertThat(testReportConfig.isSizePageToContent()).isEqualTo(UPDATED_SIZE_PAGE_TO_CONTENT);
        assertThat(testReportConfig.isIgnoreHyperlink()).isEqualTo(UPDATED_IGNORE_HYPERLINK);
        assertThat(testReportConfig.isForceLineBreakPolicy()).isEqualTo(UPDATED_FORCE_LINE_BREAK_POLICY);
        assertThat(testReportConfig.isIsCompressed()).isEqualTo(UPDATED_IS_COMPRESSED);
        assertThat(testReportConfig.isEncrypted()).isEqualTo(UPDATED_ENCRYPTED);
        assertThat(testReportConfig.getOwnerPassword()).isEqualTo(UPDATED_OWNER_PASSWORD);
        assertThat(testReportConfig.getUserPassword()).isEqualTo(UPDATED_USER_PASSWORD);

        // Validate the ReportConfig in Elasticsearch
        verify(mockReportConfigSearchRepository, times(1)).save(testReportConfig);
    }

    @Test
    @Transactional
    public void updateNonExistingReportConfig() throws Exception {
        int databaseSizeBeforeUpdate = reportConfigRepository.findAll().size();

        // Create the ReportConfig
        ReportConfigDTO reportConfigDTO = reportConfigMapper.toDto(reportConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportConfigMockMvc.perform(put("/api/report-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportConfig in the database
        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReportConfig in Elasticsearch
        verify(mockReportConfigSearchRepository, times(0)).save(reportConfig);
    }

    @Test
    @Transactional
    public void deleteReportConfig() throws Exception {
        // Initialize the database
        reportConfigRepository.saveAndFlush(reportConfig);

        int databaseSizeBeforeDelete = reportConfigRepository.findAll().size();

        // Delete the reportConfig
        restReportConfigMockMvc.perform(delete("/api/report-configs/{id}", reportConfig.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportConfig> reportConfigList = reportConfigRepository.findAll();
        assertThat(reportConfigList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReportConfig in Elasticsearch
        verify(mockReportConfigSearchRepository, times(1)).deleteById(reportConfig.getId());
    }

    @Test
    @Transactional
    public void searchReportConfig() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        reportConfigRepository.saveAndFlush(reportConfig);
        when(mockReportConfigSearchRepository.search(queryStringQuery("id:" + reportConfig.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reportConfig), PageRequest.of(0, 1), 1));

        // Search the reportConfig
        restReportConfigMockMvc.perform(get("/api/_search/report-configs?query=id:" + reportConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].storageType").value(hasItem(DEFAULT_STORAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].allowedPermissionsHint").value(hasItem(DEFAULT_ALLOWED_PERMISSIONS_HINT)))
            .andExpect(jsonPath("$.[*].sizePageToContent").value(hasItem(DEFAULT_SIZE_PAGE_TO_CONTENT.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreHyperlink").value(hasItem(DEFAULT_IGNORE_HYPERLINK.booleanValue())))
            .andExpect(jsonPath("$.[*].forceLineBreakPolicy").value(hasItem(DEFAULT_FORCE_LINE_BREAK_POLICY.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompressed").value(hasItem(DEFAULT_IS_COMPRESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].encrypted").value(hasItem(DEFAULT_ENCRYPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].ownerPassword").value(hasItem(DEFAULT_OWNER_PASSWORD)))
            .andExpect(jsonPath("$.[*].userPassword").value(hasItem(DEFAULT_USER_PASSWORD)));
    }
}
