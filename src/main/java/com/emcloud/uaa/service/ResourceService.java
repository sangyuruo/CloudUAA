package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing Resources.
 */
public interface ResourceService {

    /**
     * Save a resources.
     *
     * @param resources the entity to save
     * @return the persisted entity
     */
    Resources save(Resources resources);

    /**
     * update a resources.
     *
     * @param resources the entity to update
     * @return the persisted entity
     */
    Resources update(Resources resources);

    List<Resources> findByParentCode(String parentCode);

    Resources findByResourceCode(String resourceCode);

    /**
     *  Get all the resources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Resources> findAll(Pageable pageable);


    List<Resources> findAll();

    /**
     *  Get the "id" resource.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Resources findOne(Long id);

    /**
     *  Get the "resourceName" Resources.
     *
     *  @param resourceName the id of the entity
     *  @return the entity
     */
    Page<Resources> findByResourceName(Pageable pageable, String resourceName);


    List<Resources> findAllByParentCodeAndRoles(String parentCode, Set<Role> Roles);
    /**
     *  Delete the "id" resource.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<Resources> findByValue(String value);

    List<String> findByValue2(String value);

}
