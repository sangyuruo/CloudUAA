package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.service.ResourceService;
import com.emcloud.uaa.web.rest.errors.BadRequestAlertException;
import com.emcloud.uaa.web.rest.util.HeaderUtil;
import com.emcloud.uaa.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
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
 * REST controller for managing Resources.
 */
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final Logger log = LoggerFactory.getLogger(ResourceResource.class);

    private static final String ENTITY_NAME = "resource";

    private final ResourceService resourceService;

    public ResourceResource(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * POST  /resources : Create a new resources.
     *
     * @param resources the resources to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resources, or with status 400 (Bad Request) if the resources has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resources")
    @Timed
    public ResponseEntity<Resources> createResource(@Valid @RequestBody Resources resources) throws URISyntaxException {
        log.debug("REST request to save Resources : {}", resources);
        if (resources.getId() != null) {
            throw new BadRequestAlertException("A new resources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resources result = resourceService.save(resources);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resources : Updates an existing resources.
     *
     * @param resources the resources to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resources,
     * or with status 400 (Bad Request) if the resources is not valid,
     * or with status 500 (Internal Server Error) if the resources couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resources")
    @Timed
    public ResponseEntity<Resources> updateResource(@Valid @RequestBody Resources resources) throws URISyntaxException {
        log.debug("REST request to update Resources : {}", resources);
        if (resources.getId() == null) {
            return createResource(resources);
        }
        Resources result = resourceService.update(resources);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resources.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resource-administrations : get all the Resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of Resources in body
     */
    @GetMapping("/resource-administrations/")
    @Timed
    public ResponseEntity<List<Resources>> getAllResource
    (@RequestParam(value = "query",required = false) String resourceName , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Resources");
        Page<Resources> page;
        if(StringUtils.isBlank(resourceName)){
            page = resourceService.findAll(pageable);
        }else{
            page = resourceService.findByResourceName(pageable,resourceName);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resource-administrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /resources/:id : get the "id" resource.
     *
     * @param id the id of the resource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resource, or with status 404 (Not Found)
     */
    @GetMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Resources> getResource(@PathVariable Long id) {
        log.debug("REST request to get Resources : {}", id);
        Resources resources = resourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resources));
    }

    /**
     * DELETE  /resources/:id : delete the "id" resource.
     *
     * @param id the id of the resource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.debug("REST request to delete Resources : {}", id);
        resourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
