package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.service.ResourceAdministrationService;
import com.emcloud.uaa.domain.ResourceAdministration;
import com.emcloud.uaa.repository.ResourceAdministrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ResourceAdministration.
 */
@Service
@Transactional
public class ResourceAdministrationServiceImpl implements ResourceAdministrationService{

    private final Logger log = LoggerFactory.getLogger(ResourceAdministrationServiceImpl.class);

    private final ResourceAdministrationRepository resourceAdministrationRepository;

    public ResourceAdministrationServiceImpl(ResourceAdministrationRepository resourceAdministrationRepository) {
        this.resourceAdministrationRepository = resourceAdministrationRepository;
    }

    /**
     * Save a resourceAdministration.
     *
     * @param resourceAdministration the entity to save
     * @return the persisted entity
     */
    @Override
    public ResourceAdministration save(ResourceAdministration resourceAdministration) {
        log.debug("Request to save ResourceAdministration : {}", resourceAdministration);
        return resourceAdministrationRepository.save(resourceAdministration);
    }

    /**
     *  Get all the resourceAdministrations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourceAdministration> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceAdministrations");
        return resourceAdministrationRepository.findAll(pageable);
    }

    /**
     *  Get all the ResourceAdministration by meterName .
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourceAdministration> findByResourceName(Pageable pageable,String resourceName) {
        log.debug("Request to get all MeterCategoryInfo by resourceName");
        return resourceAdministrationRepository.findAllByResourceAdministrationContaining(pageable,resourceName);
    }

    /**
     *  Get one resourceAdministration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ResourceAdministration findOne(Long id) {
        log.debug("Request to get ResourceAdministration : {}", id);
        return resourceAdministrationRepository.findOne(id);
    }


    /**
     *  Delete the  resourceAdministration by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceAdministration : {}", id);
        resourceAdministrationRepository.delete(id);
    }
}
