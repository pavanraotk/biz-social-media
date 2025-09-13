package com.bizsocialmedia.controller;

import com.bizsocialmedia.entity.BusinessUser;
import com.bizsocialmedia.repository.BusinessUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/business-users")
@CrossOrigin(origins = "*")
public class BusinessUserController {

    @Autowired
    private BusinessUserRepository businessUserRepository;

    /**
     * Get all business users
     */
    @GetMapping
    public ResponseEntity<List<BusinessUser>> getAllBusinessUsers() {
        List<BusinessUser> users = businessUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Get business user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusinessUser> getBusinessUserById(@PathVariable Long id) {
        Optional<BusinessUser> user = businessUserRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new business user
     */
    @PostMapping
    public ResponseEntity<BusinessUser> createBusinessUser(@Valid @RequestBody BusinessUser businessUser) {
        // Check if email already exists
        if (businessUserRepository.existsByEmail(businessUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        BusinessUser savedUser = businessUserRepository.save(businessUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Update an existing business user
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusinessUser> updateBusinessUser(@PathVariable Long id, 
                                                          @Valid @RequestBody BusinessUser businessUser) {
        Optional<BusinessUser> existingUser = businessUserRepository.findById(id);
        if (existingUser.isPresent()) {
            BusinessUser userToUpdate = existingUser.get();
            userToUpdate.setCompanyName(businessUser.getCompanyName());
            userToUpdate.setEmail(businessUser.getEmail());
            userToUpdate.setDescription(businessUser.getDescription());
            
            BusinessUser updatedUser = businessUserRepository.save(userToUpdate);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a business user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessUser(@PathVariable Long id) {
        if (businessUserRepository.existsById(id)) {
            businessUserRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Search business users by company name
     */
    @GetMapping("/search")
    public ResponseEntity<List<BusinessUser>> searchByCompanyName(@RequestParam String companyName) {
        List<BusinessUser> users = businessUserRepository.findByCompanyNameContainingIgnoreCase(companyName);
        return ResponseEntity.ok(users);
    }
}