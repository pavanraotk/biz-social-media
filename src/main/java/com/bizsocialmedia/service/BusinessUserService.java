package com.bizsocialmedia.service;

import com.bizsocialmedia.api.BusinessUsersApi;
import com.bizsocialmedia.entity.BusinessUser;
import com.bizsocialmedia.mapper.BusinessUserMapper;
import com.bizsocialmedia.model.BusinessUserCreate;
import com.bizsocialmedia.model.BusinessUserUpdate;
import com.bizsocialmedia.repository.BusinessUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class BusinessUserService implements BusinessUsersApi {

    @Autowired
    private BusinessUserRepository businessUserRepository;

    @Autowired
    private BusinessUserMapper businessUserMapper;

    @Override
    public ResponseEntity<List<com.bizsocialmedia.model.BusinessUser>> getAllBusinessUsers() {
        List<BusinessUser> entities = businessUserRepository.findAll();
        List<com.bizsocialmedia.model.BusinessUser> models = businessUserMapper.toModelList(entities);
        return ResponseEntity.ok(models);
    }

    @Override
    public ResponseEntity<com.bizsocialmedia.model.BusinessUser> getBusinessUserById(Long id) {
        Optional<BusinessUser> entity = businessUserRepository.findById(id);
        if (entity.isPresent()) {
            com.bizsocialmedia.model.BusinessUser model = businessUserMapper.toModel(entity.get());
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<com.bizsocialmedia.model.BusinessUser> createBusinessUser(BusinessUserCreate businessUserCreate) {
        // Check if email already exists
        if (businessUserRepository.existsByEmail(businessUserCreate.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        BusinessUser entity = businessUserMapper.toEntity(businessUserCreate);
        BusinessUser savedEntity = businessUserRepository.save(entity);
        com.bizsocialmedia.model.BusinessUser model = businessUserMapper.toModel(savedEntity);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Override
    public ResponseEntity<com.bizsocialmedia.model.BusinessUser> updateBusinessUser(Long id, BusinessUserUpdate businessUserUpdate) {
        Optional<BusinessUser> existingEntity = businessUserRepository.findById(id);
        if (existingEntity.isPresent()) {
            BusinessUser entityToUpdate = existingEntity.get();
            businessUserMapper.updateEntity(entityToUpdate, businessUserUpdate);
            
            BusinessUser updatedEntity = businessUserRepository.save(entityToUpdate);
            com.bizsocialmedia.model.BusinessUser model = businessUserMapper.toModel(updatedEntity);
            
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteBusinessUser(Long id) {
        if (businessUserRepository.existsById(id)) {
            businessUserRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<com.bizsocialmedia.model.BusinessUser>> searchBusinessUsersByCompanyName(String companyName) {
        List<BusinessUser> entities = businessUserRepository.findByCompanyNameContainingIgnoreCase(companyName);
        List<com.bizsocialmedia.model.BusinessUser> models = businessUserMapper.toModelList(entities);
        return ResponseEntity.ok(models);
    }
}