package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.Resources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Resources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
    Page<Resources> findAllByResourceNameContaining(Pageable pageable, String resourceName);
    Resources findOneByResourceCode(String code);
    List<Resources> findAllByParentCode(String parentCode);
    List<Resources> findAllByRoleIdentify(String roleIdentify);
}
