package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Resources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
    Page<Resources> findAllByResourceNameContaining(Pageable pageable, String resourceName);
    Role findOneByResourceCode(String code);
}
