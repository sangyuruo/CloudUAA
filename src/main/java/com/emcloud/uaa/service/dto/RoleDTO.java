package com.emcloud.uaa.service.dto;

import com.emcloud.uaa.domain.Resources;
import com.emcloud.uaa.domain.Role;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleDTO {


        private Long id;


        @Size(max = 40)
        private String name;

        @Size(max = 500)
        private String description;


        private Set<String> resources ;


        // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
        public RoleDTO(){}
        public RoleDTO(Role role){
            this( role.getId(),role.getName(),role.getDescription(),role.getResources()
                .stream().map(Resources::getResourceCode)
                .collect(Collectors.toSet()));
        }
    public RoleDTO(Long id,String name ,String description,Set<String> resources){
            this.id=id;
            this.name=name;
            this.description=description;
            this.resources=resources;
    }



        @Override
        public String toString() {
            return "Role{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", resources='" + getResources() + "'" +
                "}";
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public Set<String> getResources() {
        return resources;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


