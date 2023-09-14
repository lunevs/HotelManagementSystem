package com.ichtus.hotelmanagementsystem.validationEntities;

import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class AmenityTest {

    @Autowired
    private TestEntityManager entityManager;

    Amenity baseAmenity = new Amenity()
            .setAmenityName("test amenity 1000")
            .setAmenityDescription("some description")
            .setAmenityPrice(100)
            .setAmenityType(AmenityType.HOTEL);


    @Test
    @WithMockAdmin
    void whenAmenityName_isIncorrect() {
        Amenity curAmenity = baseAmenity.setAmenityName(null);
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAmenity);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenAmenityPrice_isIncorrect() {
        Amenity amenity = baseAmenity.setAmenityPrice(-20);
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(amenity);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenAmenity_isCorrect() {
        assertThatCode(() -> {
            entityManager.persist(baseAmenity);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }
}
