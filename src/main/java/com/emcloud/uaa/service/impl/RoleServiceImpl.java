package com.emcloud.uaa.service.impl;

import com.emcloud.uaa.domain.Resources;
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

import java.util.List;
import java.util.Optional;
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
//
//    //阿紫======================阿紫============================阿紫=======================阿紫========================阿紫
//    public Role createRole(RoleDTO roleDTO) {
//        Role role = new Role();
//        role.setName(roleDTO.getName());
//        role.setDesc(roleDTO.getDesc());
//        if (roleDTO.getResources() != null) {
//            Set resourcesSet =
//            roleDTO.getResources().stream()
//                .map(resourceRepository::findOneByResourceCode).collect(Collectors.toSet());
//            role.setResources(resourcesSet);
//        }
//        roleRepository.save(role);
//        log.debug("Created Information for role: {}", role);
//        return role;
//    }

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
     *  Get all the roles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        log.debug("Request to get all roles");
        return roleRepository.findAll(pageable);
    }

    /**
     *  Get all the roles by id or name.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAllByNameOrDesc(String name, String desc, Pageable pageable){
        log.debug("Request to get all roles by id or name");
        return roleRepository.findAllByNameOrDescContaining(pageable, name,desc);
    }

    /**
     *  Get one role by id.
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
     *  Delete the  role by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
    }
//阿紫============================阿紫 ============================阿紫==================================================
    public Optional<RoleDTO> updateRole(RoleDTO roleDTO) {
        return Optional.of(roleRepository
            .findOneByName(roleDTO.getName()))
            .map(role -> {
                role.setName(roleDTO.getName());
                role.setDesc(roleDTO.getDesc());
                Set<Resources> managedResources = role.getResources();
                managedResources.clear();
                roleDTO.getResources().stream()
                    .map(resourceRepository::findOneByResourceCode)
                    .forEach(managedResources::add);
               // cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                log.debug("Changed Information for Role: {}", role);
                return role;
            })
            .map(RoleDTO::new);
    }

    /**
     * @return a list of all the resources
     */
    public List<String> getResources() {
        return resourceRepository.findAll().stream().map(Resources::getResourceCode).collect(Collectors.toList());

    }
}
