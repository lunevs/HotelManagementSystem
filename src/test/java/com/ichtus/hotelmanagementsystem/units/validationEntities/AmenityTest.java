package com.ichtus.hotelmanagementsystem.units.validationEntities;

import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
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
