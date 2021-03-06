package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;
import com.emcloud.uaa.security.SecurityUtils;
import com.emcloud.uaa.service.ResourceService;
import com.emcloud.uaa.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing Resources.
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
     * Save a resources.
     *
     * @param resources the entity to save
     * @return the persisted entity
     */
    @Override
    public Resources save(Resources resources) {
        log.debug("Request to save Resources : {}", resources);
        resources.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        resources.setCreateTime(Instant.now());
        resources.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        resources.setUpdateTime(Instant.now());
        return resourceRepository.save(resources);
    }

    /**
     * update a resources.
     *
     * @param resources the entity to update
     * @return the persisted entity
     */
    @Override
    public Resources update(Resources resources) {
        log.debug("Request to save Company : {}", resources);
        resources.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        resources.setUpdateTime(Instant.now());
        return resourceRepository.save(resources);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resources> findByParentCode(String parentCode) {
        log.debug("Request to get all Resources by parentCode");
        return resourceRepository.findAllByParentCode(parentCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Resources findByResourceCode(String resourceCode){
        log.debug("Request to get all Resources by roleIdentify");
        return resourceRepository.findByResourceCode(resourceCode);
    }

    /**
     *  Get all the resources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Resources> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourceRepository.findAll(pageable);
    }

    @Override
    public List<Resources> findAll() {
        return resourceRepository.findAll();
    }

    /**
     *  Get one resource by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Resources findOne(Long id) {
        log.debug("Request to get Resources : {}", id);
        return resourceRepository.findOne(id);
    }

    /**
     *  Get all the Resources by resourceName .
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Resources> findByResourceName(Pageable pageable, String resourceName) {
        log.debug("Request to get all Resources by resourceName");
        return resourceRepository.findAllByResourceNameContaining(pageable,resourceName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resources> findAllByParentCodeAndRoles(String parentCode, Set<Role> Roles){
        //log.debug("Request to get all Resources by parentCode , Roles");
        return resourceRepository.findAllByParentCodeAndRoles(parentCode,Roles);
    }


    /**
     *  Delete the  resource by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resources : {}", id);
        resourceRepository.delete(id);
    }

    @Override
    public List<Resources> findByValue(String value) {
        return resourceRepository.findByValue(value);
    }

    @Override
    public List<String> findByValue2(String value) {
        return resourceRepository.findByValue2(value);
    }


}
