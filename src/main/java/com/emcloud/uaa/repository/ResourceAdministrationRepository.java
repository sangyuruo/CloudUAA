package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.ResourceAdministration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResourceAdministration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceAdministrationRepository extends JpaRepository<ResourceAdministration, Long> {
    Page<ResourceAdministration> findAllByResourceAdministrationContaining(Pageable pageable, String meterName);
}
