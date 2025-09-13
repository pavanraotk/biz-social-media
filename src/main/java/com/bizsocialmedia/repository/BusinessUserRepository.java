package com.bizsocialmedia.repository;

import com.bizsocialmedia.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

    /**
     * Find a business user by email address
     */
    Optional<BusinessUser> findByEmail(String email);

    /**
     * Find business users by company name (case insensitive)
     */
    @Query("SELECT bu FROM BusinessUser bu WHERE LOWER(bu.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<BusinessUser> findByCompanyNameContainingIgnoreCase(@Param("companyName") String companyName);

    /**
     * Check if a business user exists with the given email
     */
    boolean existsByEmail(String email);
}