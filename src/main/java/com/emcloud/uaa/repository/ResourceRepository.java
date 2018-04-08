package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the Resources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
    Page<Resources> findAllByResourceNameContaining(Pageable pageable, String resourceName);
    List<Resources> findAllByParentCode(String parentCode);
    Resources  findByResourceCode(String resourceCode);
    List<Resources> findAllByParentCodeAndRoles(String parentCode, Set<Role> Roles);
    List<Resources> findAll();



}
