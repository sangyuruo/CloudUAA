package com.emcloud.uaa.web.rest;

import com.emcloud.uaa.EmCloudUaaApp;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.repository.ResourceRepository;
import com.emcloud.uaa.service.ResourceService;
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
 * Test class for the ResourceResource REST controller.
 *
 * @see ResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmCloudUaaApp.class)
public class ResourceResourcesIntTest {

    private static final String DEFAULT_RESOURCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_URL = "BBBBBBBBBB";

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
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourceMockMvc;

    private Resources resources;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceResource resourceResource = new ResourceResource(resourceService);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
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
    public static Resources createEntity(EntityManager em) {
        Resources resources = new Resources()
            .resourceCode(DEFAULT_RESOURCE_CODE)
            .resourceName(DEFAULT_RESOURCE_NAME)
            .resourceType(DEFAULT_RESOURCE_TYPE)
            .resourceUrl(DEFAULT_RESOURCE_URL)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return resources;
    }

    @Before
    public void initTest() {
        resources = createEntity(em);
    }

    @Test
    @Transactional
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resources
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isCreated());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate + 1);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getResourceCode()).isEqualTo(DEFAULT_RESOURCE_CODE);
        assertThat(testResources.getResourceName()).isEqualTo(DEFAULT_RESOURCE_NAME);
        assertThat(testResources.getResourceType()).isEqualTo(DEFAULT_RESOURCE_TYPE);
        assertThat(testResources.getResourceUrl()).isEqualTo(DEFAULT_RESOURCE_URL);
        assertThat(testResources.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testResources.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResources.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testResources.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testResources.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resources with an existing ID
        resources.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResourceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setResourceCode(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setResourceName(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setResourceType(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setResourceUrl(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setEnable(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setCreatedBy(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setCreateTime(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setUpdatedBy(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resources.setUpdateTime(null);

        // Create the Resources, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isBadRequest());

        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resources);

        // Get all the resourceList
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resources.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceCode").value(hasItem(DEFAULT_RESOURCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].resourceName").value(hasItem(DEFAULT_RESOURCE_NAME.toString())))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].resourceUrl").value(hasItem(DEFAULT_RESOURCE_URL.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resources);

        // Get the resources
        restResourceMockMvc.perform(get("/api/resources/{id}", resources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resources.getId().intValue()))
            .andExpect(jsonPath("$.resourceCode").value(DEFAULT_RESOURCE_CODE.toString()))
            .andExpect(jsonPath("$.resourceName").value(DEFAULT_RESOURCE_NAME.toString()))
            .andExpect(jsonPath("$.resourceType").value(DEFAULT_RESOURCE_TYPE.toString()))
            .andExpect(jsonPath("$.resourceUrl").value(DEFAULT_RESOURCE_URL.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResource() throws Exception {
        // Get the resources
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResource() throws Exception {
        // Initialize the database
        resourceService.save(resources);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resources
        Resources updatedResources = resourceRepository.findOne(resources.getId());
        updatedResources
            .resourceCode(UPDATED_RESOURCE_CODE)
            .resourceName(UPDATED_RESOURCE_NAME)
            .resourceType(UPDATED_RESOURCE_TYPE)
            .resourceUrl(UPDATED_RESOURCE_URL)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResources)))
            .andExpect(status().isOk());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getResourceCode()).isEqualTo(UPDATED_RESOURCE_CODE);
        assertThat(testResources.getResourceName()).isEqualTo(UPDATED_RESOURCE_NAME);
        assertThat(testResources.getResourceType()).isEqualTo(UPDATED_RESOURCE_TYPE);
        assertThat(testResources.getResourceUrl()).isEqualTo(UPDATED_RESOURCE_URL);
        assertThat(testResources.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testResources.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResources.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testResources.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testResources.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Create the Resources

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resources)))
            .andExpect(status().isCreated());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceService.save(resources);

        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Get the resources
        restResourceMockMvc.perform(delete("/api/resources/{id}", resources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resources> resourcesList = resourceRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resources.class);
        Resources resources1 = new Resources();
        resources1.setId(1L);
        Resources resources2 = new Resources();
        resources2.setId(resources1.getId());
        assertThat(resources1).isEqualTo(resources2);
        resources2.setId(2L);
        assertThat(resources1).isNotEqualTo(resources2);
        resources1.setId(null);
        assertThat(resources1).isNotEqualTo(resources2);
    }
}
