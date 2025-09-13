package com.bizsocialmedia.repository;

import com.bizsocialmedia.entity.BusinessUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessUserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private BusinessUserRepository businessUserRepository;

    private BusinessUser testUser;

    @BeforeEach
    void setUp() {
        businessUserRepository.deleteAll();
        testUser = new BusinessUser();
        testUser.setCompanyName("Test Company");
        testUser.setEmail("test@company.com");
        testUser.setDescription("A test company for integration testing");
        businessUserRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        Optional<BusinessUser> found = businessUserRepository.findByEmail("test@company.com");
        
        assertThat(found).isPresent();
        assertThat(found.get().getCompanyName()).isEqualTo("Test Company");
        assertThat(found.get().getEmail()).isEqualTo("test@company.com");
    }

    @Test
    void testExistsByEmail() {
        boolean exists = businessUserRepository.existsByEmail("test@company.com");
        
        assertThat(exists).isTrue();
    }

    @Test
    void testSaveAndFind() {
        BusinessUser newUser = new BusinessUser();
        newUser.setCompanyName("Another Company");
        newUser.setEmail("another@company.com");
        newUser.setDescription("Another test company");
        
        BusinessUser saved = businessUserRepository.save(newUser);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        
        Optional<BusinessUser> found = businessUserRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCompanyName()).isEqualTo("Another Company");
    }
}