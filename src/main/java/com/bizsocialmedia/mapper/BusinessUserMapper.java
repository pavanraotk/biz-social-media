package com.bizsocialmedia.mapper;

import com.bizsocialmedia.entity.BusinessUser;
import com.bizsocialmedia.model.BusinessUserCreate;
import com.bizsocialmedia.model.BusinessUserUpdate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessUserMapper {

    /**
     * Convert entity to API model
     */
    public com.bizsocialmedia.model.BusinessUser toModel(BusinessUser entity) {
        if (entity == null) {
            return null;
        }
        
        com.bizsocialmedia.model.BusinessUser model = new com.bizsocialmedia.model.BusinessUser();
        model.setId(entity.getId());
        model.setCompanyName(entity.getCompanyName());
        model.setEmail(entity.getEmail());
        model.setDescription(entity.getDescription());
        model.setCreatedAt(localDateTimeToDate(entity.getCreatedAt()));
        model.setUpdatedAt(localDateTimeToDate(entity.getUpdatedAt()));
        
        return model;
    }

    /**
     * Convert list of entities to list of API models
     */
    public List<com.bizsocialmedia.model.BusinessUser> toModelList(List<BusinessUser> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Convert create request to entity
     */
    public BusinessUser toEntity(BusinessUserCreate createRequest) {
        if (createRequest == null) {
            return null;
        }
        
        BusinessUser entity = new BusinessUser();
        entity.setCompanyName(createRequest.getCompanyName());
        entity.setEmail(createRequest.getEmail());
        entity.setDescription(createRequest.getDescription());
        
        return entity;
    }

    /**
     * Update entity from update request
     */
    public void updateEntity(BusinessUser entity, BusinessUserUpdate updateRequest) {
        if (entity == null || updateRequest == null) {
            return;
        }
        
        entity.setCompanyName(updateRequest.getCompanyName());
        entity.setEmail(updateRequest.getEmail());
        entity.setDescription(updateRequest.getDescription());
    }

    /**
     * Convert LocalDateTime to Date for API compatibility
     */
    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}