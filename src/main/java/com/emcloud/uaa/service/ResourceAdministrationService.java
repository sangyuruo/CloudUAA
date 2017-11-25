package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.ResourceAdministration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ResourceAdministration.
 */
public interface ResourceAdministrationService {

    /**
     * Save a resourceAdministration.
     *
     * @param resourceAdministration the entity to save
     * @return the persisted entity
     */
    ResourceAdministration save(ResourceAdministration resourceAdministration);

    /**
     * update a resourceAdministration.
     *
     * @param resourceAdministration the entity to update
     * @return the persisted entity
     */
    ResourceAdministration update(ResourceAdministration resourceAdministration);


    /**
     *  Get all the resourceAdministrations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ResourceAdministration> findAll(Pageable pageable);

    /**
     *  Get the "id" resourceAdministration.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ResourceAdministration findOne(Long id);


    /**
     *  Get the "resourceName" meterCategoryInfo.
     *
     *  @param resourceName the id of the entity
     *  @return the entity
     */
    Page<ResourceAdministration> findByResourceName(Pageable pageable,String resourceName);

    /**
     *  Delete the "id" resourceAdministration.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
