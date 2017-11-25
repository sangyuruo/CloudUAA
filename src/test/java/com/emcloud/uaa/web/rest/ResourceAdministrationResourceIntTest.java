package com.emcloud.uaa.web.rest;

import com.emcloud.uaa.EmCloudUaaApp;

import com.emcloud.uaa.config.SecurityBeanOverrideConfiguration;

import com.emcloud.uaa.domain.ResourceAdministration;
import com.emcloud.uaa.repository.ResourceAdministrationRepository;
import com.emcloud.uaa.service.ResourceAdministrationService;
import com.emcloud.uaa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.uaa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResourceAdministrationResource REST controller.
 *
 * @see ResourceAdministrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmCloudUaaApp.class)
public class ResourceAdministrationResourceIntTest {

    private static final String DEFAULT_RESOURCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VISIT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_VISIT_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResourceAdministrationRepository resourceAdministrationRepository;

    @Autowired
    private ResourceAdministrationService resourceAdministrationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourceAdministrationMockMvc;

    private ResourceAdministration resourceAdministration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceAdministrationResource resourceAdministrationResource = new ResourceAdministrationResource(resourceAdministrationService);
        this.restResourceAdministrationMockMvc = MockMvcBuilders.standaloneSetup(resourceAdministrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceAdministration createEntity(EntityManager em) {
        ResourceAdministration resourceAdministration = new ResourceAdministration()
            .resourceCode(DEFAULT_RESOURCE_CODE)
            .resourceName(DEFAULT_RESOURCE_NAME)
            .resourceType(DEFAULT_RESOURCE_TYPE)
            .visitAddress(DEFAULT_VISIT_ADDRESS)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return resourceAdministration;
    }

    @Before
    public void initTest() {
        resourceAdministration = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceAdministration() throws Exception {
        int databaseSizeBeforeCreate = resourceAdministrationRepository.findAll().size();

        // Create the ResourceAdministration
        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isCreated());

        // Validate the ResourceAdministration in the database
        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceAdministration testResourceAdministration = resourceAdministrationList.get(resourceAdministrationList.size() - 1);
        assertThat(testResourceAdministration.getResourceCode()).isEqualTo(DEFAULT_RESOURCE_CODE);
        assertThat(testResourceAdministration.getResourceName()).isEqualTo(DEFAULT_RESOURCE_NAME);
        assertThat(testResourceAdministration.getResourceType()).isEqualTo(DEFAULT_RESOURCE_TYPE);
        assertThat(testResourceAdministration.getVisitAddress()).isEqualTo(DEFAULT_VISIT_ADDRESS);
        assertThat(testResourceAdministration.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testResourceAdministration.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResourceAdministration.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testResourceAdministration.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testResourceAdministration.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createResourceAdministrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceAdministrationRepository.findAll().size();

        // Create the ResourceAdministration with an existing ID
        resourceAdministration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceAdministration in the database
        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResourceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setResourceName(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setResourceType(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisitAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setVisitAddress(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setEnable(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setCreatedBy(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setCreateTime(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setUpdatedBy(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAdministrationRepository.findAll().size();
        // set the field null
        resourceAdministration.setUpdateTime(null);

        // Create the ResourceAdministration, which fails.

        restResourceAdministrationMockMvc.perform(post("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isBadRequest());

        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceAdministrations() throws Exception {
        // Initialize the database
        resourceAdministrationRepository.saveAndFlush(resourceAdministration);

        // Get all the resourceAdministrationList
        restResourceAdministrationMockMvc.perform(get("/api/resource-administrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceAdministration.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceCode").value(hasItem(DEFAULT_RESOURCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].resourceName").value(hasItem(DEFAULT_RESOURCE_NAME.toString())))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].visitAddress").value(hasItem(DEFAULT_VISIT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getResourceAdministration() throws Exception {
        // Initialize the database
        resourceAdministrationRepository.saveAndFlush(resourceAdministration);

        // Get the resourceAdministration
        restResourceAdministrationMockMvc.perform(get("/api/resource-administrations/{id}", resourceAdministration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resourceAdministration.getId().intValue()))
            .andExpect(jsonPath("$.resourceCode").value(DEFAULT_RESOURCE_CODE.toString()))
            .andExpect(jsonPath("$.resourceName").value(DEFAULT_RESOURCE_NAME.toString()))
            .andExpect(jsonPath("$.resourceType").value(DEFAULT_RESOURCE_TYPE.toString()))
            .andExpect(jsonPath("$.visitAddress").value(DEFAULT_VISIT_ADDRESS.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResourceAdministration() throws Exception {
        // Get the resourceAdministration
        restResourceAdministrationMockMvc.perform(get("/api/resource-administrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceAdministration() throws Exception {
        // Initialize the database
        resourceAdministrationService.save(resourceAdministration);

        int databaseSizeBeforeUpdate = resourceAdministrationRepository.findAll().size();

        // Update the resourceAdministration
        ResourceAdministration updatedResourceAdministration = resourceAdministrationRepository.findOne(resourceAdministration.getId());
        updatedResourceAdministration
            .resourceCode(UPDATED_RESOURCE_CODE)
            .resourceName(UPDATED_RESOURCE_NAME)
            .resourceType(UPDATED_RESOURCE_TYPE)
            .visitAddress(UPDATED_VISIT_ADDRESS)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restResourceAdministrationMockMvc.perform(put("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourceAdministration)))
            .andExpect(status().isOk());

        // Validate the ResourceAdministration in the database
        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeUpdate);
        ResourceAdministration testResourceAdministration = resourceAdministrationList.get(resourceAdministrationList.size() - 1);
        assertThat(testResourceAdministration.getResourceCode()).isEqualTo(UPDATED_RESOURCE_CODE);
        assertThat(testResourceAdministration.getResourceName()).isEqualTo(UPDATED_RESOURCE_NAME);
        assertThat(testResourceAdministration.getResourceType()).isEqualTo(UPDATED_RESOURCE_TYPE);
        assertThat(testResourceAdministration.getVisitAddress()).isEqualTo(UPDATED_VISIT_ADDRESS);
        assertThat(testResourceAdministration.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testResourceAdministration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResourceAdministration.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testResourceAdministration.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testResourceAdministration.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceAdministration() throws Exception {
        int databaseSizeBeforeUpdate = resourceAdministrationRepository.findAll().size();

        // Create the ResourceAdministration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResourceAdministrationMockMvc.perform(put("/api/resource-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceAdministration)))
            .andExpect(status().isCreated());

        // Validate the ResourceAdministration in the database
        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResourceAdministration() throws Exception {
        // Initialize the database
        resourceAdministrationService.save(resourceAdministration);

        int databaseSizeBeforeDelete = resourceAdministrationRepository.findAll().size();

        // Get the resourceAdministration
        restResourceAdministrationMockMvc.perform(delete("/api/resource-administrations/{id}", resourceAdministration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResourceAdministration> resourceAdministrationList = resourceAdministrationRepository.findAll();
        assertThat(resourceAdministrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceAdministration.class);
        ResourceAdministration resourceAdministration1 = new ResourceAdministration();
        resourceAdministration1.setId(1L);
        ResourceAdministration resourceAdministration2 = new ResourceAdministration();
        resourceAdministration2.setId(resourceAdministration1.getId());
        assertThat(resourceAdministration1).isEqualTo(resourceAdministration2);
        resourceAdministration2.setId(2L);
        assertThat(resourceAdministration1).isNotEqualTo(resourceAdministration2);
        resourceAdministration1.setId(null);
        assertThat(resourceAdministration1).isNotEqualTo(resourceAdministration2);
    }
}
