package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.service.AuthorityService;
import com.emcloud.uaa.domain.Authority;
import com.emcloud.uaa.repository.AuthorityRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Authority.
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
     * Save a authority.
     *
     * @param authority the entity to save
     * @return the persisted entity
     */
    @Override
    public Authority save(Authority authority) {
        log.debug("Request to save Authority : {}", authority);
        return authorityRepository.save(authority);
    }



    /**
     * Update a authority.
     *
     * @param authority the entity to update
     * @return the persisted entity
     */
    @Override
    public Authority update(Authority authority) {
        log.debug("Request to save Authority : {}", authority);
        return authorityRepository.save(authority);
    }

    /**
     *  Get all the authorities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Authority> findAll(Pageable pageable) {
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
    public Page<Authority> findAllByNameOrDesc(String name,String desc,Pageable pageable){
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
    public Authority findOne(Long id) {
        log.debug("Request to get Authority : {}", id);
        return authorityRepository.findOne(id);
    }

    /**
     *  Delete the  authority by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Authority : {}", id);
        authorityRepository.delete(id);
    }
}
