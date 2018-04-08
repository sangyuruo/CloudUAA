package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.domain.RoleResource;
import com.emcloud.uaa.service.RoleResourceService;
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
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing RoleResource.
 */
@RestController
@RequestMapping("/api")
public class RoleResourceResource {

    private final Logger log = LoggerFactory.getLogger(RoleResourceResource.class);

    private static final String ENTITY_NAME = "roleResource";

    private final RoleResourceService roleResourceService;


    public RoleResourceResource(RoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }

    /**
     * POST  /role-resources : Create a new roleResource.
     *
     * @param roleResource the roleResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleResource, or with status 400 (Bad Request) if the roleResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-resources")
    @Timed
    public ResponseEntity<RoleResource> createRoleResource(@Valid @RequestBody RoleResource roleResource) throws URISyntaxException {
        log.debug("REST request to save RoleResource : {}", roleResource);
        if (roleResource.getId() != null) {
            throw new BadRequestAlertException("A new roleResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleResource result = roleResourceService.save(roleResource);
        return ResponseEntity.created(new URI("/api/role-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-resources : Updates an existing roleResource.
     *
     * @param roleResource the roleResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleResource,
     * or with status 400 (Bad Request) if the roleResource is not valid,
     * or with status 500 (Internal Server Error) if the roleResource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-resources")
    @Timed
    public ResponseEntity<RoleResource> updateRoleResource(@Valid @RequestBody RoleResource roleResource) throws URISyntaxException {
        log.debug("REST request to update RoleResource : {}", roleResource);
        if (roleResource.getId() == null) {
            return createRoleResource(roleResource);
        }
        RoleResource result = roleResourceService.save(roleResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleResource.getId().toString()))
            .body(result);
    }

    @PutMapping("/role-resources/update")
    @Timed
    public void update(@Valid @RequestBody RoleResource roleResource) throws URISyntaxException {
        log.debug("REST request to update RoleResource : {}", roleResource);
        String[] resourceCodes = roleResource.getResourceCode().split(",");
        List<RoleResource> roleResource3 = roleResourceService.findByRoleName(roleResource.getRoleName());
        for (RoleResource roleResource1 : roleResource3){
            roleResourceService.delete(roleResource1.getId());
        }
        for (String s : resourceCodes) {
            RoleResource roleResource2 = new RoleResource();
            roleResource2.setRoleName(roleResource.getRoleName());
            roleResource2.setResourceCode(s);


            roleResourceService.save(roleResource2);
         /*   for (RoleResource roleResource1 : roleResource3){
                if(roleResource1.getResourceCode().equals(roleResource2.getResourceCode())
                    &&
                    roleResource1.getRoleName().equals(roleResource2.getRoleName())) {
                    break;
                }
                else {
                    roleResourceService.save(roleResource2);
                }

            }*/
           // roleResourceService.save(roleResource2);


          /*  if (roleResource3.contains(roleResource2)){
                break;
            }
            else {
                roleResourceService.save(roleResource2);
            }*/
        }
    }

    /**
     * GET  /role-resources : get all the roleResources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleResources in body
     */
    @GetMapping("/role-resources")
    @Timed
    public ResponseEntity<List<RoleResource>> getAllRoleResources(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RoleResources");
        Page<RoleResource> page = roleResourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-resources");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/role-resources/{roleName}")
    @Timed
    public List<RoleResource> getAllRoleResources(String roleName) {
        log.debug("REST request to get a page of RoleResources");
        List<RoleResource> roleResource = roleResourceService.findByRoleName(roleName);
        return roleResource;
    }
    /**
     * GET  /role-resources/:id : get the "id" roleResource.
     *
     * @param id the id of the roleResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleResource, or with status 404 (Not Found)
     */
    @GetMapping("/role-resources/{id}")
    @Timed
    public ResponseEntity<RoleResource> getRoleResource(@PathVariable Long id) {
        log.debug("REST request to get RoleResource : {}", id);
        RoleResource roleResource = roleResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roleResource));
    }

    /**
     * DELETE  /role-resources/:id : delete the "id" roleResource.
     *
     * @param id the id of the roleResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoleResource(@PathVariable Long id) {
        log.debug("REST request to delete RoleResource : {}", id);
        roleResourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
