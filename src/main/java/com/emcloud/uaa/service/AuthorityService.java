package com.emcloud.uaa.service;

import com.emcloud.uaa.domain.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Authority.
 */
public interface AuthorityService {

    /**
     * Save a authority.
     *
     * @param authority the entity to save
     * @return the persisted entity
     */
    Authority save(Authority authority);




    /**
     * Update a authority.
     *
     * @param authority the entity to update
     * @return the persisted entity
     */
    Authority update(Authority authority);


    /**
     *  Get all the authorities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Authority> findAll(Pageable pageable);

    /**
     *  Get the "id" authority.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Authority findOne(Long id);

    /**
     *  Delete the "id" authority.
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
    Page<Authority> findAllByNameOrDesc(String name ,String desc,Pageable pageable);

}
