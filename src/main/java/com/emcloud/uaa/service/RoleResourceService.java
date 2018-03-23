package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.RoleResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RoleResource.
 */
public interface RoleResourceService {

    /**
     * Save a roleResource.
     *
     * @param roleResource the entity to save
     * @return the persisted entity
     */
    RoleResource save(RoleResource roleResource);

    /**
     *  Get all the roleResources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RoleResource> findAll(Pageable pageable);

    /**
     *  Get the "id" roleResource.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RoleResource findOne(Long id);

    /**
     *  Delete the "id" roleResource.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
