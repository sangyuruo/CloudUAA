package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.domain.ResourceAdministration;
import com.emcloud.uaa.service.ResourceAdministrationService;
import com.emcloud.uaa.web.rest.errors.BadRequestAlertException;
import com.emcloud.uaa.web.rest.util.HeaderUtil;
import com.emcloud.uaa.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
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
 * REST controller for managing ResourceAdministration.
 */
@RestController
@RequestMapping("/api")
public class ResourceAdministrationResource {

    private final Logger log = LoggerFactory.getLogger(ResourceAdministrationResource.class);

    private static final String ENTITY_NAME = "resourceAdministration";

    private final ResourceAdministrationService resourceAdministrationService;

    public ResourceAdministrationResource(ResourceAdministrationService resourceAdministrationService) {
        this.resourceAdministrationService = resourceAdministrationService;
    }

    /**
     * POST  /resource-administrations : Create a new resourceAdministration.
     *
     * @param resourceAdministration the resourceAdministration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourceAdministration, or with status 400 (Bad Request) if the resourceAdministration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resource-administrations")
    @Timed
    public ResponseEntity<ResourceAdministration> createResourceAdministration(@Valid @RequestBody ResourceAdministration resourceAdministration) throws URISyntaxException {
        log.debug("REST request to save ResourceAdministration : {}", resourceAdministration);
        if (resourceAdministration.getId() != null) {
            throw new BadRequestAlertException("A new resourceAdministration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceAdministration result = resourceAdministrationService.save(resourceAdministration);
        return ResponseEntity.created(new URI("/api/resource-administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resource-administrations : Updates an existing resourceAdministration.
     *
     * @param resourceAdministration the resourceAdministration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourceAdministration,
     * or with status 400 (Bad Request) if the resourceAdministration is not valid,
     * or with status 500 (Internal Server Error) if the resourceAdministration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resource-administrations")
    @Timed
    public ResponseEntity<ResourceAdministration> updateResourceAdministration(@Valid @RequestBody ResourceAdministration resourceAdministration) throws URISyntaxException {
        log.debug("REST request to update ResourceAdministration : {}", resourceAdministration);
        if (resourceAdministration.getId() == null) {
            return createResourceAdministration(resourceAdministration);
        }
        ResourceAdministration result = resourceAdministrationService.save(resourceAdministration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourceAdministration.getId().toString()))
            .body(result);
    }


    /**
     * GET  /resource-administrations : get all the resourceAdministrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resourceAdministrations in body
     */
    @GetMapping("/resource-administrations/")
    @Timed
    public ResponseEntity<List<ResourceAdministration>> getAllResourceAdministrations
    (@RequestParam(value = "query",required = false) String resourceName , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ResourceAdministrations");
        Page<ResourceAdministration> page;
        if(StringUtils.isBlank(resourceName)){
            page = resourceAdministrationService.findAll(pageable);
        }else{
            page = resourceAdministrationService.findByResourceName(pageable,resourceName);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resource-administrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /resource-administrations/:id : get the "id" resourceAdministration.
     *
     * @param id the id of the resourceAdministration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourceAdministration, or with status 404 (Not Found)
     */
    @GetMapping("/resource-administrations/{id}")
    @Timed
    public ResponseEntity<ResourceAdministration> getResourceAdministration(@PathVariable Long id) {
        log.debug("REST request to get ResourceAdministration : {}", id);
        ResourceAdministration resourceAdministration = resourceAdministrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resourceAdministration));
    }

    /**
     * DELETE  /resource-administrations/:id : delete the "id" resourceAdministration.
     *
     * @param id the id of the resourceAdministration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resource-administrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteResourceAdministration(@PathVariable Long id) {
        log.debug("REST request to delete ResourceAdministration : {}", id);
        resourceAdministrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
