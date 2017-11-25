package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.security.SecurityUtils;
import com.emcloud.uaa.service.ResourceService;
import com.emcloud.uaa.domain.Resource;
import com.emcloud.uaa.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;


/**
 * Service Implementation for managing Resource.
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService{

    private final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Save a resource.
     *
     * @param resource the entity to save
     * @return the persisted entity
     */
    @Override
    public Resource save(Resource resource) {
        log.debug("Request to save Resource : {}", resource);
        resource.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        resource.setCreateTime(Instant.now());
        resource.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        resource.setUpdateTime(Instant.now());
        return resourceRepository.save(resource);
    }

    /**
     * update a resource.
     *
     * @param resource the entity to update
     * @return the persisted entity
     */
    @Override
    public Resource update(Resource resource) {
        log.debug("Request to save Company : {}", resource);
        resource.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        resource.setUpdateTime(Instant.now());
        return resourceRepository.save(resource);
    }

    /**
     *  Get all the resources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Resource> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourceRepository.findAll(pageable);
    }

    /**
     *  Get one resource by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Resource findOne(Long id) {
        log.debug("Request to get Resource : {}", id);
        return resourceRepository.findOne(id);
    }

    /**
     *  Get all the Resource by resourceName .
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Resource> findByResourceName(Pageable pageable,String resourceName) {
        log.debug("Request to get all Resource by resourceName");
        return resourceRepository.findAllByResourceNameContaining(pageable,resourceName);
    }

    /**
     *  Delete the  resource by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resource : {}", id);
        resourceRepository.delete(id);
    }


}
