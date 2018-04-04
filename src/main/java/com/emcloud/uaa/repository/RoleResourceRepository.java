package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.RoleResource;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RoleResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    List<RoleResource> findByRoleName(String roleName);

}
