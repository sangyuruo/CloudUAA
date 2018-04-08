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
     * GET  /resource : get all the .Resource
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Resources in body
     */

    @GetMapping("/resources/tree")
    @Timed
    public StringBuilder getRoots() {

        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次

        List<Resources> roots = resourceService.findByParentCode("0");

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {//查询所有菜单
            String resourceCode = null;
            Resources preNav = null;
            for (Resources nav : roots) {
                resourceCode = nav.getResourceCode();
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
                lastLevelNum = curLevelNum;
                preNav = nav;

                sb.append("{ \n");
                sb.append("\"label\"").append(":\"").append(nav.getResourceName()).append("\",");
                sb.append("\"resourceCode\"").append(":\"").append(nav.getResourceCode()).append("\",");
                List<Resources> roots2 = resourceService.findByParentCode(resourceCode);
                if (roots2.size() != 0) {
                    sb.append("\"leaf\"").append(":").append(false);
                    sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\",");
                    sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                    sb.append(",\"children\" :[ \n");



                    int lastLevelNum2 = 0; // 上一次的层次
                    int curLevelNum2 = 0; // 本次对象的层次

//                    List<Resources> roots2 = resourceService.findByParentCode(resourceCode);
                    //StringBuilder sb = new StringBuilder();
                    try {//查询所有菜单

                        Resources preNav2 = null;
                        for (Resources nav2 : roots2) {
                            curLevelNum2 = getLevelNum(nav2);
                            if (null != preNav2) {
                                if (lastLevelNum2 == curLevelNum2) { // 同一层次的
                                    sb.append("}, \n");
                                } else if (lastLevelNum2 > curLevelNum2) { // 这次的层次比上次高一层，也即跳到上一层
                                    sb.append("} \n");

                                    for (int j = curLevelNum2; j < lastLevelNum2; j++) {
                                        sb.append("}] \n");
                                        if (j == lastLevelNum2 - 1) {
                                            sb.append(", \n");
                                        }

                                    }
                                }
                            }
                            sb.append("{ \n");
                            sb.append("\"label\"").append(":\"").append(nav2.getResourceName()).append("\",");
                            sb.append("\"resourceCode\"").append(":\"").append(nav2.getResourceCode()).append("\",");
                            sb.append("\"icon\"").append(":\"").append("fa-file-image-o").append("\"");
                            List<Resources> nav2roots2 = resourceService.findByParentCode(nav2.getResourceCode());
                            if (nav2roots2.size() != 0) {
                    sb.append(",\"leaf\"").append(":").append(false);
                    sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\"");
                    sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                               /* sb.append(",\"children\" :[ \n");
                                sb.append("] \n");*/
                            }
                            lastLevelNum2 = curLevelNum2;
                            preNav2 = nav2;
                        }
                        sb.append("} \n");

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


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




    @GetMapping("/resources/nextTree")
    public StringBuilder getNextTree(@RequestParam(value = "parentCode") String parentCode) {

        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次

        List<Resources> roots = resourceService.findByParentCode(parentCode);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {//查询所有菜单
            Resources preNav = null;
            for (Resources nav : roots) {
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
                sb.append("\"resourceCode\"").append(":\"").append(nav.getResourceCode()).append("\",");
                sb.append("\"id\"").append(":").append(nav.getId()).append(",");
                List<Resources> nav2roots = resourceService.findByParentCode(nav.getResourceCode());
                if (nav2roots.size() != 0) {
//                    sb.append(",\"leaf\"").append(":").append(false);
//                    sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\",");
//                    sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                    sb.append(",\"children\" :[ \n");
                    sb.append("] \n");
                }
                lastLevelNum = curLevelNum;
                preNav = nav;
            }
            sb.append("} \n");


        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("]");
        return sb;
    }

 /*   @GetMapping("/resources/{roleIdentify}")
    @Timed
    public List<Resources> getAllResourceRoleIdentify
        (@PathVariable(value = "roleIdentify", required = false) String roleIdentify) {
        log.debug("REST roleIdentify to get a page of Resources");
        List<Resources> list = resourceService.findByRoleIdentify(roleIdentify);
        return list;
    }*/
    /**
     * GET  /resources : get all the Resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of Resources in body
     */
    @GetMapping("/resources")
    @Timed
    public ResponseEntity<List<Resources>> getAllResource(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Resources");
        Page<Resources> page = resourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resources");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resources : get all the Resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of Resources in body
     */
    @GetMapping("/resources/{resourceName}")
    @Timed
    public ResponseEntity<List<Resources>> getAllResourceByResourceName
    (@RequestParam(value = "query",required = false) String resourceName , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Resources");
        Page<Resources> page;
        if(StringUtils.isBlank(resourceName)){
            page = resourceService.findAll(pageable);
        }else{
            page = resourceService.findByResourceName(pageable,resourceName);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resources");
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
