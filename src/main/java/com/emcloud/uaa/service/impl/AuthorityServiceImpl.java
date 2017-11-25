package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.domain.Role;
import com.emcloud.uaa.service.AuthorityService;
import com.emcloud.uaa.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Role.
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService{

    private final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a role.
     *
     * @param role the entity to save
     * @return the persisted entity
     */
    @Override
    public Role save(Role role) {
        log.debug("Request to save Role : {}", role);
        return authorityRepository.save(role);
    }



    /**
     * Update a role.
     *
     * @param role the entity to update
     * @return the persisted entity
     */
    @Override
    public Role update(Role role) {
        log.debug("Request to save Role : {}", role);
        return authorityRepository.save(role);
    }

    /**
     *  Get all the authorities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        log.debug("Request to get all Authorities");
        return authorityRepository.findAll(pageable);
    }

    /**
     *  Get all the authorities by id or name.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAllByNameOrDesc(String name, String desc, Pageable pageable){
        log.debug("Request to get all Authorities by id or name");
        return authorityRepository.findAllByNameOrDescContaining(pageable, name,desc);
    }




    /**
     *  Get one authority by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Role findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        return authorityRepository.findOne(id);
    }

    /**
     *  Delete the  authority by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        authorityRepository.delete(id);
    }
}
