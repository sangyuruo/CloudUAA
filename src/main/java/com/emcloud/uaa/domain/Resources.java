package com.emcloud.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Resources.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源编号
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "资源编号", required = true)
    @Column(name = "resource_code", length = 64, nullable = false)
    private String resourceCode;

    /**
     * 资源名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "资源名称", required = true)
    @Column(name = "resource_name", length = 100, nullable = false)
    private String resourceName;

    /**
     * 资源类型
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "资源类型", required = true)
    @Column(name = "resource_type", length = 100, nullable = false)
    private String resourceType;

    /**
     * 访问地址
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "访问地址", required = true)
    @Column(name = "resource_url", length = 100, nullable = false)
    private String resourceUrl;

    /**
     * 上级代码
     */
    @Size(max = 64)
    @Column(name = "parent_code", length = 100, nullable = false)
    @ApiModelProperty(value = "上级代码", required = true)
    private String parentCode;

    /**
     * 上级名
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "parent_name", length = 100, nullable = false)
    @ApiModelProperty(value = "上级名", required = true)
    private String parentName;

    /**
     * 排序
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "sort", length = 100, nullable = false)
    @ApiModelProperty(value = "排序", required = true)
    private String sort;

    /**
     * 权限标识
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "role_identify", length = 100, nullable = false)
    @ApiModelProperty(value = "权限标识", required = true)
    private String roleIdentify;

    /**
     * 级别
     */
    @NotNull
    @Column(name = "level", nullable = false)
    @ApiModelProperty(value = "级别", required = true)
    private Integer level;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Boolean enable;

    /**
     * 创建人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "role_resource",
        joinColumns = {@JoinColumn(name = "resource_code", referencedColumnName = "resource_code")},
        inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Role> roles;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Resources resourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
        return this;
    }

    public String getResourceCode() {
        return resourceCode;
    }
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Resources resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Resources resourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public Resources resourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Resources parentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public Resources parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSort() {
        return sort;
    }

    public Resources sort(String sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRoleIdentify() {
        return roleIdentify;
    }

    public Resources roleIdentify(String roleIdentify) {
        this.roleIdentify = roleIdentify;
        return this;
    }

    public void setRoleIdentify(String roleIdentify) {
        this.roleIdentify = roleIdentify;
    }

    public Integer getLevel() {
        return level;
    }

    public Resources level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Boolean isEnable() {
        return enable;
    }

    public Resources enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Resources createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Resources createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Resources updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Resources updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resources resources = (Resources) o;
        if (resources.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resources.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resources{" +
            "id=" + getId() +
            ", resourceCode='" + getResourceCode() + "'" +
            ", resourceName='" + getResourceName() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            ", resourceUrl='" + getResourceUrl() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", sort='" + getSort() + "'" +
            ", roleIdentify='" + getRoleIdentify() + "'" +
            ", level='" + getLevel() + "'" +
            ", enable='" + isEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
