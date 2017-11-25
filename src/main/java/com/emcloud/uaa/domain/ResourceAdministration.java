package com.emcloud.uaa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 资源管理
 * @author youhong
 */
@ApiModel(description = "资源管理 @author youhong")
@Entity
@Table(name = "resource_administration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResourceAdministration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源编号
     */
    @Size(max = 64)
    @ApiModelProperty(value = "资源编号")
    @Column(name = "resource_code", length = 64)
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
    @Column(name = "visit_address", length = 100, nullable = false)
    private String visitAddress;

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
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @NotNull
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public ResourceAdministration resourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
        return this;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public ResourceAdministration resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public ResourceAdministration resourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public ResourceAdministration visitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
        return this;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    public Boolean isEnable() {
        return enable;
    }

    public ResourceAdministration enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ResourceAdministration createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public ResourceAdministration createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public ResourceAdministration updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public ResourceAdministration updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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
        ResourceAdministration resourceAdministration = (ResourceAdministration) o;
        if (resourceAdministration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceAdministration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceAdministration{" +
            "id=" + getId() +
            ", resourceCode='" + getResourceCode() + "'" +
            ", resourceName='" + getResourceName() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            ", visitAddress='" + getVisitAddress() + "'" +
            ", enable='" + isEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
