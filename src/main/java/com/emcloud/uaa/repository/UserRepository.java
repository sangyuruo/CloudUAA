package com.emcloud.uaa.repository;

import com.emcloud.uaa.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过激活码查找某个 findOneByActivationKey
     */
    Optional<User> findOneByActivationKey(String activationKey);


    /**
     * 查找激活状态为False 且 创建日期 在指定日期之前的所有
     */
    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);


    /**
     * 通过重置码查找某个 findOneByResetKey
     */
    Optional<User> findOneByResetKey(String resetKey);


    /**
     * 通过Email查找且忽略大小写 findOneByEmailIgnoreCase
     */
    Optional<User> findOneByEmailIgnoreCase(String email);


    /**
     * 通过登录名查找   findOneByLogin
     */
    Optional<User> findOneByLogin(String login);



    @EntityGraph(attributePaths = "roles")
    User findOneWithRolesById(Long id);

    @EntityGraph(attributePaths = "roles")
    @Cacheable(cacheNames="users")
    Optional<User> findOneWithRolesByLogin(String login);

    /**
     * 查找登录名不为login的方法   findOneByLoginNot
     */
    Page<User> findAllByLoginNot(Pageable pageable, String login);
}
