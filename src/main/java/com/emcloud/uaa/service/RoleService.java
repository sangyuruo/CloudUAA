package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Role.
 */
public interface RoleService {

    /**
     * Save a role.
     *
     * @param role the entity to save
     * @return the persisted entity
     */
    Role save(Role role);




    /**
     * Update a role.
     *
     * @param role the entity to update
     * @return the persisted entity
     */
    Role update(Role role);


    /**
     *  Get all the authorities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Role> findAll(Pageable pageable);


    Optional<Role> findByName(String name);

    /**
     *  Get the "id" role.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Role findOne(Long id);

    /**
     *  Delete the "id" role.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);


    /**
     *  Get all the authorities by desc or name .
     *  azi
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Role> findAllByNameOrDesc(String name , String desc, Pageable pageable);

}
