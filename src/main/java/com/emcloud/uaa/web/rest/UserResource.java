package com.emcloud.uaa.web.rest;

import com.emcloud.uaa.config.Constants;
import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;
import com.emcloud.uaa.domain.User;
import com.emcloud.uaa.repository.RoleRepository;
import com.emcloud.uaa.repository.UserRepository;
import com.emcloud.uaa.security.RolesConstants;
import com.emcloud.uaa.service.MailService;
import com.emcloud.uaa.service.ResourceService;
import com.emcloud.uaa.service.RoleService;
import com.emcloud.uaa.service.UserService;
import com.emcloud.uaa.service.dto.UserDTO;
import com.emcloud.uaa.web.rest.errors.BadRequestAlertException;
import com.emcloud.uaa.web.rest.errors.EmailAlreadyUsedException;
import com.emcloud.uaa.web.rest.errors.LoginAlreadyUsedException;
import com.emcloud.uaa.web.rest.vm.ManagedUserVM;
import com.emcloud.uaa.web.rest.util.HeaderUtil;
import com.emcloud.uaa.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Role,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the roles, because people will
 * quite often do relationships with the user, and we don't want them to get the roles all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all roles come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceResource resourceResource;

    private final MailService mailService;

    public UserResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured(RolesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(managedUserVM);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @Secured(RolesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(managedUserVM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", managedUserVM.getLogin()));
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/roles")
    @Timed
    @Secured(RolesConstants.ADMIN)
    public List<String> getRoles() {
        return userService.getRoles();
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithRolesByLogin(login)
                .map(UserDTO::new));
    }

    @GetMapping("/users/bylogin/{login}")
    @Timed
    public StringBuilder getResource(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        List<Resources> roots = userService.findOneByLogin(login);

        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {//查询所有菜单

            Resources preNav = null;
            for (Resources nav : roots) {
                String resourceCode = nav.getResourceCode();
                curLevelNum = getLevelNum(nav);
                if (null != preNav) {
                    if (lastLevelNum == curLevelNum) { // 同一层次的
                        sb.append("}, \n");
                    } else if (lastLevelNum > curLevelNum) { // 这次的层次比上次高一层，也即跳到上一层
                        sb.append("} \n");

                        for (int j = curLevelNum; j < lastLevelNum; j++) {
                            sb.append("]} \n");
                            if (j == lastLevelNum - 1) {
                                sb.append(", \n");
                            }
                        }
                    }
                }
                sb.append("{ \n");
                sb.append("\"title\"").append(":\"").append(nav.getResourceName()).append("\",");
                sb.append("\n");
                sb.append("\"icon\"").append(":\"").append("nb-bar-chart").append("\",");
                sb.append("\n");
                List<Resources> nav2roots = resourceService.findByParentCode(nav.getResourceCode());
                if (nav2roots.size() != 0) {
                    sb.append("\"children\"").append(":").append("[");

                    sb.append("\n");
                    int lastLevelNum2 = 0; // 上一次的层次
                    int curLevelNum2 = 0; // 本次对象的层次
                    List<Resources> roots2 = resourceService.findByParentCode(resourceCode);
                    // sb.append("[");
                    try {//查询所有菜单
                        Resources preNav2 = null;
                        for (Resources nav2 : roots2) {
                            curLevelNum2 = getLevelNum(nav2);//2=2
                            if (null != preNav2) {
                                if (lastLevelNum2 == curLevelNum2) { // 同一层次的
                                    sb.append("}, \n");
                                } else if (lastLevelNum2 > curLevelNum2) { // 这次的层次比上次高一层，也即跳到上一层
                                    sb.append("} \n");

                                    for (int j = curLevelNum2; j < lastLevelNum2; j++) {
                                        sb.append("]} \n");
                                        if (j == lastLevelNum2 - 1) {
                                            sb.append(", \n");
                                        }
                                    }
                                }
                            }
                            sb.append("{ \n");
                            sb.append("\"title\"").append(":\"").append(nav2.getResourceName()).append("\"");
                            lastLevelNum2 = curLevelNum2;
                            preNav2 = nav;
                        }
                        sb.append("}\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // sb.append("]");


                    sb.append("] \n");
                }
                lastLevelNum = curLevelNum;
                preNav = nav;
            }
            sb.append("} \n");
            for (int j = 1; j < curLevelNum; j++) {
                sb.append("]} \n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("]");
        return sb;
    }
    private static int getLevelNum(Resources org) {
        return org.getResourceCode().length() / 2;
    }


    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(RolesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }
}
