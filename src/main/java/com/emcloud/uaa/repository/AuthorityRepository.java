package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Authority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    /**
     * 通过id或name查找
     *
     * */
    Page<Authority> findAllByNameOrDescContaining(Pageable pageable, String name,String desc);

    Authority findOneByName(String name);
}
