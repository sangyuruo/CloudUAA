package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.domain.Role;
import com.emcloud.uaa.repository.ResourceRepository;
import com.emcloud.uaa.repository.RoleRepository;
import com.emcloud.uaa.service.RoleService;
import com.emcloud.uaa.service.dto.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Role.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final ResourceRepository resourceRepository;

    private final RoleRepository roleRepository;

    public RoleServiceImpl(ResourceRepository resourceRepository, RoleRepository roleRepository) {
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
    }

    //阿紫======================阿紫============================阿紫=======================阿紫========================阿紫
    public Role createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDesc(roleDTO.getDesc());
        if (roleDTO.getResources() != null) {
            Set resourcesSet =
            roleDTO.getResources().stream()
                .map(resourceRepository::findOneByResourceCode).collect(Collectors.toSet());
            role.setResources(resourcesSet);
        }
        roleRepository.save(role);
        log.debug("Created Information for role: {}", role);
        return role;
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
        return roleRepository.save(role);
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
        return roleRepository.save(role);
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
        return roleRepository.findAll(pageable);
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
        return roleRepository.findAllByNameOrDescContaining(pageable, name,desc);
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
        return roleRepository.findOne(id);
    }

    /**
     *  Delete the  authority by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
    }


}
