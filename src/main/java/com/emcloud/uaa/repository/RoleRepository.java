package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 通过name或desc查找
     *
     * */
    Page<Role> findAllByNameOrDescContaining(Pageable pageable, String name, String desc);

    Role findOneByName(String name);
//    Optional<Role> findAllByName(String name);
}
