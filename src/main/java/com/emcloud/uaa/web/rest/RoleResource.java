package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.domain.Role;
import com.emcloud.uaa.service.RoleService;
import com.emcloud.uaa.web.rest.errors.BadRequestAlertException;
import com.emcloud.uaa.web.rest.util.HeaderUtil;
import com.emcloud.uaa.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "authority";

    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * POST  /authorities : Create a new role.
     *
     * @param role the role to create
     * @return the ResponseEntity with status 201 (Created) and with body the new role, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorities")
    @Timed
    public ResponseEntity<Role> createAuthority(@Valid @RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to save Role : {}", role);
        if (role.getId() != null) {
            throw new BadRequestAlertException("A new role cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Role result = roleService.save(role);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorities : Updates an existing role.
     *
     * @param role the role to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated role,
     * or with status 400 (Bad Request) if the role is not valid,
     * or with status 500 (Internal Server Error) if the role couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorities")
    @Timed
    public ResponseEntity<Role> updateAuthority(@Valid @RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to update Role : {}", role);
        if (role.getId() == null) {
            return createAuthority(role);
        }
        Role result = roleService.update(role);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, role.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorities : get all the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities")
    @Timed
    public ResponseEntity<List<Role>> getAllAuthorities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Authorities");
        Page<Role> page = roleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorities : get all the authorities by id or name.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/{name}or{desc}")
    @Timed
    public ResponseEntity<List<Role>> getAllAuthoritiesByNameOrDesc(@PathVariable String name, @PathVariable String desc, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Authorities");
        Page<Role> page = roleService.findAllByNameOrDesc(name,desc,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities/{name}or{desc}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }





    /**
     * GET  /authorities/:id : get the "id" authority.
     *
     * @param id the id of the authority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authority, or with status 404 (Not Found)
     */
    @GetMapping("/authorities/{id}")
    @Timed
    public ResponseEntity<Role> getAuthority(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        Role role = roleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(role));
    }

    /**
     * DELETE  /authorities/:id : delete the "id" authority.
     *
     * @param id the id of the authority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
