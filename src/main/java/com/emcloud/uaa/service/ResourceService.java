package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Resource.
 */
public interface ResourceService {

    /**
     * Save a resource.
     *
     * @param resource the entity to save
     * @return the persisted entity
     */
    Resource save(Resource resource);

    /**
     * update a resource.
     *
     * @param resource the entity to update
     * @return the persisted entity
     */
    Resource update(Resource resource);
    /**
     *  Get all the resources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Resource> findAll(Pageable pageable);

    /**
     *  Get the "id" resource.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Resource findOne(Long id);

    /**
     *  Get the "resourceName" Resource.
     *
     *  @param resourceName the id of the entity
     *  @return the entity
     */
    Page<Resource> findByResourceName(Pageable pageable,String resourceName);

    /**
     *  Delete the "id" resource.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
