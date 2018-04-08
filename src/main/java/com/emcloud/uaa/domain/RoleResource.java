package com.emcloud.uaa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 角色资源关系表
 * @author youhong
 */
@ApiModel(description = "角色资源关系表 @author youhong")
@Entity
@Table(name = "role_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "角色名", required = true)
    @Column(name = "role_name", length = 255, nullable = false)
    private String roleName;

    /**
     * 资源编码
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "资源编码", required = true)
    @Column(name = "resource_code", length = 255, nullable = false)
    private String resourceCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public RoleResource roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public RoleResource resourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
        return this;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
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
        RoleResource roleResource = (RoleResource) o;
        if (roleResource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleResource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleResource{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", resourceCode='" + getResourceCode() + "'" +
            "}";
    }
}
